package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class DatosComercioActivity extends AppCompatActivity {

    private ArrayList<String> country_list;
    LinearLayout    lyNombre, lyDocumento, lyDireccion, lyPais, lyEstado, lyCiudad, lyTelefono1, lyTelefono2;
    TextView        tvNombre, tvDocumento, tvDireccion, tvPais, tvEstado, tvCiudad, tvTelefono1, tvTelefono2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_comercio);

        initWidgets();

        //https://github.com/mukeshsolanki/country-picker-android
        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));
    }

    private void initWidgets() {
        lyNombre = findViewById(R.id.lyNombre);
        lyDocumento = findViewById(R.id.lyDocumento);
        lyDireccion = findViewById(R.id.lyDireccion);

    }
}
