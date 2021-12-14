package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarProductosActivity extends AppCompatActivity {

    RecyclerView recyclerViewProductos;
    ProductosAdapted mAdapter;
    FirebaseFirestore dbb;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_productos);

        recyclerViewProductos = findViewById(R.id.recyclerProductos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        dbb = FirebaseFirestore.getInstance();

        Query query = dbb.collection("productos");

        FirestoreRecyclerOptions<Producto> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Producto>()
                .setQuery(query, Producto.class).build();

        mAdapter = new ProductosAdapted(firestoreRecyclerOptions, this );
        mAdapter.notifyDataSetChanged();
        recyclerViewProductos.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}