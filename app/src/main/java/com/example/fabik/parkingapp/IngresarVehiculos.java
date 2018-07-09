package com.example.fabik.parkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class IngresarVehiculos extends AppCompatActivity {

    TextView tv1;
    private static String primera, segunda, daysAsTime, datePrev;
    private static Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_vehiculos);

        setTitle("Ingresar Vehoculos");

        tv1 = findViewById(R.id.tvHora);

        Date anotherCurDate = new Date();
        SimpleDateFormat formatime = new SimpleDateFormat("HH:mm:ss");
        String formatedTime = formatime.format(anotherCurDate);

        tv1.setText(formatedTime);

        //tv1.setText(String.valueOf(hora)+":"+String.valueOf(minute)+":"+String.valueOf(segundos));

    }

    public void Nuevo_Ingreso(View view) {

        Date anotherCurDate = new Date();
        startDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy, MMMM, d, HH, mm, ss");
        SimpleDateFormat formatime = new SimpleDateFormat("HH:mm:ss");

        String formattedDateString = formatter.format(anotherCurDate);
        String formatedTime = formatime.format(anotherCurDate);

        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Calendar c = Calendar.getInstance();
        datePrev = outputFormat.format(c.getTime());

        tv1.setText(datePrev);


        System.out.println("2. "+anotherCurDate);
        System.out.println("3. "+formattedDateString);
        System.out.println("4. "+formatedTime);
        System.out.println("Prueba "+datePrev);

        //long hours = ChronoUnit.HOURS.between(oldDate, newDate);
        //long minutes = ChronoUnit.MINUTES.between(oldDate, newDate);

    }

    public void Prueba(View view){

        Date currentTime = Calendar.getInstance().getTime();

        System.out.println("5. "+currentTime);

        endDate = new Date();

        long diff=(this.endDate.getTime()-this.startDate.getTime())/(60*60 * 1000);

        getDateAsTime(datePrev);

        System.out.println("6. "+daysAsTime);

        tv1.setText(daysAsTime);
    }

    private String getDateAsTime(String datePrev) {
        daysAsTime = "";
        long day = 0, diff = 0;
        String outputPattern = "yyyy:MM:dd HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Calendar c = Calendar.getInstance();
        String dateCurrent = outputFormat.format(c.getTime());
        try {
            Date  date1 = outputFormat.parse(datePrev);
            Date date2 = outputFormat.parse(dateCurrent);
            diff = date2.getTime() - date1.getTime();
            day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day == 0) {
            long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            if (hour == 0)
                daysAsTime = String.valueOf(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)).concat(" minutes ago");
            else
                daysAsTime = String.valueOf(hour).concat(" hours ago");
        } else {
            daysAsTime = String.valueOf(day).concat(" days ago");
        }
        return daysAsTime;
    }
}
