package com.example.esportsproject.Notification;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class JobSchedulerStart {

    private static final int JOB_ID = 1111;

    public static void start(Context context, long timeTomill, String begin_at, String team1Name, String team2Name, String team1Img, String team2Img,String matchId,String tag){
        Bundle bundle = new Bundle();
        bundle.putLong("time",timeTomill);
        bundle.putString("team1Name",team1Name);
        bundle.putString("team1Img",team1Img);
        bundle.putString("team2Name",team2Name);
        bundle.putString("team2Img",team2Img);
        bundle.putInt("notiid",Integer.parseInt(matchId));

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job myJob = dispatcher.newJobBuilder()
                .setService(NotificationJobFireBaseService.class)
                .setExtras(bundle)
                .setTag(tag)
                .setRecurring(false)
                .setTrigger(Trigger.NOW)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
        dispatcher.mustSchedule(myJob);
    }

}
