package com.example.fabik.parkingapp.BD_Utilidades;

public class Utilidades {

    //Campos constantes de la base de datos
    public static final String TABLA_1 = "facturacion";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOM = "nombre";
    public static final String CAMPO_DOC = "documento";
    public static final String CAMPO_PRO = "producto";
    public static final String CAMPO_VAL = "valor";

    public static final String CREAR_TABLA_FAC =
            "CREATE TABLE " +TABLA_1+" " +
                    "("+CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOM+" TEXT,"+CAMPO_DOC+" INTEGER, "+CAMPO_PRO+" TEXT, "+CAMPO_VAL+" INTEGER)";
}
