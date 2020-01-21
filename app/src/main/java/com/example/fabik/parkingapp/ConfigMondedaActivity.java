package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fabik.parkingapp.BD.AdminSQLiteOpenHelper;
import com.example.fabik.parkingapp.BD.Utilidades;
import com.example.fabik.parkingapp.Fragments.DialogDataFragment;

import static com.example.fabik.parkingapp.appInit.sComercio;

public class ConfigMondedaActivity extends AppCompatActivity {

    public static final int DIALOG_QUEST_CODE = 300;

    String moneda, simbolo;
    boolean isCentavos;

    LinearLayout lyMoneda, lySimbolo;
    TextView tvMoneda, tvSimbolo;
    Switch swCentavos;

    AdminSQLiteOpenHelper bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_moneda);

        bd = new AdminSQLiteOpenHelper(this);

        initWidgets();
        onClickLinears();

        if (sComercio.getMoneda() != null) {
            moneda = sComercio.getMoneda();
            tvMoneda.setText(sComercio.getMoneda());
        }
        if (sComercio.getSimboloMoneda() != null) {
            simbolo = sComercio.getSimboloMoneda();
            tvSimbolo.setText(sComercio.getSimboloMoneda());
        }
        isCentavos = sComercio.isCentavos();


    }

    private void onClickLinears() {
        lyMoneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (moneda != null) {
                    moneda = "Euros";
                }
                DialogDataFragment  dialog = DialogDataFragment.newInstance("Moneda", Utils.checkNull(moneda));
                dialog.setRequestCode(DIALOG_QUEST_CODE);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, dialog).addToBackStack(null).commit();
                dialog.setmListener(new DialogDataFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onFragmentInteraction(int requestCode, Object obj) {
                        if (bd.updateColumnStringComercio(Utilidades.comercio_moneda, obj.toString())) {
                            appInit.cargarComercio(ConfigMondedaActivity.this);
                            tvMoneda.setText(obj.toString());
                        }

                    }
                });

            }
        });

        lySimbolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (simbolo != null) {
                    simbolo = "€";
                }
                DialogDataFragment  dialog = DialogDataFragment.newInstance("Simbolo de moneda", Utils.checkNull(simbolo));
                dialog.setRequestCode(DIALOG_QUEST_CODE);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, dialog).addToBackStack(null).commit();
                dialog.setmListener(new DialogDataFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onFragmentInteraction(int requestCode, Object obj) {
                        if (bd.updateColumnStringComercio(Utilidades.comercio_moneda, obj.toString())) {
                            appInit.cargarComercio(ConfigMondedaActivity.this);
                            tvMoneda.setText(obj.toString());
                        }

                    }
                });
            }
        });
    }

    private void initWidgets() {
        lyMoneda = findViewById(R.id.lyMoneda);
        lySimbolo = findViewById(R.id.lySimbolo);
        tvMoneda = findViewById(R.id.tvMoneda);
        tvSimbolo = findViewById(R.id.tvSimbolo);
        swCentavos = findViewById(R.id.swCentavos);
    }
}
