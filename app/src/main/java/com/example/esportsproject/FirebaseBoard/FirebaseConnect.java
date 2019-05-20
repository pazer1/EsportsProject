package com.example.esportsproject.FirebaseBoard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esportsproject.Adapter.BoardAdapter;
import com.example.esportsproject.ChatMessage.MessageItem;
import com.example.esportsproject.Global.FirebaseMatchField;
import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.example.esportsproject.Global.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.annotation.Nullable;

public class FirebaseConnect {

    private static FirebaseConnect firebaseConnect;
    private CollectionReference matchDocument;
    private ArrayList<String> matchidList;
    private int reload = 0;
    private static final String userToke = FirebaseInstanceId.getInstance().getId();
    private Toast myToast;
    Long voteNum1,voteNum2;
    ArrayList<MessageItem> messageItemsList;

    public static FirebaseConnect getFirebaseConnect() {
        if(firebaseConnect==null)firebaseConnect = new FirebaseConnect();
        return firebaseConnect;
    }
    public FirebaseConnect() {
        matchidList = new ArrayList<>();
        matchDocument = FirebaseFirestore.getInstance().collection("matches");
    }

    public void loadDB(){
        matchDocument.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot query:task.getResult()){
                        matchidList.add(query.getId());
                    }
                }else{
                    new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            loadDB();
                            reload++;
                            if(reload>=2){
                                reload=0;
                                return;
                            }
                        }
                    }.sendEmptyMessageDelayed(405,2000);
                }
              checkItHas();
            }
        });
    }

    private String calPercent(long team1Long, long team2Long){
        if(team1Long==0 )return "0%";
        long sum = team1Long+team2Long;
        double percent = (team1Long/(double)sum)*100;
        return String.valueOf((int)percent+"%");
    }

    public void getVoteView(final View team1VoteView, View team2VoteView, String game_id, final View team1Percent, final View team2Percent){

        final TextView team1View = (TextView) team1VoteView;
        final TextView team2View = (TextView) team2VoteView;
        final TextView team1percentView = (TextView)team1Percent;
        final TextView team2percentView = (TextView)team2Percent;

        matchDocument.document(game_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                voteNum1 = (Long)documentSnapshot.get("team1Name");
                voteNum2 = (Long)documentSnapshot.get("team2Name");
                ArrayList tokenList = (ArrayList) documentSnapshot.get("tokenList");
                if(tokenList.contains(userToke)){
                    team1View.setText(String.valueOf(voteNum1));
                    team2View.setText(String.valueOf(voteNum2));
                    ((TextView) team1percentView).setText(calPercent(voteNum1,voteNum2));
                    ((TextView) team2percentView).setText(calPercent(voteNum2,voteNum1));
                }
            }
        });
    }



    public void teamVote(final String teamName, final String game_id, final Context context, final String teamVoteOrder){

        matchDocument.document(game_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList tokenList = (ArrayList) task.getResult().get("tokenList");
                    if(tokenList.contains(userToke)){
                        myToast=Toast.makeText(context,"투표는 한번만 가능합니다",Toast.LENGTH_SHORT);
                        myToast.show();
                        return;
                    }

                    Long teamVote = (Long)task.getResult().get(teamVoteOrder)+1;
                    ArrayList getTokenList = (ArrayList) task.getResult().get("tokenList");
                    myToast=Toast.makeText(context,teamName+"에 투표했습니다.",Toast.LENGTH_SHORT);
                    myToast.show();
                    if(!tokenList.contains(userToke)){
                        tokenList.add(userToke);
                    }
                    matchDocument.document(game_id).update(teamVoteOrder,teamVote);

                    matchDocument.document(game_id).update("tokenList",tokenList);
                }
            }
        });

    }

    public void writeToBoard(final MessageItem messageItem, final Context context){
        messageItem.setUserToken(userToke);
        matchDocument.document(messageItem.getGame_id()).collection("User").document(String.valueOf(System.currentTimeMillis())).set(messageItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    myToast = Toast.makeText(context, "글이 저장되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }else{
                    myToast = Toast.makeText(context, "통신이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }

    public void getBoard(String game_id, final RecyclerView boardRecyclerView, final Context context){

        matchDocument.document(game_id).collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                messageItemsList = new ArrayList<>();
                if(queryDocumentSnapshots.getDocuments().size()<=0)return;

                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String title  = (String)documentSnapshot.get("title");
                    String game_id  = (String)documentSnapshot.get("game_id");
                    String message  = (String)documentSnapshot.get("message");
                    String time  = (String)documentSnapshot.get("time");
                    String userToken = (String)documentSnapshot.get("userToken");

                    MessageItem messageItem = new MessageItem(game_id,title,message,time);
                    messageItem.setUserToken(userToken);
                    messageItemsList.add(0,messageItem);

                }
                if(messageItemsList.size()>0){
                    LinearLayoutManager linearLayout = new LinearLayoutManager(context);
                    boardRecyclerView.setLayoutManager(linearLayout);
                    BoardAdapter boardAdapter = new BoardAdapter(context,messageItemsList);
                    boardRecyclerView.setAdapter(boardAdapter);
                    boardAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void checkItHas(){
        if(Matches.getMatches().size()>0){
            String key,matchId;
            Iterator it = Matches.getMatches().keySet().iterator();
            while(it.hasNext()){
                 key = (String)it.next();
                ArrayList matches = Matches.getMatches().get(key);
                for(int i = 0; i<matches.size();i++){
                    Match match = (Match) matches.get(i);
                    matchId = String.valueOf(match.getId());
                    if(matchidList.contains(matchId)){
                        continue;
                    } else {
                        matchDocument.document(matchId).set(new FirebaseMatchField());
                    }
                }
            }
        }
    }
}
