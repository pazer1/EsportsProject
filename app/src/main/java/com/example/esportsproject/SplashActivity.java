package com.example.esportsproject;

import android.content.Intent;
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

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Set;

public class SplashActivity extends AppCompatActivity {

    private static int splashDelayTime = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ProgressBar progressBar = findViewById(R.id.loading_spinner);
        ApiCall apiCall = new ApiCall();
        apiCall.excute(progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
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
