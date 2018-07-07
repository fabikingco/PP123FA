package com.example.fabik.parkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class IngresarVehiculos extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_vehiculos);

        setTitle("Ingresar Vehoculos");

        tv1 = findViewById(R.id.tvHora);

        Calendar myDate = Calendar.getInstance();
        int hora = myDate.get( Calendar.HOUR);
        int minute = myDate.get(Calendar.MINUTE);
        int segundos = myDate.get(Calendar.SECOND);



        tv1.setText(String.valueOf(segundos)+" "+String.valueOf(minute)+" "+String.valueOf(hora))
        ;

    }
}
