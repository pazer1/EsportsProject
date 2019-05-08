package com.example.esportsproject.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.example.esportsproject.Adapter.RecyclerAdapter;
import com.example.esportsproject.Global.IsIntalled;
import com.example.esportsproject.R;

public class AlarmBrodcastReciever extends BroadcastReceiver {

    private static int NOTIFICATION_ID = 222;
    private final static String NOTIGROUP_KEY="ESPORTS";
    private static String channelId = "esports_noti";
    private static String chnnelName = "esports";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String team1Name = bundle.getString("team1Name");
        String team1Img = bundle.getString("team1Img");
        String team2Name = bundle.getString("team2Name");
        String team2Img = bundle.getString("team2Img");
        int notiCode = bundle.getInt("notiid");
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_item);
        notificationLayout.setTextViewText(R.id.team1Name,team1Name);
        notificationLayout.setTextViewText(R.id.team2Name,team2Name);
        notificationLayout.setImageViewUri(R.id.team1img, Uri.parse(team1Img));
        notificationLayout.setImageViewUri(R.id.team2img, Uri.parse(team2Img));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(channelId,chnnelName,importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId);
        if(IsIntalled.getInstance().isTwitch()) {
            String uri = "twitch://stream/LCK_Korea";
            Intent twitchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,twitchIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
        }
        builder.setVibrate(new long[]{1000,2000});

        Notification notification = builder
                .setSmallIcon(R.drawable.notification)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .build();

        NotificationTarget notificationTarget = new NotificationTarget(
                context,
                R.id.team1img,
                notificationLayout,
                notification,
                notiCode);

        NotificationTarget notificationTarget2 = new NotificationTarget(
                context,
                R.id.team2img,
                notificationLayout,
                notification,
                notiCode);

        Glide.with(context)
                .asBitmap()
                .load(team1Img)
                .into( notificationTarget );


        Glide.with(context)
                .asBitmap()
                .load(team2Img)
                .into( notificationTarget2);


        notificationManager.notify(notiCode,notification);
    }
}
