package com.example.esportsproject.Adapter;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esportsproject.ChatMessage.MessageItem;
import com.example.esportsproject.FirebaseBoard.FirebaseConnect;
import com.example.esportsproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList messageList;
    String firebaseUsertoken;

    public BoardAdapter(Context context, ArrayList messageList,String firebaseUsertoken) {
        this.context = context;
        this.messageList = messageList;
        this.firebaseUsertoken = firebaseUsertoken;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_recycler_item,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        VH vh = (VH)viewHolder;
        final MessageItem messageItem = (MessageItem)(messageList.get(i));
        vh.board_title.setText(messageItem.getTitle());
        vh.board_content.setText(messageItem.getMessage());
        vh.board_date.setText(messageItem.getTime());
        String userToken = messageItem.getUserToken();
        if(messageItem.getUserToken().equals(firebaseUsertoken)){
            vh.board_delete.setVisibility(View.VISIBLE);
        }
        userToken = userToken.substring(0,3);
        vh.board_id.setText(messageItem.getUserNickname()+"("+userToken+")");
        vh.board_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseConnect.getFirebaseConnect().deleteMessage(messageItem);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class VH extends RecyclerView.ViewHolder{

        public TextView board_title;
        public TextView board_content;
        public TextView board_date;
        public TextView board_id;
        public ImageView board_image;
        public TextView board_delete;


        public VH(@NonNull View itemView) {
            super(itemView);
            board_id = itemView.findViewById(R.id.board_id);
            board_title = itemView.findViewById(R.id.board_title);
            board_content = itemView.findViewById(R.id.board_content);
            board_date = itemView.findViewById(R.id.board_date);
            board_image = itemView.findViewById(R.id.view_image);
            board_delete = itemView.findViewById(R.id.board_delete);
        }
    }
}
