package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fabik.parkingapp.Adaptadores.AdaptadorTipoVehiculos;
import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.Modelos.TipoVehiculos;

import java.util.ArrayList;

public class TiposDeVehiculosActivity extends AppCompatActivity implements AdaptadorTipoVehiculos.OnItemClickListenerTipo {

    RecyclerView recyclerView;
    ArrayList<TipoVehiculos> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_de_vehiculos);

        recyclerView = findViewById(R.id.rvTipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        AdminSQLiteOpenHelper bd = new AdminSQLiteOpenHelper(this);
        arrayList = bd.tiposDeVehiculos();
        arrayList.add(new TipoVehiculos(999, "Añadir nuevo", R.drawable.ic_add_black_24dp, "null", "null"));

        AdaptadorTipoVehiculos tipoVehiculos = new AdaptadorTipoVehiculos(this, arrayList);
        tipoVehiculos.setOnItemClickListener(this);
        recyclerView.setAdapter(tipoVehiculos);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder item, int position, int id) {
        TipoVehiculos tipoVehiculos = arrayList.get(position);
        if (tipoVehiculos.getId() == 999) {
            Intent i=new Intent(this,NuevoVehiculos.class);
            startActivity(i);
        }
        //Toast.makeText(this, "" + tipoVehiculos.getName(), Toast.LENGTH_SHORT).show();
    }
}
