package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NuevoVehiculos extends AppCompatActivity {

    Spinner combovehiculo;
    Spinner combotiempodepago;
    private EditText texvehiculo,texmetodo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_vehiculos);

        combovehiculo=(Spinner)findViewById(R.id.vehiculos);
        combotiempodepago=(Spinner)findViewById(R.id.tiempodepago);
        texvehiculo=(EditText)findViewById(R.id.agrevehiculo);
        texmetodo=(EditText)findViewById(R.id.metparqueadero);


        //Array para cargar el spinner de Vehiculos del formulario Nuevo_Vehiculos
        ArrayList<String> combovehiculoList=new ArrayList<String>();
        combovehiculoList.add("Seleccione Tipo de Vehiculo");
        combovehiculoList.add("Automovil");
        combovehiculoList.add("Camiones");
        combovehiculoList.add("Motocicleta");
        combovehiculoList.add("Bicicleta");
        combovehiculoList.add("Otros");

        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,combovehiculoList);
        combovehiculo.setAdapter(adapter);

        combovehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(adapterView.getContext(),"Seleccione:"+adapterView.getItemAtPosition(i),Toast.LENGTH_LONG).show();
                if(adapterView.getItemAtPosition(i).equals("5")){
                    texvehiculo.setVisibility(View.VISIBLE);
                    texmetodo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Array para cargar el spinner de Tiempo de pago del formulario Nuevo_Vehiculos
        ArrayList<String> combotiempodepagoList=new ArrayList<String>();
        combotiempodepagoList.add("Seleccione Metodo de Cobro");
        combotiempodepagoList.add("Horas");
        combotiempodepagoList.add("Dias");
        combotiempodepagoList.add("Semanas");
        combotiempodepagoList.add("Mes");

        ArrayAdapter<CharSequence> adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,combotiempodepagoList);
        combotiempodepago.setAdapter(adapter1);
    }
}
