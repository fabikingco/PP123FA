package com.example.fabik.parkingapp.BD_Utilidades;

public class Utilidades {

    //Campos constantes de la base de datos
    public static final String TABLA_1 = "ingreso";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_PLACA = "placa";
    public static final String CAMPO_FECHA_ING = "fecha_ing";
    public static final String CAMPO_TIPO = "tipo";

    public static final String CREAR_TABLA_ING =
            "CREATE TABLE " +TABLA_1+" " +
                    "("+CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_PLACA+" TEXT,"+CAMPO_FECHA_ING+" TEXT, "+CAMPO_TIPO+" TEXT)";


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
            "CREATE TABLE " +TABLA_2+" " +
                    "("+CAMPO_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_PLACA2+" TEXT,"+CAMPO_FECHA_ING2+" TEXT, "
                    +CAMPO_FECHA_SAL2 +" TEXT, "+CAMPO_TIPO2+" TEXT, " + CAMPO_PAGO2+" INTEGER, "+CAMPO_MINUTO2+" INTEGER)";

}



