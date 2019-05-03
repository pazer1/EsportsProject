package com.example.esportsproject.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UtcToLocal {

    private static UtcToLocal utcToLocal;

    public static UtcToLocal getUtcToLocal(){
        if(utcToLocal == null)utcToLocal=new UtcToLocal();
        return  utcToLocal;
    }

    public String getTime(String changeTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MM-dd");
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

}
