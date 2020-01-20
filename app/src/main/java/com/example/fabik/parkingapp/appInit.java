package com.example.fabik.parkingapp;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.fabik.parkingapp.Modelos.Comercio;
import com.example.fabik.parkingapp.Printer.PrintManager;
import com.example.fabik.parkingapp.Printer.utils.LogUtil;

import com.mazenrashed.printooth.Printooth;
import com.pos.device.SDKManager;
import com.pos.device.SDKManagerCallback;

/**
 * Creado por Fabian Ardila
 */

public class appInit extends Application {

    public static Context appContext;
    public static Comercio sComercio;

    @Override
    public void onCreate() {
        super.onCreate();
        cargarComercio();
        init();

    }

    private void cargarComercio() {

    }

    private void init() {
        System.out.println("MODELO " + Build.MODEL);
        if (Build.MODEL.equals("NEW9220")) {
            appContext = getApplicationContext();
            SDKManager.init(appInit.appContext, new SDKManagerCallback() {
                @Override
                public void onFinish() {
                    LogUtil.e("init SDK success(threadname: " + Thread.currentThread().getName() + ")");
                }
            });
            PrintManager.init(getApplicationContext());
        } else if (Build.MODEL.equals("Android SDK built for x86")){
            Log.d("Modelo sin impresion", "No se impreme");
        } else if (Build.MODEL.equals("i80")) {
            Log.d("POS SUNYARD", "No carga info");
        }
        else {
            Printooth.INSTANCE.init(this);
        }

    }

}
