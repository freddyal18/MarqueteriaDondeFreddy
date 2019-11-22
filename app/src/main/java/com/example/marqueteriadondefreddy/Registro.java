package com.example.marqueteriadondefreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private EditText nom, ape, cel, corr, con; //Variables para hacer uso de los EditText
    private Button regi, volver; //Variable para hacer uso del botón de registro
    FirebaseAuth firebaseAuth; //Variable para hacer uso de la autenticación
    FirebaseDatabase firebaseDatabase; //Variable para hacer uso de la base de datos
    FirebaseStorage firebaseStorage; //Variable para hacer uso de la base de datos
    StorageReference storageReference; //Variable para hacer uso de la base de datos
    DatabaseReference databaseReference; //Variable para hacer uso de la base de datos
    private String nombre, apellido, celular, correo, contra; //Variables para guardar lo que se escriba en los EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        nom = findViewById(R.id.tx_nombre);
        ape = findViewById(R.id.tx_apell);
        cel = findViewById(R.id.tx_cel);
        corr = findViewById(R.id.tx_corr);
        con = findViewById(R.id.tx_cont);
        regi = findViewById(R.id.btn_reg);
        volver = findViewById(R.id.btn_volverDeReg);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = nom.getText().toString();
                apellido = ape.getText().toString();
                celular = cel.getText().toString();
                correo = corr.getText().toString();
                contra = con.getText().toString();

                if(!nombre.isEmpty() && !apellido.isEmpty() && !celular.isEmpty() && !contra.isEmpty() && !correo.isEmpty()){
                    if(contra.length() >=7){
                        registrarUsuario();
                    }
                    else{
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 7 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Registro.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void registrarUsuario (){
        firebaseAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre", nombre);
                    map.put("Apellidos", apellido);
                    map.put("Celular", celular);
                    map.put("Correo", correo);
                    map.put("Contraseña", contra);
                    String id = firebaseAuth.getCurrentUser().getUid();
                    databaseReference.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if (task1.isSuccessful()){
                                finish();
                            }
                            else{
                                Toast.makeText(Registro.this, "No se pudo almacenar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}

