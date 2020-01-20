package com.example.fabik.parkingapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fabik.parkingapp.Modelos.TipoVehiculos;
import com.example.fabik.parkingapp.R;

import java.util.ArrayList;

public class AdaptadorTipoVehiculos extends RecyclerView.Adapter<AdaptadorTipoVehiculos.HolderTipoVehiculos> {


    private OnItemClickListenerTipo listener;

    /**
     * Interfaz de comunicación
     */
    public interface OnItemClickListenerTipo {
        void onItemClick(RecyclerView.ViewHolder item, int position, int id);
    }

    public void setOnItemClickListener(OnItemClickListenerTipo listener) {
        this.listener = listener;
    }

    public OnItemClickListenerTipo getOnItemClickListener() {
        return listener;
    }

    private Context context;
    private ArrayList<TipoVehiculos> tipoVehiculos;

    public AdaptadorTipoVehiculos(Context context, ArrayList<TipoVehiculos> tipoVehiculos) {
        this.context = context;
        this.tipoVehiculos = tipoVehiculos;
    }

    @NonNull
    @Override
    public HolderTipoVehiculos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipos_vehiculos, parent, false);
        return new HolderTipoVehiculos(v, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTipoVehiculos holder, int position) {
        TipoVehiculos tipoVehiculos = this.tipoVehiculos.get(position);
        holder.tvTitulo.setText(tipoVehiculos.getName());
        holder.imgButton.setImageDrawable(context.getDrawable(tipoVehiculos.getImg()));
    }

    @Override
    public int getItemCount() {
        return tipoVehiculos.size();
    }

    public class HolderTipoVehiculos extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageButton imgButton;
        TextView tvTitulo;
        LinearLayout tiposDeVehiculos;
        AdaptadorTipoVehiculos adaptador;

        public HolderTipoVehiculos(@NonNull View itemView, AdaptadorTipoVehiculos adaptador) {
            super(itemView);
            this.adaptador = adaptador;
            tiposDeVehiculos = itemView.findViewById(R.id.tiposDeVehiculos);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            imgButton = itemView.findViewById(R.id.imgButton);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            final OnItemClickListenerTipo listener = adaptador.getOnItemClickListener();
            int id = view.getId();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition(), id);
            }
        }
    }
}
