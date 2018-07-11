package com.example.fabik.parkingapp.Printer;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by vincent on 2018/2/7.
 * description: presenter class.
 */

public class Presenter {
    public final static  int MSG_TITLE = 1;
    public final static int MSG_RESULT = 2;
    public final static int MSG_CONTENT = 3;


    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;
    private  View view;
    private viewInterface listener;
    private Context mContext  = null;
    private boolean isResult = false;
    private boolean isChooseCam  = false;
    public int testTimes = 1;//test times of insert card/nfc/magcard


    public Presenter(viewInterface listener, Context context){
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        this.listener = listener;
    }


    /************printer***************/
    public void printHanlde(){
        listener.showMsg("Printer",MSG_TITLE);
        PrintManager.getInstance().start(listener);
    }
}
