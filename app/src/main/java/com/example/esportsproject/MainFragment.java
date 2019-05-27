package com.example.esportsproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.esportsproject.Adapter.RecyclerAdapter;
import com.example.esportsproject.GetApi.ApiCall;
import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.NotificationSave;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainFragment extends Fragment {

    private final static String ITEMS_COUNT_KEY = "PartThreeFragment&ItmesCount";
    ArrayList matchList;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;


    public static MainFragment createInstance(int itemCount, ArrayList matchList,Context context){
        MainFragment mainFragment = new MainFragment();
        mainFragment.setUpmatchList(matchList);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY,itemCount);
        bundle.putParcelableArrayList("matchList",matchList);
        for (int i = 0; i < matchList.size(); i++){
            Match match = (Match)matchList.get(i);
            Log.d("matinfrgment11111",match.getStatus());
        }
        mainFragment.setArguments(bundle);
        return mainFragment;

    }


    private void setUpmatchList(ArrayList matchList){
        this.matchList = matchList;
        for (int i = 0; i < matchList.size(); i++){
            Match match = (Match)matchList.get(i);
            Log.d("matinfrgment33333",match.getStatus());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_item,container,false);
        Bundle bundle = getArguments();
        matchList = bundle.getParcelableArrayList("matchList");
        for (int i = 0; i < matchList.size(); i++){
            Match match = (Match)matchList.get(i);
            Log.d("onCreateView33333",match.getStatus());
        }
        setupRecyclerView(recyclerView);
        return recyclerView;
    }


    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerAdapter = new RecyclerAdapter(createItemList(),matchList,NotificationSave.getInstance());
        for (int i = 0; i < matchList.size(); i++){
            Match match = (Match)matchList.get(i);
            Log.d("setupRecyclerView",match.getStatus());
        }

        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
        Log.d("새로고침","ㅁㄴㅇㅁㄴㅇ");
    }
//
//    public void refreshRecyclerAdapter(){
//        if(recyclerAdapter != null)recyclerAdapter.notifyDataSetChanged();
//    }


    private List<String>createItemList(){
        List<String>itemList = new ArrayList<>();
        Bundle bundle = getArguments();

        if(bundle !=null){
            int itemsCount =bundle.getInt(ITEMS_COUNT_KEY);
            for(int i = 0; i < itemsCount; i++){
                itemList.add("item"+i);
            }
        }
        return itemList;
    }
}
