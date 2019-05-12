package com.example.esportsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.esportsproject.FirebaseBoard.FirebaseConnect;
import com.example.esportsproject.NestedScrollingView.LoremIpsumAdapter;

public class DetailFragment extends Fragment implements View.OnClickListener {

    String game_id;

    public static DetailFragment getInstance(String team1Img,String team2Img,String team1name, String team2Name, String status, String matchName, String slug,String game_id){
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("team1",team1Img);
        bundle.putString("team2",team2Img);
        bundle.putString("team1Name",team1name);
        bundle.putString("team2Name",team2Name);
        bundle.putString("status",status);
        bundle.putString("matchName",matchName);
        bundle.putString("slug",slug);
        bundle.putString("game_id",game_id);
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
        Bundle bundle = getArguments();
        ImageView team1View = view.findViewById(R.id.detail_team1img);
        ImageView team2View = view.findViewById(R.id.detail_tema2img);
        TextView team1NameView = view.findViewById(R.id.detail_tema1Name);
        TextView team2NameView = view.findViewById(R.id.detail_tema2Name);
        TextView statusView = view.findViewById(R.id.detail_status);
        TextView matchNameView = view.findViewById(R.id.detail_status);
        TextView slugView = view.findViewById(R.id.detail_slug);
        TextView team1VoteView = view.findViewById(R.id.detail_team1vote);
        TextView team2VoteView = view.findViewById(R.id.detail_team2vote);
        TextView team1percent = view.findViewById(R.id.detail_tema1_percent);
        TextView team2percent = view.findViewById(R.id.detail_tema2_percent);

        String team1  = bundle.getString("team1","null1");
        String team2  = bundle.getString("team2","null2");
        game_id = bundle.getString("game_id");
        team1VoteView.setTag(bundle.getString("team1Name"));
        team2VoteView.setTag(bundle.getString("team2Name"));
        team1VoteView.setOnClickListener(this);
        team2VoteView.setOnClickListener(this);

        Glide.with(getContext()).load(team1).into(team1View);
        Glide.with(getContext()).load(team2).into(team2View);
        team1NameView.setText(bundle.getString("team1Name"));
        team2NameView.setText(bundle.getString("team2Name"));
        statusView.setText(bundle.getString("status"));
        slugView.setText(bundle.getString("slug"));

        FirebaseConnect.getFirebaseConnect().getVoteView(team1VoteView,team2VoteView,game_id,team1percent,team2percent);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        ScrollNested

        RecyclerView rv = view.findViewById(R.id.detail_board);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setAdapter(new LoremIpsumAdapter(getContext()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(),lm.getOrientation()));

    }


    @Override
    public void onClick(View v) {
        String teamName;
        switch (v.getId()){
            case R.id.detail_team1vote:
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team1Vote");
                break;
            case R.id.detail_team2vote:
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team2Vote");
        }
    }
}
