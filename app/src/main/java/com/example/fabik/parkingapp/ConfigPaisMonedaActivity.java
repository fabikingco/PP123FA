package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class ConfigPaisMonedaActivity extends AppCompatActivity {

    private ArrayList<String> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //https://github.com/mukeshsolanki/country-picker-android

        setContentView(R.layout.activity_config_pais_moneda);

        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));
    }
}
