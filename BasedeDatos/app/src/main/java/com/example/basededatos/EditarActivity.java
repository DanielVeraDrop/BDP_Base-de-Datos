package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Value;

import java.util.HashMap;
import java.util.Map;

public class EditarActivity extends AppCompatActivity {

    EditText txt_codigo2;
    EditText txt_descripcion2;
    EditText txt_precio2;
    Button edit_button;

    private String productoID;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txt_codigo2 = findViewById(R.id.txt_codigo2);
        txt_descripcion2 = findViewById(R.id.txt_descripcion2);
        txt_precio2 = findViewById(R.id.txt_precio2);
        edit_button = findViewById(R.id.edit_btn);

        db = FirebaseFirestore.getInstance();
        productoID = getIntent().getStringExtra("productoID");

        edit_button.setOnClickListener(view -> actualizarDatos());
    }
    private void actualizarDatos(){

        String codigo = txt_codigo2.getText().toString();
        String descripcion = txt_descripcion2.getText().toString();
        String precio = txt_precio2.getText().toString();
        Map<String, Object> map = new HashMap<>();

        map.put("codigo", codigo);
        map.put("descripcion", descripcion);
        map.put("precio", precio);

        db.collection("productos").document(productoID).update(map);
    }
}