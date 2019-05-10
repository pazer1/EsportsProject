package com.example.esportsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.esportsproject.Firebase.FirebaseDB;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailFragment extends Fragment {


    public static DetailFragment getInstance(String team1Img,String team2Img,String team1name, String team2Name, String status, String matchName, String slug,String gameId){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("team1",team1Img);
        bundle.putString("team2",team2Img);
        bundle.putString("team1Name",team1name);
        bundle.putString("team2Name",team2Name);
        bundle.putString("status",status);
        bundle.putString("matchName",matchName);
        bundle.putString("slug",slug);
        bundle.putString("gameId",gameId);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.detail_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        ImageView team1View = view.findViewById(R.id.detail_team1img);
        ImageView team2View = view.findViewById(R.id.detail_tema2img);
        TextView team1NameView = view.findViewById(R.id.detail_tema1Name);
        TextView team2NameView = view.findViewById(R.id.detail_tema2Name);
        TextView statusView = view.findViewById(R.id.detail_status);
        TextView matchNameView = view.findViewById(R.id.detail_status);
        TextView slugView = view.findViewById(R.id.detail_slug);
        ImageView voteView1 = view.findViewById(R.id.detail_team1vote);
        ImageView voteView2 = view.findViewById(R.id.detail_team1vote);

        String team1  = bundle.getString("team1","null1");
        String team2  = bundle.getString("team2","null2");
        Glide.with(getContext()).load(team1).into(team1View);
        Glide.with(getContext()).load(team2).into(team2View);
        team1NameView.setText(bundle.getString("team1Name"));
        team2NameView.setText(bundle.getString("team2Name"));
        statusView.setText(bundle.getString("status"));
        slugView.setText(bundle.getString("slug"));
        voteView1.setTag(bundle.getString("team1Name"));
        voteView2.setTag(bundle.getString("team2Name"));
        voteView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                vote에 태그를 줘서 태그로 처리하자. team1Name? id를 줘서
                FirebaseDB.getInstance().setVote(bundle.getString("gameId"),(String)view.getTag());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
