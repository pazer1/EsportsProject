package com.dipdoo.esportsproject.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.transition.Fade;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dipdoo.esportsproject.DetailFragment;
import com.dipdoo.esportsproject.Global.IsIntalled;
import com.dipdoo.esportsproject.Global.Match;
import com.dipdoo.esportsproject.Notification.AlarmBrodcastReciever;
import com.dipdoo.esportsproject.Notification.JobSchedulerStart;
import com.dipdoo.esportsproject.R;
import com.dipdoo.esportsproject.Util.DetailsTransition;
import com.dipdoo.esportsproject.Util.UtcToLocal;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.ALARM_SERVICE;

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
        for (int i = 0; i < matchList.size(); i++){
            Match match = (Match)matchList.get(i);
        }


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item,viewGroup,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final VH holder = (VH) viewHolder;
        final String team1Name,team2Name,team1Img,team2Img,matchId,status;
        if(matchList.size()<=0){
            return;
        }
        if(matchList.get(i).getOpponents().size() == 2){
            if(matchList.get(i).getOpponents().get(0).getOpponent().getName() != null){
                team1Name = matchList.get(i).getOpponents().get(0).getOpponent().getName();
            }else{
                team1Name ="TBD";
            }
            if(matchList.get(i).getOpponents().get(0).getOpponent().getImage_url() != null){
                team1Img = matchList.get(i).getOpponents().get(0).getOpponent().getImage_url().toString();
            }else{
                team1Img="null";

            }

            team2Name = matchList.get(i).getOpponents().get(1).getOpponent().getName();
            if(matchList.get(i).getOpponents().get(1).getOpponent().getImage_url() != null){
                team2Img = matchList.get(i).getOpponents().get(1).getOpponent().getImage_url().toString();

            }else{
                team2Img="null";
            }

        }else if(matchList.get(i).getOpponents().size() == 1){
            team1Name = matchList.get(i).getOpponents().get(0).getOpponent().getName();
            team1Img = matchList.get(i).getOpponents().get(0).getOpponent().getImage_url().toString();
            team2Name = "TBD";
            team2Img = "null";

        }else{
            team1Name = "TBD";
            team2Name = "TBD";
            team1Img = "null";
            team2Img = "null";
        }
        matchId = matchList.get(i).getId()+"";
        holder.team1Name.setText(team1Name);
        holder.team2Name.setText(team2Name);
        holder.match_name.setText(matchList.get(i).getName());
        holder.twitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsIntalled.getInstance().isTwitch()){
                    String uri = "twitch://stream/LCK_Korea";
                    Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                    mContext.startActivity(i);
//                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("tv.twitch.android.app");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(inteLnt);
                }else{
                    String url = "market://details?id=" + "tv.twitch.android.app";
//                    String url ="market://details?id="+"kr.co.nowcom.mobile.afreeca";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mContext.startActivity(i);
                }
            }
        });
        status = matchList.get(i).getStatus();

        if(status.equals("running")){
            holder.noti.setVisibility(View.GONE);
            holder.score_container.setVisibility(View.VISIBLE);
            holder.tv_score1.setText("게임중입니다.");
            holder.tv_score2.setText("");
        }

        holder.score_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("finished")){
                    String team1Score,team2Score;
                    int team1Id = matchList.get(i).getOpponents().get(0).getOpponent().getId();
                    int team1ScoreId = matchList.get(i).getResults().get(0).getTeam_id();
                    //int team2ScoreId = matchList.get(i).getResults().get(1).getTeam_id();
                    if(team1Id == team1ScoreId){
                        team1Score = matchList.get(i).getOpponents().get(0).getOpponent().getName() +":"+ matchList.get(i).getResults().get(0).getScore();
                        team2Score = matchList.get(i).getOpponents().get(1).getOpponent().getName() +":"+ matchList.get(i).getResults().get(1).getScore();
                    }else{
                        team1Score = matchList.get(i).getOpponents().get(0).getOpponent().getName() +":"+ matchList.get(i).getResults().get(1).getScore();
                        team2Score = matchList.get(i).getOpponents().get(1).getOpponent().getName() +":"+ matchList.get(i).getResults().get(0).getScore();
                    }
                    if(Integer.parseInt(team1Score.split(":")[1])>Integer.parseInt(team2Score.split(":")[1]))
                        holder.tv_score1.setTextColor(mContext.getResources().getColor(R.color.status_live_backgroud));
                    else
                        holder.tv_score2.setTextColor(mContext.getResources().getColor(R.color.status_live_backgroud));

                    if(team1Score.length()>18){
                        String[] team1Arr = team1Score.split(" ");
                        team1Score = team1Arr[0]+" "+team1Arr[team1Arr.length-1];
                    }
                    if(team2Score.length()>18){
                        String[] team2Arr = team2Score.split(" ");
                        team2Score = team2Arr[0]+" "+team2Arr[team2Arr.length-1];
                    }
                    holder.tv_score1.setText(team1Score);
                    holder.tv_score2.setText(team2Score);
                }
            }
        });


        if(!team1Img.equals("null")) {Glide.with(mContext).load(team1Img).into(holder.team1Img);
        }else{holder.team1Img.setBackgroundResource(R.drawable.qusetion_mark_tbd);}
        if(!team2Img.equals("null")) {Glide.with(mContext).load(team2Img).into(holder.team2Img);
        }else{holder.team2Img.setBackgroundResource(R.drawable.qusetion_mark_tbd);}

        final String begin_at = matchList.get(i).getBegin_at();
        final String detail_begin_at = utcToLocal.dettailTime(begin_at);
        holder.timeText.setText(detail_begin_at);
        holder.setNoti(begin_at,detail_begin_at,team1Name,team2Name,team1Img,team2Img,matchId,status);
        holder.leaguName.setText(matchList.get(i).getLeague().getName());
        holder.setDetailFragment(team1Img,team2Img,team1Name,team2Name,status,matchList.get(i).getTournament().getName(),matchList.get(i).getTournament().getSlug(),String.valueOf(matchList.get(i).getId()),detail_begin_at);


        if(matchList.get(i).getStatus().equals("finished")){
            holder.status_container.setBackgroundColor(mContext.getResources().getColor(R.color.status_finished_background));
            holder.status_text.setTextColor(mContext.getResources().getColor(R.color.status_finished_text));
        }else if(matchList.get(i).getStatus().equals("not_started")){
            holder.status_container.setBackgroundColor(mContext.getResources().getColor(R.color.status_future_background));
            holder.status_text.setTextColor(mContext.getResources().getColor(R.color.staus_future_text));

        }else{
            holder.status_container.setBackgroundColor(mContext.getResources().getColor(R.color.status_live_backgroud));
            holder.status_text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        holder.status_text.setText(matchList.get(i).getStatus());
        holder.game_id.setText(matchId);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        boolean isNotiCheck = false;
        public TextView team1Name,team2Name,timeText,match_name,leaguName;
        public CircleImageView team1Img;
        public CircleImageView team2Img;
        public ImageView noti,twitch;
        public TextView status_text,tv_score1,tv_score2,game_id;
        public View status_container,score_container;

        public VH(@NonNull View itemView) {
            super(itemView);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            team1Img = itemView.findViewById(R.id.team1img);
            team2Img = itemView.findViewById(R.id.team2img);
            timeText = itemView.findViewById(R.id.begin_at);
            noti = itemView.findViewById(R.id.iv_noti);
            match_name = itemView.findViewById(R.id.tv_result);
            twitch = itemView.findViewById(R.id.iv_twitch);
            leaguName = itemView.findViewById(R.id.leaguName);
            status_text = itemView.findViewById(R.id.status_text);
            status_container = itemView.findViewById(R.id.status_container);
            tv_score1 = itemView.findViewById(R.id.tv_score1);
            tv_score2 = itemView.findViewById(R.id.tv_score2);
            score_container = itemView.findViewById(R.id.container_score);
            game_id = itemView.findViewById(R.id.game_id);
        }

        public void setDetailFragment(final String team1Img, final String team2Img, final String team1Name, final String team2Name, final String status, final String matchName, final String slug, final String game_id,final String begin_at){
            itemView.setOnClickListener(new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    DetailFragment detailFragment = DetailFragment.getInstance(team1Img,team2Img,team1Name,team2Name,status,matchName,slug,game_id,begin_at);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        DetailsTransition detailsTransition = new DetailsTransition();
                        detailsTransition.setDuration(1000L);
                        detailsTransition.setStartDelay(1000L);
//                        detailFragment.setSharedElementEnterTransition(detailsTransition.setDuration(300000L));
                        detailFragment.setEnterTransition(new Fade().setDuration(500L));
                        detailFragment.setExitTransition(new Fade().setDuration(500L));
//                        detailFragment.setSharedElementReturnTransition(detailsTransition.setDuration(300000L));
                    }
                    FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
                    fm.beginTransaction().addToBackStack(null).replace(R.id.container,detailFragment)
                           .commit();
                }
            });
        }
