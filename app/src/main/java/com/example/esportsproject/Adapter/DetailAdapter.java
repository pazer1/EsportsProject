package com.example.esportsproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.esportsproject.ChatMessage.MessageItem;

import java.util.ArrayList;

public class DetailAdapter extends BaseAdapter {

    ArrayList<MessageItem>messageItems;
    LayoutInflater inflater;

    public DetailAdapter(ArrayList<MessageItem> messageItems, LayoutInflater inflater) {
        this.messageItems = messageItems;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {return messageItems.size();}

    @Override
    public Object getItem(int position) {return messageItems.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageItem item = messageItems.get(position);

        View itemVIew = null;
        return itemVIew;
    }
}
