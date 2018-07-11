package com.example.fabik.parkingapp.Printer.utils;

import android.util.Log;

/**
 * Created by vincent on 2017/8/2.
 * description:Logger Util
 */

public class LogUtil {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    public static final int NOTHING = 6;
    private static final String TAG="posTag";
    public static int level = VERBOSE;
    public static void v(String message){
        if (level <= VERBOSE)
            Log.d(TAG,message);
    }
    public static void d(String message){
        if (level<=DEBUG)
            Log.e(TAG,message);
    }
    public static void i(String message){
        if (level<=INFO)
            Log.i(TAG,message);
    }
    public static void w(String message){
        if (level<=WARN)
            Log.w(TAG,message);
    }
    public static void e(String message){
        if (level<=ERROR)
            Log.e(TAG,message);
    }
}
