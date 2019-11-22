package com.example.marqueteriadondefreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperacion extends AppCompatActivity {

    private TextView a, b;
    private Typeface script;
    private EditText correo;
    private Button enviar, volver;
    private String email ="";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperacion);
        firebaseAuth =FirebaseAuth.getInstance();
        a = findViewById(R.id.id_recuperacion);
        b = findViewById(R.id.id_celula);
        correo = findViewById(R.id.tx_recucor);
        volver = findViewById(R.id.btn_volverDeRecu);
        dia = new ProgressDialog(this);
        enviar = findViewById(R.id.btn_recuper);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = correo.getText().toString();
                if(!email.isEmpty()){
                    dia.setMessage("Espere un momento");
                    dia.setCanceledOnTouchOutside(false);
                    dia.show();
                    resetPassword();
                }
                else{
                    Toast.makeText(Recuperacion.this, "Debe ingresar el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void resetPassword (){
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Recuperacion.this, "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(Recuperacion.this, "No se pudo enviar el correo de recuperación de contraseña", Toast.LENGTH_SHORT).show();
                }
                dia.dismiss();
            }
        });
        String fuente = "fuentes/Gabriola.ttf";
        this.script = Typeface.createFromAsset(getAssets(), fuente);
        a.setTypeface(script);
        b.setTypeface(script);
    }
}
