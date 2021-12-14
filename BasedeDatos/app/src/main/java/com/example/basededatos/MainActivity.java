package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    EditText txt_codigo;
    EditText txt_descripcion;
    EditText txt_precio;
    EditText txt_verif;
    TextView txt_datos;
    Button regis_button;
    Button busca_button;
    Button buscar_button;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        txt_codigo = findViewById(R.id.txt_codigo);
        txt_descripcion = findViewById(R.id.txt_descripcion);
        txt_precio = findViewById(R.id.txt_precio);
        txt_datos = findViewById(R.id.txt_datos);
        txt_verif = findViewById(R.id.txt_verif);
        regis_button = findViewById(R.id.regis_button);
        busca_button = findViewById(R.id.busca_button);
        buscar_button = findViewById(R.id.buscar_button);


        regis_button.setOnClickListener(view -> crearDatos());
        buscar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MostrarProductosActivity.class));
            }
        });
        busca_button.setOnClickListener(view -> buscadatos());
    }

    private void buscadatos(){
        String verificador = txt_verif.getText().toString();
        db.collection("productos").document(verificador).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String codigo = documentSnapshot.getString("codigo");
                    String descripcion = documentSnapshot.getString("descripcion");
                    String precio = documentSnapshot.getString("precio");

                    txt_datos.setText("Codigo: " + codigo + "\n" + "Descripcion:" + descripcion
                    + "\n" + "Precio:" + precio);
                }
            }
        });
    }
    private void crearDatos(){

        String codigo = txt_codigo.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String precio = txt_precio.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("codigo", codigo);
        map.put("descripcion", descripcion);
        map.put("precio", precio);
            db.collection("productos").document().set(map);

    }
}


