package com.example.fabik.parkingapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fabik.parkingapp.Adaptadores.Adapter_Listado_main;
import com.example.fabik.parkingapp.Adaptadores.RecyOnItemClickListener;
import com.example.fabik.parkingapp.BD_Utilidades.Utilidades;
import com.example.fabik.parkingapp.Entidades.Facturados;
import com.example.fabik.parkingapp.Entidades.Ingresados;
import com.example.fabik.parkingapp.Printer.PrintManager;
import com.example.fabik.parkingapp.Printer.viewInterface;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListadoMain extends AppCompatActivity implements RecyOnItemClickListener {


    private RecyclerView recyclerView;
    private BottomAppBar bottomAppBar;
    private List<Ingresados> listaVehiculoIngresados;
    public static viewInterface listener;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_main);
        imageView = findViewById(R.id.imagenPrincipal);
        listaVehiculoIngresados = getListIngresados();
        recyclerView = findViewById(R.id.rcy01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        cargarAdaptador(listaVehiculoIngresados);
        FloatingActionButton floatingActionButton = findViewById(R.id.fbtn);
        setUpBottomAppBar();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListadoMain.this, IngresarVehiculos.class));
            }
        });
    }

    private void setUpBottomAppBar() {
        bottomAppBar = findViewById(R.id.appbar);

        setSupportActionBar(bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String mensaje;
                switch (item.getItemId()) {
                    case R.id.home:
                        mensaje = "home";
                        break;
                    case R.id.buscar:
                        mensaje = "buscar";
                        break;
                    case R.id.filter:
                        mensaje = "filter";
                        break;
                    default:
                        mensaje=" desconocido";
                }
                Toast.makeText(ListadoMain.this, mensaje, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private List<Ingresados> getListIngresados() {
        List<Ingresados> listaIngresados = new ArrayList<>();

        SQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Ingresados ingresados = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_1, null);

        while (cursor.moveToNext()) {
            ingresados = new Ingresados();
            ingresados.setId(cursor.getInt(0));
            ingresados.setPlaca(cursor.getString(1));
            ingresados.setFecha_ing(cursor.getString(2));
            ingresados.setTipo(cursor.getString(3));

            listaIngresados.add(ingresados);
        }
        if (listaIngresados.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        return listaIngresados;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this, "Prueba menu", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Otra opcion", Toast.LENGTH_SHORT).show();
        }
        return true;
    }




    @Override
    public void onclick(Ingresados obj) {
        AlertDialog(obj);

    }

    public void AlertDialog(final Ingresados obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingreso de Vehiculo");
        builder.setMessage("Vehiculo " + obj.getPlaca() + " encontrado, ingreso " + obj.getFecha_ing() + " y es de tipo " + obj.getTipo());
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Facturados facturados = new Facturados(obj.getId(), obj.getPlaca(), obj.getFecha_ing(), obj.getTipo());
                        String outputPattern = "yyyy:MM:dd HH:mm:ss";
                        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
                        Calendar c = Calendar.getInstance();
                        facturados.setFecha_sal(outputFormat.format(c.getTime()));
                        getDateAsTime(facturados);
                        ValorAPagar(facturados);
                        AlertDialog2(facturados);


                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

        builder.show();
    }


    private void getDateAsTime(Facturados facturados) {

        long day = 0, diff = 0, minutos = 0;

        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
/*        Calendar c = Calendar.getInstance();
        Global.FechaSalida = outputFormat.format(c.getTime());
        */

        try {
            Date date1 = outputFormat.parse(facturados.getFecha_ing());
            Date date2 = outputFormat.parse(facturados.getFecha_sal());
            diff = date2.getTime() - date1.getTime();
            day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            minutos = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            facturados.setMinutos((int) minutos);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day == 0) {
            long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            if (hour == 0)
                facturados.setTiempo_total(String.valueOf(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)));  //Minutos
            else
                facturados.setTiempo_total(String.valueOf(hour)); //Horas
        } else {
            facturados.setTiempo_total(String.valueOf(day)); //Dias
        }

    }


    public void AlertDialog2(final Facturados facturados) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Facturacion");
        builder.setMessage("Vehiculo " + facturados.getPlaca() + " estuvo " + facturados.getMinutos() + " minutos  y debe pagar la suma de $" + facturados.getValor() + " por el minuto a $" + facturados.getValor_min());
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrintManager.getInstance().ImpresionTiqueteSalida(listener, facturados);
                        Toast.makeText(ListadoMain.this, "IMPRIMIENDO FACTURA", Toast.LENGTH_SHORT).show();
                        EliminarAgregar(facturados);
                        cargarAdaptador(getListIngresados());


                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();


                    }
                });

        builder.show();
    }

    public void ValorAPagar(Facturados facturados) {
        facturados.setValor_min(125);
        facturados.setValor(facturados.getMinutos() * facturados.getValor_min());
    }


    public void EliminarAgregar(Facturados facturados) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete(Utilidades.TABLA_1, Utilidades.CAMPO_ID + "=" + facturados.getId(), null);

        if (cant == 1) {

            ContentValues insetar = new ContentValues();
            insetar.put(Utilidades.CAMPO_PLACA2, facturados.getPlaca());
            insetar.put(Utilidades.CAMPO_FECHA_ING2, facturados.getFecha_ing());
            insetar.put(Utilidades.CAMPO_FECHA_SAL2, facturados.getFecha_sal());
            insetar.put(Utilidades.CAMPO_TIPO2, facturados.getTipo());
            insetar.put(Utilidades.CAMPO_PAGO2, facturados.getValor());
            insetar.put(Utilidades.CAMPO_MINUTO2, facturados.getMinutos());
            try {
                bd.insert(Utilidades.TABLA_2, null, insetar);
                Toast.makeText(this, "Se elimino el registro de la base de datos",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Error al eliminar",
                        Toast.LENGTH_SHORT).show();
            }
            bd.close();

        } else {
            Toast.makeText(this, "No exite ese articulo",
                    Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    private void cargarAdaptador(List<Ingresados> lista) {
        Adapter_Listado_main adapter = new Adapter_Listado_main(this, lista, R.layout.item_listado_main, this);
        recyclerView.setAdapter(adapter);
    }
}
