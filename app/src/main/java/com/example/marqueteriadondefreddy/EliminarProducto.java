package com.example.marqueteriadondefreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EliminarProducto extends AppCompatActivity  implements ImageAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private ValueEventListener mDBListener;
    private List<uploadInfo> mUpload;
    private Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_producto);
        salir = findViewById(R.id.btn_volver_elimi);
        recyclerView = findViewById(R.id.recycler_view_pro_b);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUpload = new ArrayList<>();
        imageAdapter = new ImageAdapter(EliminarProducto.this, mUpload);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(EliminarProducto.this);
        firebaseStorage = FirebaseStorage.getInstance();
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Producto");
        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUpload.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    uploadInfo Upload = postSnapshot.getValue(uploadInfo.class);
                    Upload.setmKey(postSnapshot.getKey());
                    mUpload.add(Upload);
                }
                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EliminarProducto.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onModifyClick(int position) {
        uploadInfo selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getmKey();
        Intent intent = new Intent(this, ModificarProducto.class);
        intent.putExtra("nombre", selectedItem.getImageName());
        intent.putExtra("descripcion", selectedItem.getDescripcion());
        intent.putExtra("key", selectedItem.getmKey());
        intent.putExtra("url", selectedItem.getImageURL());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        uploadInfo selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getmKey();

        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(selectedItem.getImageURL());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectedKey).removeValue();
                Toast.makeText(EliminarProducto.this, "Se ha borrado exitosamente", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}

