package com.example.esportsproject.Notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobService;

public class NotificationJobFireBaseService extends JobService {
    @Override
    public boolean onStartJob(@NonNull final com.firebase.jobdispatcher.JobParameters job) {

                AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Bundle bundle = job.getExtras();
                int matchid = bundle.getInt("notiid");
                long notiLong = bundle.getLong("time");

                Intent intent = new Intent(NotificationJobFireBaseService.this,AlarmBrodcastReciever.class);
                intent.putExtras(bundle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationJobFireBaseService.this,matchid,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),pendingIntent);
                }else{
                    manager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),pendingIntent);
                }
                return false;
            }

            @Override
            public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {

                return false;//리스케쥴 필요
            }

}
