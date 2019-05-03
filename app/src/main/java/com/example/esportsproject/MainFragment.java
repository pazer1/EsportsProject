package com.example.esportsproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    public static MainFragment createInstance(int itemCount){
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_recycler_item,container,false);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
