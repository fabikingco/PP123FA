package com.example.fabik.parkingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.fabik.parkingapp.BD_Utilidades.Utilidades;
import com.example.fabik.parkingapp.Global;

public class SacarVehiculos extends AppCompatActivity {

    EditText placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacar_vehiculos);

        setTitle("Sacar Vehiculos");


    }

    public void salida (View view){
        placa = findViewById(R.id.et_i_Placa);
        Global.Placa = placa.getText().toString();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] pl ={Global.Placa};

        try {
            Cursor cursor=bd.rawQuery("SELECT "+ Utilidades.CAMPO_FECHA_ING+","+Utilidades.CAMPO_TIPO+
                    " FROM "+Utilidades.TABLA_1+" WHERE "+Utilidades.CAMPO_PLACA+"=? ",pl);

            cursor.moveToFirst();
            Global.FechaIngreso = cursor.getString(0);
            Global.Tipo = cursor.getString(1);
            bd.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Placa no encontrada",Toast.LENGTH_LONG).show();
            bd.close();
        }

        getDateAsTime(Global.FechaIngreso); //Llamo al metodo
    }

    private String getDateAsTime(String datePrev) {
        Global.TiempoTotal = "";
        long day = 0, diff = 0;
        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Calendar c = Calendar.getInstance();
        Global.FechaSalida = outputFormat.format(c.getTime());
        try {
            Date date1 = outputFormat.parse(Global.FechaIngreso);
            Date date2 = outputFormat.parse(Global.FechaSalida);
            diff = date2.getTime() - date1.getTime();
            day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day == 0) {
            long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            if (hour == 0)
                Global.TiempoTotal = String.valueOf(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)).concat(" minutes ago");
            else
                Global.TiempoTotal = String.valueOf(hour).concat(" hours ago");
        } else {
            Global.TiempoTotal = String.valueOf(day).concat(" days ago");
        }
        return Global.TiempoTotal;
    }
}
