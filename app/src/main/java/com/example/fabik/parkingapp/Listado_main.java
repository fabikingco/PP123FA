package com.example.fabik.parkingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.ArrayList;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actyvity_listado_main);


        lista = getListIngresados();
        edPlaca = findViewById(R.id.tiTitulo);
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
/*
        if (lista.size() == 0) {
            showCard(null, Type_Card.TYPE_NEW);
        }*/

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fbtn.isExpanded()) {
                    /*hideCard(cardType_View, false);*/

                } else {
                    showCard(null, Type_Card.TYPE_NEW);
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCard(Type_Card.TYPE_NEW, false);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          /*      Mensaje m = getMensajeCard(null);
                if (m != null) {
                    lista.add(m);
                    hideCard(Type_Card.TYPE_NEW, false);
                }*/
            }
        });
    }

    private void showCard(String mensaje, Type_Card type) {

        if (mensaje == null) {

        }
        switch (type) {
            case TYPE_EDIT:

                break;
            case TYPE_NEW:
                fbtn.setExpanded(true);
                edPlaca.requestFocus();
                imm.showSoftInput(edPlaca, InputMethodManager.SHOW_IMPLICIT);
                appbar.setVisibility(View.GONE);
                break;
            case TYPE_VIEW:
                fbtn.setExpanded(true);

                btnOk.setVisibility(View.GONE);

                break;
        }
    }

    private void hideCard(Type_Card oldType, boolean isSaveTempo) {
        switch (oldType) {
            case TYPE_NEW:
            case TYPE_EDIT:
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
                break;
            case TYPE_VIEW:
                fbtn.setExpanded(false);
                appbar.setVisibility(View.VISIBLE);

                break;

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


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
