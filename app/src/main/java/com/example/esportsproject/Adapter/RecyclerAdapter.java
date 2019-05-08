package com.example.esportsproject.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.esportsproject.DetailFragment;
import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.example.esportsproject.Global.NotificationSave;
import com.example.esportsproject.Notification.JobSchedulerStart;
import com.example.esportsproject.R;
import com.example.esportsproject.Util.DetailsTransition;
import com.example.esportsproject.Util.UtcToLocal;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter {
    Context mContext;
    private List<String> mItemList;
    private List<Match> matchList;
    HashMap notificationSave;
    private HashMap<String, View> notiList = new HashMap<>();

    private final UtcToLocal utcToLocal = UtcToLocal.getUtcToLocal();

    public RecyclerAdapter(List<String>itemList, ArrayList matchList, HashMap notificationSave) {
        this.notificationSave = notificationSave;
        this.matchList = matchList;
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item,viewGroup,false);
        VH vh = new VH(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final VH holder = (VH) viewHolder;
        final String team1Name,team2Name,team1Img,team2Img,matchId;
        if(matchList.get(i).getOpponents().size() == 2){
            team1Name = matchList.get(i).getOpponents().get(0).getOpponent().getName();
            team1Img = matchList.get(i).getOpponents().get(0).getOpponent().getImage_url().toString();
            team2Name = matchList.get(i).getOpponents().get(1).getOpponent().getName();
            team2Img = matchList.get(i).getOpponents().get(1).getOpponent().getImage_url().toString();

        }else if(matchList.get(i).getOpponents().size() == 1){
            team1Name = matchList.get(i).getOpponents().get(0).getOpponent().getName();
            team1Img = matchList.get(i).getOpponents().get(0).getOpponent().getImage_url().toString();
            team2Name = "TBD";
            team2Img = "http://dipdoo.dothome.co.kr/Esports/question_mark.png";

        }else{
            team1Name = "TBD";
            team2Name = "TBD";
            team1Img = "http://dipdoo.dothome.co.kr/Esports/question_mark.png";
            team2Img = "http://dipdoo.dothome.co.kr/Esports/question_mark.png";
        }
        matchId = matchList.get(i).getId()+"";
        holder.team1Name.setText(team1Name);
        holder.team2Name.setText(team2Name);
        holder.match_name.setText(matchList.get(i).getName());
        Glide.with(mContext).load(team1Img).into(holder.team1Img);
        Glide.with(mContext).load(team2Img).into(holder.team2Img);
        final String begin_at = matchList.get(i).getBegin_at();
        final String detail_begin_at = utcToLocal.dettailTime(begin_at);
        holder.timeText.setText(detail_begin_at);
        holder.setNoti(begin_at,detail_begin_at,team1Name,team2Name,team1Img,team2Img,matchId);
        holder.setFragmentClickListener();
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DetailFragment detailFragment = DetailFragment.getInstance();
//                FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
//                fm.beginTransaction().addToBackStack(null).replace(R.id.container,detailFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        boolean isNotiCheck = false;
        public TextView team1Name,team2Name,timeText,match_name;
        public CircleImageView team1Img;
        public CircleImageView team2Img;
        public ImageView noti;

        public VH(@NonNull final View itemView) {
            super(itemView);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            team1Img = itemView.findViewById(R.id.team1img);
            team2Img = itemView.findViewById(R.id.team2img);
            timeText = itemView.findViewById(R.id.begin_at);
            noti = itemView.findViewById(R.id.iv_noti);
            match_name = itemView.findViewById(R.id.tv_result);
        }

        public void setFragmentClickListener(){


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailFragment detailFragment = DetailFragment.getInstance();
                    FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.container,detailFragment)
                            .addToBackStack("ss")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
                    fm.removeOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                        @Override
                        public void onBackStackChanged() {
                            Toast.makeText(mContext, "11", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        public void setNoti(final String begin_at, final String detail_begin_at, final String team1Name, final String team2Name, final String team1Img, final String team2Img, final String matchId){

            if(notificationSave.containsKey(begin_at+matchId)){
                isNotiCheck = (Boolean) notificationSave.get(begin_at+matchId);
                Log.d("d",isNotiCheck+"");
            }
            if(isNotiCheck){
                noti.setBackgroundResource(R.drawable.setnotiimg);
            }
            if(utcToLocal.tiemToMill(begin_at)-System.currentTimeMillis()<=0){
                noti.setVisibility(View.GONE);
            }

            noti.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(isNotiCheck){
                                Toast.makeText(mContext, "알림이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mContext));
                                dispatcher.cancel(begin_at+matchId);
                                isNotiCheck = false;
                                noti.setBackgroundResource((R.drawable.notification));
                                notificationSave.remove(begin_at+matchId);
                                return;
                            }
                            long now = System.currentTimeMillis();
                            if(utcToLocal.tiemToMill(begin_at)-now <= 0){
                                isNotiCheck =false;
                                return;
                            }
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                String chaanelId = "Ch_1";
                                CharSequence channelName = "Ch_name";
                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                NotificationChannel channel = new NotificationChannel(chaanelId,channelName,importance);
                                NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);
                            }
                            long notiOffset = utcToLocal.tiemToMill(begin_at) - now;
                            Toast.makeText(mContext, detail_begin_at+"알람이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                            JobSchedulerStart.start(mContext,notiOffset,begin_at,team1Name,team2Name,team1Img,team2Img,matchId);
                            noti.setBackgroundResource(R.drawable.setnotiimg);
                            isNotiCheck = true;
                            notificationSave.put(begin_at+matchId,isNotiCheck);
                        }
            });
        }
    }
}
