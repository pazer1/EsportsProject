package com.example.esportsproject.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.example.esportsproject.MainFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FirebaseDB {

    static FirebaseDB firebaseDB;
    String matchId;
    final ArrayList<String> dataIdList = new ArrayList();
    CollectionReference collectionReference;
    FirebaseFirestore db;
    private static String dbCollectionName="matches";

    public static FirebaseDB getInstance(){
         if(firebaseDB == null)firebaseDB = new FirebaseDB();
         return firebaseDB;
    }

    public void insertDB(){


         db = FirebaseFirestore.getInstance();
         collectionReference = db.collection(dbCollectionName);


        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        dataIdList.add(document.getId());
                        Log.d("first",document.getId());
                    }
                } else {
                    Log.w("isSuccessful", "Error getting documents.", task.getException());
                }
                checkMatchId();
            }
        });
//
//        Log.d("popoppp","pppp");
//        db.collection("matches")
//                .document()
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                                Log.d("isSuceespul", task.getResult().getId() + " => " + task.getResult().getData());
//                        } else {
//                            Log.w("isSuceespul", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
         //child가 matchID

//        matches에 정보가 다 있으니깐
//        매치를 불러와서

    }

    public void setVote(String detailMatchId,String teamTag){

    }


    private void checkMatchId(){
        if(Matches.getMatches().size() > 0){
            Iterator matchesIt = Matches.getMatches().keySet().iterator();
            String key;
            while(matchesIt.hasNext()){

                key = (String)matchesIt.next();
                for(int i = 0; i<Matches.getMatches().get(key).size(); i++){
                    Match match = (Match)Matches.getMatches().get(key).get(i);
                    matchId = String.valueOf(match.getId());
                    if(dataIdList.contains(matchId)){
                        continue;
                    }else{
                        collectionReference.document(matchId).set(match);
                    }
                }
            }
        }
    }
}
//  Set keyset = matches.keySet();
//            Iterator it = keyset.iterator();
//            while(it.hasNext()){
//                String key = (String)it.next();
//                for(int j =0; j<matches.get(key).size(); j++){
//                    Match match = (Match) matches.get(key).get(j);
//
//                    Log.d("size",match.getName());
//                }
//            }
