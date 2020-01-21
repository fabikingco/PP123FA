package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.fabik.parkingapp.Fragments.DialogDataFragment;

import static com.example.fabik.parkingapp.appInit.sComercio;

public class ConfigMondedaActivity extends AppCompatActivity {

    public static final int DIALOG_QUEST_CODE = 300;

    String moneda, simbolo;
    boolean isCentavos;

    LinearLayout lyMoneda, lySimbolo;
    TextView tvMoneda, tvSimbolo;
    Switch swCentavos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_moneda);

        initWidgets();
        onClickLinears();

        if (sComercio.getMoneda() != null) {
            moneda = sComercio.getMoneda();


        }

        if (sComercio.getSimboloMoneda() != null) {
            simbolo = sComercio.getSimboloMoneda();
        }

        isCentavos = sComercio.isCentavos();


    }

    private void onClickLinears() {
        lyMoneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogDataFragment  dialog = DialogDataFragment.newInstance("Parametro 1", "Parametro 2");
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, dialog).addToBackStack(null).commit();



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
