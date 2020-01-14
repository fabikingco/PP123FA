package com.example.fabik.parkingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Modelos.Ingresados;

import java.util.ArrayList;

public class ListarVehiculos extends AppCompatActivity {

    ListView listViewIngresados;
    ArrayList<String> listaVehiculos;
    ArrayList<Ingresados> listaIngresados;

    AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_vehiculos);

        admin= new AdminSQLiteOpenHelper(this);
        listViewIngresados = findViewById(R.id.lv1);

        consultarListaVehiculos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaVehiculos);
        listViewIngresados.setAdapter(adaptador);

    }

    private void consultarListaVehiculos() {
        SQLiteDatabase db=admin.getReadableDatabase();

        Ingresados ingresados=null;
        listaIngresados=new ArrayList<Ingresados>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_1,null);

        while (cursor.moveToNext()){
            ingresados = new Ingresados();
            ingresados.setId(cursor.getInt(0));
            ingresados.setPlaca(cursor.getString(1));
            ingresados.setFecha_ing(cursor.getString(2));
            ingresados.setTipo(cursor.getString(3));

            listaIngresados.add(ingresados);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaVehiculos=new ArrayList<String>();

        for (int i=0; i<listaIngresados.size();i++){
            listaVehiculos.add(listaIngresados.get(i).getId()+" - "
                    +listaIngresados.get(i).getPlaca()+" - "
                    +listaIngresados.get(i).getTipo());
        }
    }
}
