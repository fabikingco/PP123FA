package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fabik.parkingapp.Adaptadores.AdaptadorTipoVehiculos;
import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.Modelos.TipoVehiculos;

import java.util.ArrayList;

public class TiposDeVehiculosActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_de_vehiculos);

        recyclerView = findViewById(R.id.rvTipos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        AdminSQLiteOpenHelper bd = new AdminSQLiteOpenHelper(this);
        ArrayList<TipoVehiculos> arrayList = bd.tiposDeVehiculos();
        arrayList.add(new TipoVehiculos(999, "Añadir nuevo", R.drawable.ic_add_black_24dp, "null", "null"));

        AdaptadorTipoVehiculos tipoVehiculos = new AdaptadorTipoVehiculos(this, arrayList);
        recyclerView.setAdapter(tipoVehiculos);

    }
}
