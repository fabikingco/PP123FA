package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ConfiguracionActivity extends AppCompatActivity {

    LinearLayout tiposDeVehiculos, seleccionPaisMoneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        tiposDeVehiculos = findViewById(R.id.tiposDeVehiculos);
        tiposDeVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfiguracionActivity.this, TiposDeVehiculosActivity.class));
            }
        });

        seleccionPaisMoneda = findViewById(R.id.seleccionPaisMoneda);
        seleccionPaisMoneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfiguracionActivity.this, ConfigMondedaActivity.class));
            }
        });
    }
}
