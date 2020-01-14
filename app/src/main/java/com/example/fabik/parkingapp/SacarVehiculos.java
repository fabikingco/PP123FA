package com.example.fabik.parkingapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Printer.Presenter;
import com.example.fabik.parkingapp.Printer.PrintManager;
import com.example.fabik.parkingapp.Printer.viewInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SacarVehiculos extends AppCompatActivity {

    EditText placa;

    private Presenter presenter;
    public static viewInterface listener; //Para la impresion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacar_vehiculos);

        presenter= new Presenter(callback,this); //Inicializar impresora

        setTitle("Sacar Vehiculos");


    }

    public void salida (View view){
        placa = findViewById(R.id.et_s_Placa);
        Global.Placa = placa.getText().toString();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] pl ={Global.Placa};

        try {
            Cursor cursor=bd.rawQuery("SELECT "+ Utilidades.CAMPO_ID + ","+ Utilidades.CAMPO_FECHA_ING+","+Utilidades.CAMPO_TIPO+
                    " FROM "+Utilidades.TABLA_1+" WHERE "+Utilidades.CAMPO_PLACA+"=? ",pl);

            cursor.moveToFirst();
            Global.Id = cursor.getString(0);
            Global.FechaIngreso = cursor.getString(1);
            Global.Tipo = cursor.getString(2);

            Toast.makeText(getApplicationContext(),"Placa encontrada",Toast.LENGTH_LONG).show();
            bd.close();
            AlertDialog();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Placa no encontrada",Toast.LENGTH_LONG).show();
            bd.close();
        }



        //getDateAsTime(Global.FechaIngreso); //Llamo al metodo
    }

    public void AlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingreso de Vehiculo");
        builder.setMessage("Vehiculo "+Global.Placa+" encontrado, ingreso "+Global.FechaIngreso+" y es de tipo "+Global.Tipo);
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String outputPattern = "yyyy:MM:dd HH:mm:ss";
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Calendar c = Calendar.getInstance();
                        Global.FechaSalida = outputFormat.format(c.getTime());

                        System.out.println("Fecha Ingreso "+Global.FechaIngreso);

                        System.out.println("Fecha Salida "+Global.FechaSalida);

                        getDateAsTime();

                        System.out.println("Tiempo "+Global.TiempoTotal);


                        ValorAPagar();
                        AlertDialog2();


                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(SacarVehiculos.this, SacarVehiculos.class);
                        startActivity(intent);
                    }
                });

        builder.show();
    }

    public void AlertDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Facturacion");
        builder.setMessage("Vehiculo "+Global.Placa+" estuvo "+Global.Minutos+" minutos  y debe pagar la suma de $"+Global.ValorAPagar+" por el minuto a $125");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrintManager.getInstance().ImpresionTiqueteSalida(listener, null);
                        Toast.makeText(SacarVehiculos.this, "IMPRIMIENDO FACTURA", Toast.LENGTH_SHORT).show();
                        EliminarAgregar();
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(SacarVehiculos.this, SacarVehiculos.class);
                        startActivity(intent);
                    }
                });

        builder.show();
    }

    public void ValorAPagar(){
        int ValorMinuto = 125;

        Global.ValorAPagar= Global.Minutos * ValorMinuto;
    }

    public void EliminarAgregar(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete(Utilidades.TABLA_1, Utilidades.CAMPO_ID+"=" + Global.Id, null);

        if (cant == 1) {
            Toast.makeText(this, "Se elimino el registro de la base de datos",
                    Toast.LENGTH_SHORT).show();

            ContentValues insetar = new ContentValues();
            insetar.put(Utilidades.CAMPO_PLACA2, Global.Placa);
            insetar.put(Utilidades.CAMPO_FECHA_ING2, Global.FechaIngreso);
            insetar.put(Utilidades.CAMPO_FECHA_SAL2, Global.FechaSalida);
            insetar.put(Utilidades.CAMPO_TIPO2, Global.Tipo);
            insetar.put(Utilidades.CAMPO_PAGO2, Global.ValorAPagar);
            insetar.put(Utilidades.CAMPO_MINUTO2, Global.Minutos);

            bd.insert(Utilidades.TABLA_2, null, insetar);
            bd.close();

        }else {
            Toast.makeText(this, "No exite ese articulo",
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    private String getDateAsTime() {
        Global.TiempoTotal = "";
        long day = 0, diff = 0, minutos = 0;

        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
/*        Calendar c = Calendar.getInstance();
        Global.FechaSalida = outputFormat.format(c.getTime());
        */

        try {
            Date date1 = outputFormat.parse(Global.FechaIngreso);
            Date date2 = outputFormat.parse(Global.FechaSalida);
            diff = date2.getTime() - date1.getTime();
            day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            minutos = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            Global.Minutos = (int) minutos;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day == 0) {
            long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            if (hour == 0)
                Global.TiempoTotal = String.valueOf(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)); //Minutos
            else
                Global.TiempoTotal = String.valueOf(hour); //Horas
        } else {
            Global.TiempoTotal = String.valueOf(day); //Dias
        }
        return Global.TiempoTotal;
    }

    //Metodo para diferencia de Dates Original
    private String getDateAsTime2() {
        Global.TiempoTotal = "";
        long day = 0, diff = 0;

        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
/*        Calendar c = Calendar.getInstance();
        Global.FechaSalida = outputFormat.format(c.getTime());
        */

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

    public viewInterface callback = new viewInterface() {
        @Override
        public void showMsg(String msg, int mode) {

        }
    };
}
