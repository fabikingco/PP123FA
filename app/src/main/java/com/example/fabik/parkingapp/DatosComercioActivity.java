package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class DatosComercioActivity extends AppCompatActivity {

    private ArrayList<String> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_comercio);

        //https://github.com/mukeshsolanki/country-picker-android
        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));
    }
}
