package com.dipdoo.esportsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dipdoo.esportsproject.FirebaseBoard.FirebaseConnect;
import com.github.abdularis.civ.CircleImageView;

public class DetailFragment extends Fragment implements View.OnClickListener {

    String game_id,status,team1Name,team2Name;
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

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =LayoutInflater.from(container.getContext()).inflate(R.layout.detail_fragment,container,false);

        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        menu.getItem(0).setVisible(false);
        setHasOptionsMenu(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_write:
//                    다이얼로그를 만들어서 send할때 firebased에 가고 여기는 다이얼로그
               //FirebaseConnect.getFirebaseConnect().writeToBoard(game_id);
                createWriteDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void createWriteDialog(){
        AlertDialog.Builder builder = new WriteDialog(getContext(),R.style.WriteDialogTheme,game_id);
        builder.show();
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
        ImageView backImage = view.findViewById(R.id.detail_write_back);
        RecyclerView boardViewPager = view.findViewById(R.id.detail_recyclerview);
        CircleImageView statusImg = view.findViewById(R.id.detail_status_img);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        String team1  = bundle.getString("team1","null1");
        String team2  = bundle.getString("team2","null2");
        game_id = bundle.getString("game_id");
        team1VoteView.setTag(bundle.getString("team1Name"));
        team2VoteView.setTag(bundle.getString("team2Name"));
        team1VoteView.setOnClickListener(this);
        team2VoteView.setOnClickListener(this);
        backImage.setOnClickListener(this);

        if(bundle.getString("status").equals("finished")){
            statusImg.setImageResource(R.drawable.dead_teemo);
        }else if(bundle.getString("status").equals("not_started")){
            statusImg.setImageResource(R.drawable.honey_teemo);
        }else{
            statusImg.setImageResource(R.drawable.fire_teemo);
        }

        if(!team1.equals("null")) {Glide.with(getContext()).load(team1).into(team1View);
        }else{team1View.setBackgroundResource(R.drawable.qusetion_mark_tbd);}
        if(!team2.equals("null")) { Glide.with(getContext()).load(team2).into(team2View);
        }else{team2View.setBackgroundResource(R.drawable.qusetion_mark_tbd);}

        team1NameView.setText(bundle.getString("team1Name"));
        team2NameView.setText(bundle.getString("team2Name"));

        statusView.setText(bundle.getString("status"));
        status = bundle.getString("status");

        team1Name = bundle.getString("team1Name");
        team2Name = bundle.getString("team2Name");

        slugView.setText(bundle.getString("slug"));

        FirebaseConnect.getFirebaseConnect().getVoteView(team1VoteView,team2VoteView,game_id,team1percent,team2percent,status);
        FirebaseConnect.getFirebaseConnect().getBoard(game_id,boardViewPager,getContext());
    }



    @Override
    public void onClick(View v) {
        String teamName;

        switch (v.getId()){
            case R.id.detail_team1vote:
                if(!status.equals("not_started")){
                    Toast.makeText(getContext(), "시작하지 않은 경기만 투표할 수 있어요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(team1Name.equals("TBD")){
                    Toast.makeText(getContext(), "TBD에 투표할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team1Name");
                break;
            case R.id.detail_team2vote:
                if(!status.equals("not_started")){
                    Toast.makeText(getContext(), "시작하지 않은 경기만 투표할 수 있어요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(team2Name.equals("TBD")){
                    Toast.makeText(getContext(), "TBD에 투표할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                teamName = (String)v.getTag();
                FirebaseConnect.getFirebaseConnect().teamVote(teamName,game_id,getContext(),"team2Name");
                break;
            case R.id.detail_write_back:
                getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
