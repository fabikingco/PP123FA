package com.example.fabik.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

public class Utils {

    public static String checkNull(String strText) {
        if (strText == null) {
            strText = "   ";
        }
        return strText;
    }


}
