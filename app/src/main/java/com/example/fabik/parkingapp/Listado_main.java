package com.example.fabik.parkingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fabik.parkingapp.Adaptadores.Adapter_Listado_main;
import com.example.fabik.parkingapp.BD_Utilidades.Utilidades;
import com.example.fabik.parkingapp.Entidades.Ingresados;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transformation.TransformationChildCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Listado_main extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {


    private RecyclerView recy;
    private View btnOk;
    private FloatingActionButton fbtn;
    private TextView tvvacio;
    private InputMethodManager imm;
    private BottomAppBar appbar;
    private List<Ingresados> lista;
    private EditText edPlaca;
    private RadioGroup radioGroup;
    private Ingresados ingresoTemporal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actyvity_listado_main);


        lista = getListIngresados();
        edPlaca = findViewById(R.id.tiTitulo);
        radioGroup = findViewById(R.id.rGroup);

        recy = findViewById(R.id.rcy01);
        recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        Adapter_Listado_main adapter = new Adapter_Listado_main(this, lista, R.layout.item_listado_main);
        recy.setAdapter(adapter);


        View closeButton = findViewById(R.id.close_button);
        TransformationChildCard sheet = findViewById(R.id.sheet);
        btnOk = findViewById(R.id.btnOk);
        fbtn = findViewById(R.id.fbtn);

        tvvacio = findViewById(R.id.tvVacio);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        appbar = findViewById(R.id.appbar);
        appbar.setOnMenuItemClickListener(this);


        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbtn.isExpanded()) {
                    hideCard();

                } else {
                    showCard();
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCard();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresoTemporal = getIngreso();
                if (ingresoTemporal != null) {
                    guardarIngrefo_db(ingresoTemporal);
                    hideCard();
                }

            }
        });
    }

    private void showCard() {

        fbtn.setExpanded(true);
        edPlaca.requestFocus();
        imm.showSoftInput(edPlaca, InputMethodManager.SHOW_IMPLICIT);
        appbar.setVisibility(View.GONE);

    }

    private void hideCard() {

        View view = Listado_main.this.getCurrentFocus();
        view.clearFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        edPlaca.setText("");
        fbtn.setExpanded(false);
        appbar.setVisibility(View.VISIBLE);
        if (lista.size() == 0) {
            tvvacio.setVisibility(View.VISIBLE);
        } else {
            tvvacio.setVisibility(View.GONE);
        }


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
        return listaIngresados;
    }


    private Ingresados getIngreso() {
        Ingresados result = new Ingresados();
        String temporal;
        temporal = edPlaca.getText().toString();
        if (temporal.isEmpty()) {

            Toast.makeText(Listado_main.this, "Digite placa por favor", Toast.LENGTH_SHORT).show();

            return null;
        } else {
            result.setPlaca(temporal);
        }

        RadioButton rb = findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb == null) {
            Toast.makeText(Listado_main.this, "Seleccione tipo de vehículo", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            temporal = rb.getText().toString();
            result.setTipo(temporal);
        }
        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Calendar c = Calendar.getInstance();
        temporal = outputFormat.format(c.getTime());
        result.setFecha_ing(temporal);
        return result;
    }


    private void guardarIngrefo_db(Ingresados obj) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues insetar = new ContentValues();
        insetar.put(Utilidades.CAMPO_PLACA, obj.getPlaca());
        insetar.put(Utilidades.CAMPO_FECHA_ING, obj.getFecha_ing());
        insetar.put(Utilidades.CAMPO_TIPO, obj.getTipo());
        bd.insert(Utilidades.TABLA_1, null, insetar);
        bd.close();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


}
