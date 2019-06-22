package com.dipdoo.esportsproject.Util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UtcToLocal {

    private static UtcToLocal utcToLocal;

    public static UtcToLocal getUtcToLocal(){
        if(utcToLocal == null)utcToLocal=new UtcToLocal();
        return  utcToLocal;
    }

    public String getTime(String changeTime, Context context){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd");

        TimeZone tz = TimeZone.getDefault();
        String locTime;
        try {
            Date date = inputFormat.parse(changeTime);
            //Locale systemLocale = context.getResources().getConfiguration().locale;
            SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.getDefault());

            long milliseconds = date.getTime();
            int offset = tz.getOffset(milliseconds);
            locTime= outputFormat.format(milliseconds+offset);
            String dayOfTheWeek = sdf.format(milliseconds+offset);
            return locTime+"("+dayOfTheWeek+")";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public String getTimeSystem(String changeTime, Context context){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd");

        String locTime;
        try {
            Date date = inputFormat.parse(changeTime);
            //Locale systemLocale = context.getResources().getConfiguration().locale;
            SimpleDateFormat sdf = new SimpleDateFormat("E",Locale.getDefault());
            String dayOfTheWeek = sdf.format(date);
            long milliseconds = date.getTime();
            locTime= outputFormat.format(milliseconds);
            return locTime+"("+dayOfTheWeek+")";
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getCurrentTime(long pluseTominus){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        TimeZone tz = TimeZone.getDefault();
        long milliseconds = System.currentTimeMillis();
        int offset = tz.getOffset(milliseconds);
        long forthDay = (1000*60*60*24) *2;
        forthDay = forthDay*pluseTominus;
        String currenttime = simpleDateFormat.format(milliseconds+forthDay);
        return String.valueOf(currenttime);
    }


    public String getTimeMDHM(){
        SimpleDateFormat outputFormat = new SimpleDateFormat("M-dd HH:mm");
        String locTime;
        long milliseconds = System.currentTimeMillis();
        locTime= outputFormat.format(milliseconds);
        return locTime;
    }

    public String dettailTime(String changeTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("aa hh:mm");
        TimeZone tz = TimeZone.getDefault();
        String locTime;
        try {
            Date date = inputFormat.parse(changeTime);
            long milliseconds = date.getTime();
            int offset = tz.getOffset(milliseconds);
            locTime= outputFormat.format(milliseconds+offset);
            return locTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public long tiemToMill(String changeTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM-dd HH:mm");
        TimeZone tz = TimeZone.getDefault();
        String locTime;
        try {
            Date date = inputFormat.parse(changeTime);
            long milliseconds = date.getTime();
            int offset = tz.getOffset(milliseconds);
            long timeToMill = milliseconds+offset;
            return timeToMill;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        }
}
