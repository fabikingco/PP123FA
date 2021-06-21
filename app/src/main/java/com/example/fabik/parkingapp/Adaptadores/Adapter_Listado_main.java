package com.example.fabik.parkingapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fabik.parkingapp.Entidades.Ingresados;
import com.example.fabik.parkingapp.R;

import java.util.List;

public class Adapter_Listado_main extends RecyclerView.Adapter<Adapter_Listado_main.ViewHolder>  {


    Context context;
    List<Ingresados> lista;
    int layout;
    Ingresados ingresados;
    RecyOnItemClickListener recyOnItemClickListener;

    public Adapter_Listado_main(Context context, List<Ingresados> lista, int layout) {
        this.context = context;
        this.lista = lista;
        this.layout = layout;
    }

    public Adapter_Listado_main(Context context, List<Ingresados> lista, int layout,  RecyOnItemClickListener recyOnItemClickListener) {
        this.context = context;
        this.lista = lista;
        this.layout = layout;
        this.recyOnItemClickListener = recyOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ingresados = lista.get(position);

        holder.tvplaca.setText(ingresados.getPlaca());
        holder.tvFecha.setText(ingresados.getFecha_ing());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyOnItemClickListener.onclick(lista.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView card;
        private TextView tvplaca, tvFecha;
        private ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            tvplaca = itemView.findViewById(R.id.tv01);
            tvFecha = itemView.findViewById(R.id.tv02);
            img = itemView.findViewById(R.id.img);
        }
    }
}
