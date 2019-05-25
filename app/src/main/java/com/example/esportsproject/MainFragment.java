package com.example.esportsproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    public static MainFragment createInstance(int itemCount,ArrayList matchList){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY,itemCount);
        bundle.putParcelableArrayList("matchList",matchList);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_item,container,false);
        setupRecyclerView(recyclerView);

        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(createItemList(),matchList,NotificationSave.getInstance());
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void refreshRecycle(){
        recyclerAdapter.notifyDataSetChanged();
    }



    private List<String>createItemList(){
        List<String>itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        matchList = bundle.getParcelableArrayList("matchList");
        if(bundle !=null){
            int itemsCount =bundle.getInt(ITEMS_COUNT_KEY);
            for(int i = 0; i < itemsCount; i++){
                itemList.add("item"+i);
            }
        }
        return itemList;
    }
}
