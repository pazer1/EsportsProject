package com.dipdoo.esportsproject.Notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobService;

public class NotificationJobFireBaseService extends JobService {

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(@NonNull final com.firebase.jobdispatcher.JobParameters job) {

        mBackgroundTask = new AsyncTask() {
                    @SuppressLint("WrongThread")
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        Bundle bundle = job.getExtras();
                        int matchid = bundle.getInt("notiid");
                        long notiLong = bundle.getLong("time");
                        Log.d("asd",String.valueOf(notiLong));
                        Intent intent = new Intent(NotificationJobFireBaseService.this,AlarmBrodcastReciever.class);
                        intent.putExtras(bundle);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationJobFireBaseService.this,matchid,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+notiLong,pendingIntent);
                        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+notiLong,pendingIntent);
                        }else{
                            manager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+notiLong,pendingIntent);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                    }
                };
                mBackgroundTask.execute();
                return false;
            }

            @Override
            public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
                jobFinished(job,false);
                return false;//리스케쥴 필요
            }

}
