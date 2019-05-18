package com.example.esportsproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esportsproject.GetApi.ApiCall;
import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.google.protobuf.Api;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;

public class SplashActivity extends AppCompatActivity {

    private static int splashDelayTime = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() == null){
            Toast.makeText(this, "인터넷을 연결해야 사용 가능합니다", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            ProgressBar progressBar = findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.VISIBLE);
            ApiCall apiCall = new ApiCall();
            apiCall.excute(progressBar);
        }
    }
}
//          검증
//        Matches matches = Matches.getMatches();
//        Set keyset = matches.keySet();
//        Iterator it = keyset.iterator();
//        TextView tv  = findViewById(R.id.tv);
//        StringBuffer buffer  = new StringBuffer();
//        while(it.hasNext()){
//            String key = (String)it.next();
//            for(int j =0; j<matches.get(key).size(); j++){
//                Match match = (Match) matches.get(key).get(j);
//                buffer.append(match.getName());
//            }
//        }
//        tv.setText(buffer.toString());
