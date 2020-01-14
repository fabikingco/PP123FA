package com.example.fabik.parkingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Modelos.Facturados;

import java.util.ArrayList;

public class ListadoTotal extends AppCompatActivity {

    ListView listViewFacturados;
    ArrayList<String> listaVehiculos;
    ArrayList<Facturados> listaFacturados;

    AdminSQLiteOpenHelper admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_total);

        admin= new AdminSQLiteOpenHelper(this);
        listViewFacturados = findViewById(R.id.lv1);

        consultarListaVehiculos();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaVehiculos);
        listViewFacturados.setAdapter(adaptador);

    }

    private void consultarListaVehiculos() {
        SQLiteDatabase db=admin.getReadableDatabase();

        Facturados facturados=null;
        listaFacturados=new ArrayList<Facturados>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_2,null);

        while (cursor.moveToNext()){
            facturados = new Facturados();
            facturados.setId(cursor.getInt(0));
            facturados.setPlaca(cursor.getString(1));
            facturados.setFecha_ing(cursor.getString(2));
            facturados.setFecha_sal(cursor.getString(3));
            facturados.setTipo(cursor.getString(4));
            facturados.setValor(cursor.getInt(5));
            facturados.setMinutos(cursor.getInt(6));

            listaFacturados.add(facturados);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaVehiculos=new ArrayList<String>();

        for (int i=0; i<listaFacturados.size();i++){
            listaVehiculos.add(listaFacturados.get(i).getId()+" - "
                    +listaFacturados.get(i).getPlaca()+" - "
                    +listaFacturados.get(i).getValor());
        }
    }
}
