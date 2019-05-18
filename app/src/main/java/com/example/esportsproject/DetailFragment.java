package com.example.esportsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.esportsproject.Adapter.DetailAdapter;
import com.example.esportsproject.Adapter.RecyclerAdapter;
import com.example.esportsproject.FirebaseBoard.FirebaseConnect;
import com.example.esportsproject.Global.NotificationSave;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements View.OnClickListener {

    String game_id;
    private boolean mIsShowingCardHeaderShadow;


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
        View view =LayoutInflater.from(container.getContext()).inflate(R.layout.detail_fragment,container,false);
        setHasOptionsMenu(true);

        RecyclerView detail_recycler = view.findViewById(R.id.detail_recyclerview);
        setupRecyclerView(detail_recycler);
        return view;

    }
    private void setupRecyclerView(RecyclerView recyclerView){
        ArrayList<String>testString = new ArrayList<>();
        for(int i =0; i<10; i++){
            testString.add("aa");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DetailAdapter recyclerAdapter = new DetailAdapter(testString,getContext() );
        recyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_write:
//                    다이얼로그를 만들어서 send할때 firebased에 가고 여기는 다이얼로그
               //FirebaseConnect.getFirebaseConnect().writeToBoard(game_id);
                createWriteFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createWriteFragment(){
        WriteFragment writeFragment = WriteFragment.getInstance();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().addToBackStack(null).add(R.id.detail_fragment_container,writeFragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
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
        Toolbar toolbar = view.findViewById(R.id.detail_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

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

    }



    @Override
    public void onClick(View v) {
        String teamName;
        switch (v.getId()){
            case R.id.detail_team1vote:
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team1Name");
                break;
            case R.id.detail_team2vote:
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team2Name");
        }
    }
}
