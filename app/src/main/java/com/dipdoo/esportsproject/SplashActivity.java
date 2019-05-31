package com.dipdoo.esportsproject;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dipdoo.esportsproject.GetApi.ApiCall;

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
            apiCall.isCallEnd =false;
            apiCall.excute(progressBar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
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
