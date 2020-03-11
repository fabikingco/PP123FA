package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;

import java.util.ArrayList;

public class NuevoVehiculos extends AppCompatActivity {

    Spinner combovehiculo;
    Spinner combotiempodepago;
    private EditText texvehiculo;
    private EditText texmetodo;
    private EditText textprecio;
    private EditText textprecio2;
    private Integer otros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_vehiculos);

        combovehiculo=(Spinner)findViewById(R.id.vehiculos);
        combotiempodepago=(Spinner)findViewById(R.id.tiempodepago);
        texvehiculo=(EditText)findViewById(R.id.agrevehiculo);
        texmetodo=(EditText)findViewById(R.id.metparqueadero);
        textprecio=(EditText)findViewById(R.id.precio);
        textprecio2=(EditText)findViewById(R.id.precio2);

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
                if(i==5){
                    texvehiculo.setVisibility(View.VISIBLE);
                    texmetodo.setVisibility(View.VISIBLE);
                    textprecio2.setVisibility(View.VISIBLE);
                    textprecio2.setEnabled(true);
                    combotiempodepago.setEnabled(false);
                    combotiempodepago.setSelection(0);
                    textprecio.setText("");
                    textprecio.setEnabled(false);
                    otros= i;
                }else if(!(i==5)){
                    texvehiculo.setVisibility(View.INVISIBLE);
                    texmetodo.setVisibility(View.INVISIBLE);
                    textprecio2.setVisibility(View.INVISIBLE);
                    textprecio.setEnabled(true);
                    combotiempodepago.setEnabled(true);
                    otros= i;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Este metodo no se esta utilizandio
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
    public void guardar(View view){
            baseDatos();
            limpiar();
    }
    public void baseDatos(){
        if(otros==5){
            AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();

            ContentValues insetar = new ContentValues();
            insetar.put(Utilidades.TIPOS_NAME,texvehiculo.getText().toString());
            bd.insert(Utilidades.TABLA_TIPOS_VEHICULOS, null, insetar);
            bd.close();
        }else if (!(otros==5)){

            if(otros==1){
                AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this);
                SQLiteDatabase bd = admin.getWritableDatabase();

                ContentValues insetar = new ContentValues();
                insetar.put(Utilidades.TIPOS_NAME,combovehiculo.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_IMG, R.drawable.coche_basic);
                insetar.put(Utilidades.TIPOS_MODO,combotiempodepago.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_PRECIO,textprecio.getText().toString());
                bd.insert(Utilidades.TABLA_TIPOS_VEHICULOS, null, insetar);
                bd.close();

            }else if(otros==2){
                AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this);
                SQLiteDatabase bd = admin.getWritableDatabase();

                ContentValues insetar = new ContentValues();
                insetar.put(Utilidades.TIPOS_NAME,combovehiculo.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_IMG, R.drawable.coche_basic);
                insetar.put(Utilidades.TIPOS_MODO,combotiempodepago.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_PRECIO,textprecio.getText().toString());
                bd.insert(Utilidades.TABLA_TIPOS_VEHICULOS, null, insetar);
                bd.close();
            }else if(otros==3){
                AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this);
                SQLiteDatabase bd = admin.getWritableDatabase();

                ContentValues insetar = new ContentValues();
                insetar.put(Utilidades.TIPOS_NAME,combovehiculo.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_IMG, R.drawable.motocicleta_basic);
                insetar.put(Utilidades.TIPOS_MODO,combotiempodepago.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_PRECIO,textprecio.getText().toString());
                bd.insert(Utilidades.TABLA_TIPOS_VEHICULOS, null, insetar);
                bd.close();
            }else if(otros==4){
                AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this);
                SQLiteDatabase bd = admin.getWritableDatabase();

                ContentValues insetar = new ContentValues();
                insetar.put(Utilidades.TIPOS_NAME,combovehiculo.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_IMG, R.drawable.bicicleta_basic);
                insetar.put(Utilidades.TIPOS_MODO,combotiempodepago.getSelectedItem().toString());
                insetar.put(Utilidades.TIPOS_PRECIO,textprecio.getText().toString());
                bd.insert(Utilidades.TABLA_TIPOS_VEHICULOS, null, insetar);
                bd.close();
            }
        }
    }
    public void limpiar(){
        if(!(otros==5)){
            combovehiculo.setSelection(0);
            combotiempodepago.setSelection(0);
            textprecio.setText("");
        }else if(otros==5){
            texvehiculo.setText("");
            texmetodo.setText("");
            textprecio.setText("");
        }
    }
}
