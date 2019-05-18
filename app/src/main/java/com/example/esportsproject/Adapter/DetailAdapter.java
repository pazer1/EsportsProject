package com.example.esportsproject.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.util.ContentLengthInputStream;
import com.example.esportsproject.R;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter{

    ArrayList testString;
    Context context;

    public DetailAdapter(ArrayList<String> testString, Context context) {
        this.context = context;
        this.testString = testString;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view =  LayoutInflater.from(context).inflate(R.layout.detail_board_item,viewGroup,false);
       return  new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VH vh = (VH) viewHolder;
        vh.tv.setText("asdasd");
    }

    @Override
    public int getItemCount() {
        return testString.size();
    }

    class VH extends RecyclerView.ViewHolder{

        public TextView tv;

        public VH(@NonNull View itemView) {
            super(itemView);
             tv = itemView.findViewById(R.id.detail_recycler_tv);
        }
    }

}
