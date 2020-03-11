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
    public static final String TIPOS_MODO = "modo"; //Segundos, minutos, hora, dia, semana, mes
    public static final String TIPOS_PRECIO = "precio";

    public static final String CREAR_TABLA_TIPO =
            "CREATE TABLE " + TABLA_TIPOS_VEHICULOS +
                    " (" + TIPOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIPOS_NAME + " TEXT," +
                    TIPOS_IMG + " INTEGER, " +
                    TIPOS_MODO + " TEXT," +
                    TIPOS_PRECIO + " TEXT)";

    public static final String TABLE_COMERCIO = "comercio";

    public static final String comercio_id = "id";
    public static final String comercio_name = "name";
    public static final String comercio_documento = "documento";
    public static final String comercio_direccion = "direccion";
    public static final String comercio_ciudad = "ciudad";
    public static final String comercio_estado = "estado";
    public static final String comercio_pais = "pais";
    public static final String comercio_telefono1 = "telefono1";
    public static final String comercio_telefono2 = "telefono2";
    public static final String comercio_header1 = "header1";
    public static final String comercio_header2 = "header2";
    public static final String comercio_footing1 = "footing1";
    public static final String comercio_footing2 = "footing2";
    public static final String comercio_moneda = "moneda";
    public static final String comercio_simboloMoneda = "simboloMoneda";
    public static final String comercio_centavos = "centavos";

    public static final String CREAR_TABLA_COMERCIO =
            "CREATE TABLE " + TABLE_COMERCIO +
                    " (" + comercio_id + " INTEGER PRIMARY KEY, " +
                    comercio_name + " TEXT, " +
                    comercio_documento + " TEXT, " +
                    comercio_direccion + " TEXT, " +
                    comercio_ciudad + " TEXT, " +
                    comercio_estado + " TEXT, " +
                    comercio_pais + " TEXT, " +
                    comercio_telefono1 + " TEXT, " +
                    comercio_telefono2 + " TEXT, " +
                    comercio_header1 + " TEXT, " +
                    comercio_header2 + " TEXT, " +
                    comercio_footing1 + " TEXT, " +
                    comercio_footing2 + " TEXT, " +
                    comercio_moneda + " TEXT, " +
                    comercio_simboloMoneda + " TEXT, " +
                    comercio_centavos + " INTEGER)";

    public static final int idComercio = 1;  //INSERT INTO comercio (id) VALUES (1);
    // SELECT * FROM comercio WHERE id = 1;

    // UPDATE comercio
    //SET name = "Parqueadero Bucaramanga"
    //WHERE
    //id = 1;




}



