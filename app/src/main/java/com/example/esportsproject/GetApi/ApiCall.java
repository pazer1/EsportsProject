package com.example.esportsproject.GetApi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;
import com.example.esportsproject.MainActivity;
import com.example.esportsproject.SplashActivity;
import com.example.esportsproject.Util.UtcToLocal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static android.support.v4.content.ContextCompat.startActivity;

public class ApiCall {

    private static ApiCall mInstance;
    private static UtcToLocal utcToLocal;
    private Matches matches;
    private boolean isCallEnd = false;
    ProgressBar progressBar;

    public static ApiCall getInstance(){
        if(mInstance == null) mInstance = new ApiCall();
        return mInstance;
    }


//    public void reStart(){
//        if(isMainStart<=0){
//            isMainStart++;
//            Toast.makeText(progressBar.getContext(), "다시 시작합니다.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(progressBar.getContext(),SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(progressBar.getContext(),intent,new Bundle());
//        }else if(isMainStart>=1){
//            Toast.makeText(progressBar.getContext(), "111", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void excute(ProgressBar progressBar){
        this.progressBar = progressBar;
        progressBar.setVisibility(View.VISIBLE);
        new JsonLoadingTask().execute();
    }

    private class JsonLoadingTask extends AsyncTask<String,Void,JsonArray> {
        @Override
        protected JsonArray doInBackground(String... strings) {
            return getJsonText();
        }

        @Override
        protected void onPostExecute(JsonArray jrr) {
            super.onPostExecute(jrr);
            Gson gson = new Gson();
            utcToLocal = UtcToLocal.getUtcToLocal();
            matches = Matches.getMatches();
            for(int i = 0; i < jrr.size(); i++){
                Match match = gson.fromJson(jrr.get(i),Match.class);
                String begin_time = utcToLocal.getTime(match.getBegin_at());
                if(matches.containsKey(begin_time))matches.get(begin_time).add(match);
                  else {
                      matches.put(begin_time,new ArrayList());
                      matches.get(begin_time).add(match);
                }
            }

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(progressBar.getContext(),MainActivity.class);
            startActivity(progressBar.getContext(),intent,new Bundle());
            Context context =progressBar.getContext();

            ((Activity)context).finish();
//            검증
//            Set keyset = matches.keySet();
//            Iterator it = keyset.iterator();
//            while(it.hasNext()){
//                String key = (String)it.next();
//                for(int j =0; j<matches.get(key).size(); j++){
//                    Match match = (Match) matches.get(key).get(j);
//
//                    Log.d("size",match.getName());
//                }
//            }
        }
    }

    public JsonArray getJsonText(){
        JsonParser parser = new JsonParser();
        JsonArray jArr = new JsonArray();
        String jsonPage = getStringFromUrl("http://dipdoo.dothome.co.kr/Esports/matches.json");
        if(jsonPage.equals(null)){
            Log.d("jsonPage",jsonPage+"");
            new Handler().sendEmptyMessageDelayed(100,2000);
        }
        Log.d("jsonPage",jsonPage+"");
        JsonElement element = parser.parse(jsonPage);
        Log.d("element",element+"");
        if (element instanceof JsonObject) {
            new Handler().sendEmptyMessageDelayed(100,2000);
            jArr = element.getAsJsonArray();
        } else if (element instanceof JsonArray) {
            jArr =  element.getAsJsonArray();
        }
        return jArr;
    }

    private class Handler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            getJsonText();
        }
    }

    public String getStringFromUrl(String pUrl){

        BufferedReader bufferedReader = null;
        HttpURLConnection urlConnection = null;
        StringBuffer page = new StringBuffer();

        try{
            URL url = new URL(pUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(contentStream,"UTF-8"));
            String line = null;

            while ((line = bufferedReader.readLine())!=null){
                Log.d("line",line);
                page.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page.toString();
    }
}
