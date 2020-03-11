package com.example.fabik.parkingapp.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Modelos.Comercio;
import com.example.fabik.parkingapp.Modelos.TipoVehiculos;
import com.example.fabik.parkingapp.TiposDeVehiculosActivity;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 2;


    public AdminSQLiteOpenHelper(Context context) {
        super(context, "ParkingApp", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_ING);
        db.execSQL(Utilidades.CREAR_TABLA_SAL);
        db.execSQL(Utilidades.CREAR_TABLA_TIPO);
        db.execSQL(Utilidades.CREAR_TABLA_COMERCIO);
        db.execSQL("INSERT INTO comercio (id) VALUES (" + Utilidades.idComercio  + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_1);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_2);
        onCreate(db);
    }

    public ArrayList<TipoVehiculos> tiposDeVehiculos() {
        ArrayList<TipoVehiculos> tipos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Utilidades.TABLA_TIPOS_VEHICULOS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do{
                tipos.add(new TipoVehiculos(cursor.getInt(0), cursor.getString(1),
                        cursor.getInt(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tipos;
    }

    public Comercio getComercioBD () {
        Comercio comercio = new Comercio();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + Utilidades.TABLE_COMERCIO + " WHERE "
                + Utilidades.comercio_id + " = " + Utilidades.idComercio;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            comercio.setId(cursor.getInt(cursor.getColumnIndex(Utilidades.comercio_id)));
            comercio.setName(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_name)));
            comercio.setDocumento(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_documento)));
            comercio.setDireccion(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_direccion)));
            comercio.setCiudad(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_ciudad)));
            comercio.setEstado(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_estado)));
            comercio.setPais(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_pais)));
            comercio.setTelefono1(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_telefono1)));
            comercio.setTelefono2(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_telefono2)));
            comercio.setHeader1(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_header1)));
            comercio.setHeader2(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_header2)));
            comercio.setFooting1(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_footing1)));
            comercio.setFooting2(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_footing2)));
            comercio.setMoneda(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_moneda)));
            comercio.setSimboloMoneda(cursor.getString(cursor.getColumnIndex(Utilidades.comercio_simboloMoneda)));
        }

        return comercio;
    }

    public boolean updateColumnStringComercio (String column, String dato) {
        boolean ret = false;
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(column, dato);

        String where = Utilidades.comercio_id + "=" + Utilidades.idComercio;
        int rta = bd.update(Utilidades.TABLE_COMERCIO, values, where, null);
        if (rta != -1) {
            ret = true;
        }
        bd.close();
        return ret;

    }


}
