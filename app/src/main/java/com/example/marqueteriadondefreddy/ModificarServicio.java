package com.example.marqueteriadondefreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModificarServicio extends AppCompatActivity {

    private EditText nombre;
    private EditText descripcion;
    private Button guardar, volver;
    private FirebaseDatabase firebaseDatabase;
    private String nom, desc, key, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_servicio);
        key = getIntent().getStringExtra("key");
        url = getIntent().getStringExtra("url");
        nom = getIntent().getStringExtra("nombre");
        desc = getIntent().getStringExtra("descripcion");
        nombre = findViewById(R.id.txt_modi_nom1);
        descripcion = findViewById(R.id.txt_modi_des1);
        guardar = findViewById(R.id.btn_guardar_modi1);
        volver = findViewById(R.id.btn_volver_modi1);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Servicio");
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
