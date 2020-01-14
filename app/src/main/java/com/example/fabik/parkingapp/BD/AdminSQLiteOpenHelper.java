package com.example.fabik.parkingapp.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Modelos.TipoVehiculos;
import com.example.fabik.parkingapp.TiposDeVehiculosActivity;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private static int DATABASE_VERSION = 2;


    public AdminSQLiteOpenHelper(Context context) {
        super(context, "ParkingApp", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_ING);
        db.execSQL(Utilidades.CREAR_TABLA_SAL);
        db.execSQL(Utilidades.CREAR_TABLA_TIPO);
        db.execSQL(Utilidades.InsertVehiculo);
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


}
