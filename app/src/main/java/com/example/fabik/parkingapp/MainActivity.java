package com.example.fabik.parkingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.etPlaca);
        et2 = findViewById(R.id.etHoraIngreso);



    }

    public void btnAtras (View view){
        Intent intent = new Intent(this, MenuPrincipal.class);
        startActivity(intent);
    }


}