//
//        public void setResult(String ){
//            iv_result.setText();
//        }


        public void setNoti(final String begin_at, final String detail_begin_at, final String team1Name, final String team2Name, final String team1Img, final String team2Img, final String matchId,final String status){

            if(notificationSave.containsKey(begin_at+matchId)){
                isNotiCheck = (Boolean) notificationSave.get(begin_at+matchId);
            }
            if(isNotiCheck){
                noti.setBackgroundResource(R.drawable.setnotiimg);
            }
//            if(utcToLocal.tiemToMill(begin_at)-System.currentTimeMillis()<=0){
//                noti.setVisibility(View.GONE);
//                score_container.setVisibility(View.VISIBLE);
//            }
            if(status.equals("finished")){
                noti.setVisibility(View.GONE);
                score_container.setVisibility(View.VISIBLE);
            }else if(status.equals("not_started")){
                noti.setVisibility(View.VISIBLE);
                score_container.setVisibility(View.GONE);
            }
            final FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mContext));
            noti.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long now = System.currentTimeMillis();
                            if(utcToLocal.tiemToMill(begin_at)-now <= 0){
                                isNotiCheck =false;
                                return;
                            }
                            String tag = begin_at+matchId;
                            if(isNotiCheck){
                                Toast.makeText(mContext, "알랑이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                Log.d("cancleTag",tag);
                                isNotiCheck = false;
                                noti.setBackgroundResource((R.drawable.notification));
                                notificationSave.remove(begin_at+matchId);
                                dispatcher.cancel(tag);
                                AlarmManager manager = (AlarmManager)mContext.getSystemService(ALARM_SERVICE);

                                Intent intent = new Intent(mContext, AlarmBrodcastReciever.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,Integer.parseInt(matchId),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                manager.cancel(pendingIntent);
                            }else {
                                long notiOffset = utcToLocal.tiemToMill(begin_at) - now;
                                Toast.makeText(mContext, detail_begin_at+"경기 알람이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                                JobSchedulerStart.start(mContext,notiOffset,begin_at,team1Name,team2Name,team1Img,team2Img,matchId,tag);
                                noti.setBackgroundResource(R.drawable.setnotiimg);
                                isNotiCheck = true;
                                notificationSave.put(begin_at+matchId,isNotiCheck);
                            }
                        }
            });
        }
    }
}
