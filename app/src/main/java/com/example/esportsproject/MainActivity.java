package com.example.esportsproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esportsproject.GetApi.ApiCall;
import com.example.esportsproject.Global.IsIntalled;
import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.example.esportsproject.Global.NotificationSave;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private static long CheckTime = 4000;

    ProgressBar progressBar;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Matches matches = Matches.getMatches();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.main_progress);
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        if(matches.size()<=0) {
            new ApiCall().excute(progressBar);
            Handler handler = new Handler();
            handler.sendEmptyMessageDelayed(404,CheckTime);
        }
        loadMap();
        progressBar.setVisibility(View.GONE);
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IsIntalled isIntalled = IsIntalled.getInstance();
        if(isPackageInstalled("tv.twitch.android.app",this)) isIntalled.setTwitch(true);
        else isIntalled.setTwitch(false);
        if(isPackageInstalled("com.google.android.youtube",this)) isIntalled.setYoutube(true);
        else isIntalled.setYoutube(false);


        Toast.makeText(this, isIntalled.isTwitch()+"twitch", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, isIntalled.isYoutube()+"youtube", Toast.LENGTH_SHORT).show();
    }

    private boolean isPackageInstalled(String pakagename, Context context){

        PackageManager pm = context.getPackageManager();
        try{
            pm.getPackageInfo(pakagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private HashMap<String,Boolean>loadMap(){
        HashMap<String,Boolean> outputMap = NotificationSave.getInstance();
        SharedPreferences pSharedPref = getSharedPreferences("noti_save", Context.MODE_PRIVATE);
        try{
            if(pSharedPref != null){
                String jsonString = pSharedPref.getString("My_map",(new JSONObject()).toString());
                Log.d("key",jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()){
                    String key = keysItr.next();

                    Boolean value = (Boolean)jsonObject.get(key);
                    outputMap.put(key,value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outputMap;
    }


    private void initToolbar(){
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        PagerAdapter pagerAdapter = new com.example.esportsproject.Adapter.PagerAdapter(getSupportFragmentManager());
        if(matches.size() >0){
            Iterator it = matches.keySet().iterator();
            String kecode;
            while (it.hasNext()){
                kecode = (String)it.next();
                ((com.example.esportsproject.Adapter.PagerAdapter) pagerAdapter).addFragement(MainFragment.createInstance(matches.get(kecode).size(),matches.get(kecode)),kecode);
            }
        }
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMap(NotificationSave.getInstance());
    }

    private void saveMap(HashMap<String,Boolean> inputMap){
        SharedPreferences pShredPref = getApplicationContext().getSharedPreferences("noti_save", Context.MODE_PRIVATE);
        if(pShredPref != null){

            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pShredPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map",jsonString);
            editor.commit();
        }
    }


    //    트위치 유투브 깔려 있는지 확인;



    private class MainHandler  extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 404:
                    if(Matches.getMatches().size()<=0){
                        Toast.makeText(MainActivity.this, "정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        return;
                    }
            }
        }
    }




}
