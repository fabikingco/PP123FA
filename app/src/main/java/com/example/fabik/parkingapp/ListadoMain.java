package com.example.fabik.parkingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fabik.parkingapp.Adaptadores.AdaptadorListaMain;
import com.example.fabik.parkingapp.Adaptadores.RecyOnItemClickListener;
import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Modelos.Facturados;
import com.example.fabik.parkingapp.Modelos.Ingresados;
import com.example.fabik.parkingapp.Fragments.BottomNavigationDrawerFragment;
import com.example.fabik.parkingapp.Printer.PrintManager;
import com.example.fabik.parkingapp.Printer.utils.LogUtil;
import com.example.fabik.parkingapp.Printer.viewInterface;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.converter.ArabicConverter;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;
import com.pos.device.SDKManager;
import com.pos.device.SDKManagerCallback;

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
    private ImageView imageView, imgStatus;
    private TextView tvConexion;

    private Printing printing = null;
    PrintingCallback printingCallback = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_main);
        if (!Build.MODEL.equals("NEW9220") && !Build.MODEL.equals("Android SDK built for x86") && !Build.MODEL.equals("i80")) {
            if (Printooth.INSTANCE.hasPairedPrinter())
                printing = Printooth.INSTANCE.printer();
        }


        imageView = findViewById(R.id.imagenPrincipal);

        initStatusPrinter();
        initListeners();

        tvConexion = findViewById(R.id.tvConexion);
        tvConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Build.MODEL.equals("NEW9220") && !Build.MODEL.equals("Android SDK built for x86") && !Build.MODEL.equals("i80")) {
                    if (Printooth.INSTANCE.hasPairedPrinter()) {
                        Printooth.INSTANCE.removeCurrentPrinter();
                        imgStatus.setImageDrawable(getDrawable(R.drawable.ic_remove_circle_red));
                    } else {
                        startActivityForResult(new Intent(ListadoMain.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                    }
                }
            }
        });

        listaVehiculoIngresados = getListIngresados();
        recyclerView = findViewById(R.id.rcy01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        cargarAdaptador(listaVehiculoIngresados);
        FloatingActionButton floatingActionButton = findViewById(R.id.fbtn);
        setUpBottomAppBar();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //printSomePrintable();
                startActivity(new Intent(ListadoMain.this, IngresarVehiculos.class));
            }
        });
    }

    private void initStatusPrinter() {
        imgStatus = findViewById(R.id.imgStatus);
        if (Build.MODEL.equals("NEW9220") || Build.MODEL.equals("Android SDK built for x86") || Build.MODEL.equals("i80")) {
            imgStatus.setImageDrawable(getDrawable(R.drawable.ic_check_circle_green));
        } else {
            if (Printooth.INSTANCE.getPairedPrinter() != null) {
                if (Printooth.INSTANCE.hasPairedPrinter()) {
                    imgStatus.setImageDrawable(getDrawable(R.drawable.ic_check_circle_green));
                }
            }
        }


    }

    private void setUpBottomAppBar() {
        bottomAppBar = findViewById(R.id.appbar);

        setSupportActionBar(bottomAppBar);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String mensaje = "";
                switch (item.getItemId()) {
                    case R.id.buscar:
                        mensaje = "buscar";
                        break;
                    case R.id.filter:
                        startActivity(new Intent(ListadoMain.this, ConfiguracionActivity.class));
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

        SQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
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
                        dialog.dismiss();

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    public void ValorAPagar(Facturados facturados) {
        facturados.setValor_min(125);
        facturados.setValor(facturados.getMinutos() * facturados.getValor_min());
    }


    public void EliminarAgregar(Facturados facturados) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
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
        AdaptadorListaMain adapter = new AdaptadorListaMain(this, lista, R.layout.item_listado_main, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("xxx", "onActivityResult "+requestCode);

        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK) {
            initListeners();
            printSomePrintable();
        }
        initStatusPrinter();
    }

    private void initListeners() {
        if (printing!=null && printingCallback==null) {
            Log.d("xxx", "initListeners ");
            printingCallback = new PrintingCallback() {

                public void connectingWithPrinter() {
                    Toast.makeText(getApplicationContext(), "Connecting with printer", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "Connecting");
                }
                public void printingOrderSentSuccessfully() {
                    Toast.makeText(getApplicationContext(), "printingOrderSentSuccessfully", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "printingOrderSentSuccessfully");
                }
                public void connectionFailed(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "connectionFailed :"+error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "connectionFailed : "+error);
                }
                public void onError(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "onError :"+error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onError : "+error);
                }
                public void onMessage(@NonNull String message) {
                    Toast.makeText(getApplicationContext(), "onMessage :" +message, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onMessage : "+message);
                }
            };

            Printooth.INSTANCE.printer().setPrintingCallback(printingCallback);
        }
    }

    private void printSomePrintable() {
        Log.d("xxx", "printSomePrintable ");
        printing.print(getSomePrintables());
//        if (printing!=null)
//            printing.print(getSomePrintables());
    }

    private ArrayList<Printable> getSomePrintables() {
        ArrayList<Printable> al = new ArrayList<>();
        al.add(new RawPrintable.Builder(new byte[]{27, 100, 4}).build()); // feed lines example in raw mode

        al.add( (new TextPrintable.Builder())
                .setText("Prueba de impresión ")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        al.add( (new TextPrintable.Builder())
                .setText("Prueba de impresión ")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC437())
                .setNewLinesAfter(1)
                .build());

        al.add( (new TextPrintable.Builder())
                .setText(" Hello World : été è à '€' içi Bò Xào Coi Xanh")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        al.add( (new TextPrintable.Builder())
                .setText("Hello World : été è à €")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        al.add( (new TextPrintable.Builder())
                .setText("Hello World")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        al.add( (new TextPrintable.Builder())
                .setText("Hello World")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_RIGHT())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1)
                .build());

        al.add( (new TextPrintable.Builder())
                .setText("اختبار العربية")
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setFontSize(DefaultPrinter.Companion.getFONT_SIZE_NORMAL())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_ARABIC_FARISI())
                .setNewLinesAfter(1)
                .setCustomConverter(new ArabicConverter()) // change only the converter for this one
                .build());

        return al;
    }
}
