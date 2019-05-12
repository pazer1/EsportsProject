package com.example.esportsproject.NestedScrollingView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.esportsproject.R;

public class LoremIpsumAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int ITEM_COUNT = 5;

    public final LayoutInflater mInflater;

    public LoremIpsumAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                mInflater.inflate(R.layout.view_holder_item, parent, false)) {};
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }
}