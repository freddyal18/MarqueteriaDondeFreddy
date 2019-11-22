package com.example.marqueteriadondefreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModificarProducto extends AppCompatActivity {

    private EditText nombre;
    private EditText descripcion;
    private Button guardar, volver;
    private FirebaseDatabase firebaseDatabase;
    private String nom, desc, key, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);
        key = getIntent().getStringExtra("key");
        url = getIntent().getStringExtra("url");
        nom = getIntent().getStringExtra("nombre");
        desc = getIntent().getStringExtra("descripcion");
        nombre = findViewById(R.id.txt_modi_nom);
        descripcion = findViewById(R.id.txt_modi_des);
        guardar = findViewById(R.id.btn_guardar_modi);
        volver = findViewById(R.id.btn_volver_modi);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Producto");
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = nombre.getText().toString();
                String b = descripcion.getText().toString();

                uploadInfo Upload = new uploadInfo(a, b, url);

                databaseReference.child(key).setValue(Upload);

            }
        });

    }
}
