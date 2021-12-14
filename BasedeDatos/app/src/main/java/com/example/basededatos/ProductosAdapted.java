package com.example.basededatos;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.annotation.Documented;

public class ProductosAdapted extends FirestoreRecyclerAdapter<Producto, ProductosAdapted.ViewHolder>{

    Activity activity;
    FirebaseFirestore db;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductosAdapted(@NonNull FirestoreRecyclerOptions<Producto>options, Activity activity) {
        super(options);
        this.activity=activity;
        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Producto producto) {

        DocumentSnapshot productoDocument = getSnapshots().getSnapshot(holder.getAdapterPosition());

        String id = productoDocument.getId();

        holder.txt_codigoview.setText(producto.getCodigo());
        holder.txt_descripcionview.setText(producto.getDescripcion());
        holder.txt_precioview.setText(producto.getPrecio());

        holder.button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditarActivity.class);
                intent.putExtra("productoID",id);
                activity.startActivity(intent);
            }
        });
        holder.button_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("productos").document(id).delete();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_productos,viewGroup, false   );
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_codigoview;
        TextView txt_descripcionview;
        TextView txt_precioview;
        Button button_editar;
        Button button_borrar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txt_codigoview = itemView.findViewById(R.id.txt_codigoview);
            txt_descripcionview = itemView.findViewById(R.id.txt_descripcionview);
            txt_precioview = itemView.findViewById(R.id.txt_precioview);
            button_editar = itemView.findViewById(R.id.button_editar);
            button_borrar = itemView.findViewById(R.id.button_borrar);
        }
    }
}
