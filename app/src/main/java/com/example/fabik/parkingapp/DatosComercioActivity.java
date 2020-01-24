package com.example.fabik.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Fragments.DialogDataFragment;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.fabik.parkingapp.appInit.sComercio;

public class DatosComercioActivity extends AppCompatActivity {

    public static final int DIALOG_QUEST_CODE = 300;

    private String[] country_list;
    LinearLayout    lyNombre, lyDocumento, lyDireccion, lyPais, lyEstado, lyCiudad, lyTelefono1, lyTelefono2;
    TextView        tvNombre, tvDocumento, tvDireccion, tvPais, tvEstado, tvCiudad, tvTelefono1, tvTelefono2;

    AdminSQLiteOpenHelper bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_comercio);

        bd = new AdminSQLiteOpenHelper(this);

        //https://github.com/mukeshsolanki/country-picker-android
        country_list = getResources().getStringArray(R.array.countries);

        initWidgets();
        cargarDatosDelComercioActual();
    }

    private void cargarDatosDelComercioActual() {
        if (sComercio.getName() != null)
            tvNombre.setText(sComercio.getName());
        if (sComercio.getDocumento() != null)
            tvDocumento.setText(sComercio.getDocumento());
        if (sComercio.getDireccion() != null)
            tvDireccion.setText(sComercio.getDireccion());
        if (sComercio.getPais() != null)
            tvPais.setText(sComercio.getPais());
        if (sComercio.getEstado() != null)
            tvEstado.setText(sComercio.getEstado());
        if (sComercio.getCiudad() != null)
            tvCiudad.setText(sComercio.getCiudad());
        if (sComercio.getTelefono1() != null)
            tvTelefono1.setText(sComercio.getTelefono1());
        if (sComercio.getTelefono2() != null)
            tvTelefono2.setText(sComercio.getTelefono2());
    }

    private void initWidgets() {
        lyNombre    = findViewById(R.id.lyNombre);
        lyDocumento = findViewById(R.id.lyDocumento);
        lyDireccion = findViewById(R.id.lyDireccion);
        lyPais      = findViewById(R.id.lyPais);
        lyEstado    = findViewById(R.id.lyEstado);
        lyCiudad    = findViewById(R.id.lyCiudad);
        lyTelefono1 = findViewById(R.id.lyTelefono1);
        lyTelefono2 = findViewById(R.id.lyTelefono2);
        tvNombre    = findViewById(R.id.tvNombre);
        tvDocumento = findViewById(R.id.tvDocumento);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvPais      = findViewById(R.id.tvPais);
        tvEstado    = findViewById(R.id.tvEstado);
        tvCiudad    = findViewById(R.id.tvCiudad);
        tvTelefono1 = findViewById(R.id.tvTelefono1);
        tvTelefono2 = findViewById(R.id.tvTelefono2);
    }

    public void onClickDatosComercio(View view) {
        switch (view.getId()){
            case R.id.lyNombre:
                cargarFragment(sComercio.getName(), "Nombre",
                        "Nombre del comercio", Utilidades.comercio_name, tvNombre);
                break;
            case R.id.lyDocumento:
                cargarFragment(sComercio.getDocumento(), "Documento",
                        "Documento del comercio", Utilidades.comercio_documento, tvDocumento);
                break;
            case R.id.lyDireccion:
                cargarFragment(sComercio.getDireccion(), "Direccion",
                        "Dirección del comercio", Utilidades.comercio_direccion, tvDireccion);
                break;
            case R.id.lyPais:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setSingleChoiceItems(country_list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (bd.updateColumnStringComercio(Utilidades.comercio_pais, country_list[which])) {
                            appInit.cargarComercio(DatosComercioActivity.this);
                            tvPais.setText(country_list[which]);
                        } else {
                            Toast.makeText(DatosComercioActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.lyEstado:
                cargarFragment(sComercio.getEstado(), "Estado",
                        "Estado o departamento", Utilidades.comercio_estado, tvEstado);
                break;
            case R.id.lyCiudad:
                cargarFragment(sComercio.getCiudad(), "Ciudad",
                        "Ciudad del comercio", Utilidades.comercio_ciudad, tvCiudad);
                break;
            case R.id.lyTelefono1:
                cargarFragment(sComercio.getTelefono1(), "Telefono",
                        "Numero de telefono 1", Utilidades.comercio_telefono1, tvTelefono1);
                break;
            case R.id.lyTelefono2:
                cargarFragment(sComercio.getTelefono2(), "Telefono",
                        "Numero de telefono 2", Utilidades.comercio_telefono2, tvTelefono2);
                break;

        }
    }

    public void cargarFragment(String data, String valueDefault, String titulo, String columnUpdate, TextView textView) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (data != null) {
            data = valueDefault;
        }
        DialogDataFragment dialog = DialogDataFragment.newInstance(titulo, Utils.checkNull(data));
        dialog.setRequestCode(DIALOG_QUEST_CODE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, dialog).addToBackStack(null).commit();
        dialog.setmListener(new DialogDataFragment.OnFragmentInteractionListener() {
            @Override
            public void onFragmentInteraction(int requestCode, Object obj) {
                if (bd.updateColumnStringComercio(columnUpdate, obj.toString())) {
                    appInit.cargarComercio(DatosComercioActivity.this);
                    textView.setText(obj.toString());
                }

            }
        });
    }
}
