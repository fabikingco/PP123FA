package com.example.fabik.parkingapp.BD_Utilidades;

public class Utilidades {

    //Campos constantes de la base de datos
    public static final String TABLA_1 = "ingreso";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_PLACA = "placa";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_HORA = "hora";

    public static final String CREAR_TABLA_FAC =
            "CREATE TABLE " +TABLA_1+" " +
                    "("+CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_PLACA+" TEXT,"+CAMPO_FECHA+" DATE, "+CAMPO_HORA+" INTEGER)";
}
