package com.example.fabik.parkingapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        String modelo = Build.MODEL;

        System.out.println("" + modelo);
    }

    public void onClic_Ingresar(View view) {
        Intent intent = new Intent(this, IngresarVehiculos.class);
        startActivity(intent);
    }

    public void onClic_Sacar(View view) {
        Intent intent = new Intent(this, SacarVehiculos.class);
        startActivity(intent);
    }

    public void onClic_Listar(View view) {
        Intent intent = new Intent(this, ListarVehiculos.class);
        startActivity(intent);
    }

    public void onClic_ListadoTotal(View view) {
        Intent intent = new Intent(this, ListadoTotal.class);
        startActivity(intent);
    }
}
