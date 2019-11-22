package com.example.marqueteriadondefreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuPrincipalFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_pantalla_principal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuPrincipalFragment()).commit();
                break;
            case R.id.nav_ver_productos:
                Intent intent = new Intent(MainActivity.this, Productos.class);
                startActivity(intent);
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VerProductosFragment()).commit();*/
                break;
            case R.id.nav_ver_servicios:
                Intent intent2  = new Intent(MainActivity.this , Servicios.class);
                startActivity(intent2);
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new VerServiciosFragment()).commit();*/
                break;
            case R.id.nav_agregar:
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AgregarFragment()).commit();*/
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setCancelable(true).setPositiveButton("Productos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent volver = new Intent(MainActivity.this, AgregarProducto.class);
                        startActivity(volver);
                    }
                }).setNegativeButton("Servicios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent volver = new Intent(MainActivity.this, AgregarServicio.class);
                        startActivity(volver);
                    }
                });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("¿Qué desea agregar?");
                titulo.show();
                break;
            case R.id.nav_eliminar:
                AlertDialog.Builder alerta1 = new AlertDialog.Builder(MainActivity.this);
                alerta1.setCancelable(true).setPositiveButton("Productos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent volver = new Intent(MainActivity.this, EliminarProducto.class);
                        startActivity(volver);
                    }
                }).setNegativeButton("Servicios", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent volver = new Intent(MainActivity.this, EliminarServicio.class);
                        startActivity(volver);
                    }
                });
                AlertDialog titulo1 = alerta1.create();
                titulo1.setTitle("¿Qué desea modificar o eliminar?");
                titulo1.show();
                break;
            case R.id.nav_cerrar_sesion:
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "No se ha podido cerrar sesión", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}
