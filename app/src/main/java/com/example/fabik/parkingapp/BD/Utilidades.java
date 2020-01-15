package com.example.fabik.parkingapp.BD;

import com.example.fabik.parkingapp.R;

public class Utilidades {

    //Campos constantes de la base de datos
    public static final String TABLA_1 = "ingreso";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_PLACA = "placa";
    public static final String CAMPO_FECHA_ING = "fecha_ing";
    public static final String CAMPO_TIPO = "tipo";

    public static final String CREAR_TABLA_ING =
            "CREATE TABLE " + TABLA_1 + " " +
                    "(" + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_PLACA + " TEXT," + CAMPO_FECHA_ING + " TEXT, " + CAMPO_TIPO + " TEXT)";


    //Campos constantes de la base de datos
    public static final String TABLA_2 = "facturacion";
    public static final String CAMPO_ID2 = "id";
    public static final String CAMPO_PLACA2 = "placa";
    public static final String CAMPO_FECHA_ING2 = "fecha_ing";
    public static final String CAMPO_FECHA_SAL2 = "fecha_sal";
    public static final String CAMPO_TIPO2 = "tipo";
    public static final String CAMPO_PAGO2 = "pago";
    public static final String CAMPO_MINUTO2 = "minuto";

    public static final String CREAR_TABLA_SAL =
            "CREATE TABLE " + TABLA_2 + " " +
                    "(" + CAMPO_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_PLACA2 + " TEXT," +
                    CAMPO_FECHA_ING2 + " TEXT, " +
                    CAMPO_FECHA_SAL2 + " TEXT, " +
                    CAMPO_TIPO2 + " TEXT, " +
                    CAMPO_PAGO2 + " TEXT, " +
                    CAMPO_MINUTO2 + " TEXT)";

    public static final String TABLA_TIPOS_VEHICULOS = "tipos_vehiculos";
    public static final String TIPOS_ID = "id";
    public static final String TIPOS_NAME = "name";
    public static final String TIPOS_IMG = "img";
    public static final String TIPOS_MODO = "modo";
    public static final String TIPOS_PRECIO = "precio";

    public static final String CREAR_TABLA_TIPO =
            "CREATE TABLE " + TABLA_TIPOS_VEHICULOS +
                    " (" + TIPOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIPOS_NAME + " TEXT," +
                    TIPOS_IMG + " INTEGER, " +
                    TIPOS_MODO + " TEXT," +
                    TIPOS_PRECIO + " TEXT)";


    public static final String InsertVehiculo = "INSERT INTO " + TABLA_TIPOS_VEHICULOS + " (" + TIPOS_NAME + ", " + TIPOS_IMG + ", " + TIPOS_MODO + ", " + TIPOS_PRECIO +
            ") VALUES ('Automovil', " + R.drawable.coche_basic + ", 'HORA', '2000');";

    public static final String TABLE_COMERCIO = "comercio";
    public static final String COMERCIO_ID = "id";
    public static final String COMERCIO_NAME = "name";
    public static final String COMERCIO_PAIS = "pais";
    public static final String COMERCIO_MONEDA = "moneda";

}



