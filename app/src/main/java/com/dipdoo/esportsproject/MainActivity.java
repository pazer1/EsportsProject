package com.dipdoo.esportsproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dipdoo.esportsproject.FirebaseBoard.FirebaseConnect;
import com.dipdoo.esportsproject.GetApi.ApiCall;
import com.dipdoo.esportsproject.Global.IsIntalled;
import com.dipdoo.esportsproject.Global.Match;
import com.dipdoo.esportsproject.Global.Matches;
import com.dipdoo.esportsproject.Global.NotificationSave;
import com.dipdoo.esportsproject.Util.UtcToLocal;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static long CheckTime = 4000;
    private final long btnBtweenTime = 10000;
    private final int wholeCategory=0;
    private final int LCK=1;
    private final int LPL=2;
    private final int LCS=3;
    private final int LEC=4;
    private final int LMS=5;
    private final int RIFT=6;

    ProgressBar progressBar;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Matches matches;
    PagerAdapter pagerAdapter;
    FragmentManager fragmentManager;
    Spinner spinner;
    static long btnClickTime=0;
    static int currentTabPosition=100;
    ArrayList matchLists;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab2, fab3,fab4;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matches= Matches.getMatches();
        progressBar = findViewById(R.id.main_progress);
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        spinner = findViewById(R.id.main_spinner);

        fab_open = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);

        if(matches.size()<=0) {
            new ApiCall().excute(progressBar);
            MainHandler handler = new MainHandler();
            handler.sendEmptyMessageDelayed(404,CheckTime);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        loadMap();
        progressBar.setVisibility(View.GONE);
        initToolbar();
        initSpinner();
//       여기서
        FirebaseConnect.getFirebaseConnect().loadDB();

//        final ProgressBar progressBar = findViewById(R.id.main_progress);
//        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(MainActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
//                ApiCall.getInstance().excute(progressBar);
//                progressBar.setVisibility(View.GONE);
//                initToolbar();
//                FirebaseConnect.getFirebaseConnect().loadDB();
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    public void anim() {

        if (isFabOpen) {
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;
        } else {
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
        }
    }

    private void initSpinner(){
        String[] category = {"전체보기","LCK","LPL","LCS","LEC","LMS","RIFT"};
        int[] categoryImg ={R.drawable.riot_logo,R.drawable.lck_logo,R.drawable.lpl_logo,R.drawable.lcs_logo,R.drawable.lec_logo,R.drawable.lms_logo,R.drawable.rift_rivals_resize};

        matchLists = new ArrayList();
        int temp=0;
        if(matches.size() >0) {
            Iterator it = matches.keySet().iterator();
            String kecode;
            while (it.hasNext()) {
                kecode = (String) it.next();
                ArrayList secondList = new ArrayList();
                for(int j=0; j<matches.get(kecode).size(); j++){
                    secondList.add(matches.get(kecode).get(j));
                }
                matchLists.add(secondList);
                temp++;
            }
        }
        SpinnerAdapter adapter = new com.dipdoo.esportsproject.Adapter.SpinnerAdapter(this,category,categoryImg);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case wholeCategory:
                        for(int i=0; i<((com.dipdoo.esportsproject.Adapter.PagerAdapter) pagerAdapter).getFragmentList().size(); i++){
                            MainFragment mainFragment =(MainFragment) ((com.dipdoo.esportsproject.Adapter.PagerAdapter) pagerAdapter).getFragmentList().get(i);
                            mainFragment.matchList.clear();
                            ArrayList arr = (ArrayList) matchLists.get(i);
                            for (int j=0; j<arr.size(); j++){
                                ((MainFragment)mainFragment).matchList.add(arr.get(j));
                            }
                        }
                        pagerAdapter.notifyDataSetChanged();
                        break;
                    case LCK:
                        setCategory("LCK");
                        break;
                    case LPL:
                        setCategory("LPL");
                        break;
                    case LCS:
                        setCategory("LCS");
                        break;
                    case LEC:
                        setCategory("LEC");
                        break;
                    case LMS:
                        setCategory("LMS");
                        break;
                    case RIFT:
                        setCategory("Rift");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCategory(String category){
        for(int i=0; i<((com.dipdoo.esportsproject.Adapter.PagerAdapter) pagerAdapter).getFragmentList().size(); i++){
            MainFragment mainFragment =(MainFragment) ((com.dipdoo.esportsproject.Adapter.PagerAdapter) pagerAdapter).getFragmentList().get(i);
            mainFragment.matchList.clear();
            ArrayList arr = (ArrayList) matchLists.get(i);
            for (int j=0; j<arr.size(); j++){
                Match match = (Match) arr.get(j);
                if(!match.getLeague().getName().contains(category))continue;
                ((MainFragment)mainFragment).matchList.add(arr.get(j));
            }
        }
        pagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_refresh:

                if(btnClickTime +btnBtweenTime > System.currentTimeMillis()){
                    Long a= ((btnClickTime+btnBtweenTime)-System.currentTimeMillis());
                    int gaps = (int)TimeUnit.MILLISECONDS.toSeconds(a);
                    Toast.makeText(this, gaps+"초 후에 실행해 주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                Toast.makeText(MainActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
                //int position  = tabLayout.getSelectedTabPosition();
                ApiCall.getInstance().excute(progressBar);
                progressBar.setVisibility(View.GONE);
                matches= Matches.getMatches();
//                Set keyset = matches.keySet();
//                Iterator it = keyset.iterator();
//                while(it.hasNext()){
//                String key = (String)it.next();
//                for(int j =0; j<matches.get(key).size(); j++){
//                    Match match = (Match) matches.get(key).get(j);
//
//                    Log.d("statusmatch",match.getStatus());
//                }
//            }

//            for(int i = 0 ; i <fragmentManager.getFragments().size(); i++){
//                Fragment fragment = fragmentManager.getFragments().get(i);
//
////
////                for(int j=0; j<fragment.matchList.size(); j++){
////                    Match match = (Match) fragment.matchList.get(j);
////                    Log.d("mainactivity",match.getStatus());
////                }
//
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.detach(fragment).attach(fragment).commit();
//                Log.d("fragmentSize",getSupportFragmentManager().getFragments().size()+"");
//            }

//                initToolbar();
//                FirebaseConnect.getFirebaseConnect().loadDB();
//                pagerAdapter.notifyDataSetChanged();
//                ((com.example.esportsproject.Adapter.PagerAdapter) pagerAdapter).refresh();
                currentTabPosition = tabLayout.getSelectedTabPosition();
                btnClickTime = System.currentTimeMillis();
                startActivity(new Intent(this,MainActivity.class));
                finish();
                //((com.example.esportsproject.Adapter.PagerAdapter) pagerAdapter).refresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        IsIntalled isIntalled = IsIntalled.getInstance();
        if(isPackageInstalled("tv.twitch.android.app",this)) isIntalled.setTwitch(true);
        else isIntalled.setTwitch(false);
        if(isPackageInstalled("com.google.android.youtube",this)) isIntalled.setYoutube(true);
        else isIntalled.setYoutube(false);
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

        pagerAdapter = new com.dipdoo.esportsproject.Adapter.PagerAdapter(fragmentManager);

        Set keyset = matches.keySet();
        Iterator it1 = keyset.iterator();
        while(it1.hasNext()){
            String key = (String)it1.next();
            for(int j =0; j<matches.get(key).size(); j++){
                Match match = (Match) matches.get(key).get(j);

                Log.d("statusmatch11",match.getStatus());
            }
        }
        if(matches.size() >0){
            Iterator it = matches.keySet().iterator();
            String kecode;
            int temp=0;
            while (it.hasNext()){
                temp++;
                kecode = (String)it.next();
                ((com.dipdoo.esportsproject.Adapter.PagerAdapter) pagerAdapter).addFragement(MainFragment.createInstance(matches.get(kecode).size(),matches.get(kecode),this),kecode);

            }
            Log.d("tempInt", String.valueOf(temp));
        }
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(tabLayout.getTabCount()<=5){
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date(System.currentTimeMillis());
        String ss = inputFormat.format(date);
        ss = UtcToLocal.getUtcToLocal().getTimeSystem(ss,this);

        View view= LayoutInflater.from(this).inflate(R.layout.today_tab,null);
        TextView tv = view.findViewById(R.id.tab_date);
        tv.setText("오늘");
        tv.setTextColor(getResources().getColor(R.color.colorAccent));

        pagerAdapter.notifyDataSetChanged();
        int centerPostion = tabLayout.getTabCount();
        centerPostion = centerPostion/2;
        tabLayout.getTabAt(centerPostion).select();
        //tabLayout.getTabAt(1).select();
        for(int i = 0; i<tabLayout.getTabCount(); i++){
            if(tabLayout.getTabAt(i).getText().toString().equals(ss)){

                tabLayout.getTabAt(i).select();
                tabLayout.getTabAt(i).setText("ddd");
                tabLayout.getTabAt(i).setCustomView(view);
                tabLayout.getTabAt(i).setCustomView(R.layout.today_tab);
            }
        }

        if(currentTabPosition !=100){
            tabLayout.getTabAt(currentTabPosition).select();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (android.os.Build.VERSION.SDK_INT >= 27) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                alertDialog = builder.create();
                View view = getTvDialog("twitch");
                builder.setView(view);
                builder.show();
                break;
            case R.id.fab3:
                anim();
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab4:
                anim();
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private View getTvDialog(String tvName){
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.tvdialog,null);
        ImageView imageView = view.findViewById(R.id.tvdialog_iv);
        TextView tv = view.findViewById(R.id.tvdialog_tv);
        Button negative = view.findViewById(R.id.dialog_negativie);
        final CheckBox checkBox = view.findViewById(R.id.tvdialog_check);
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
//                    체크 되있으면
                }else{
                    //안되있으면
                }
            }
        });
        if(tvName.equals("twitch")){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.twitch_icon));
            tv.setText(tvName+"로 이동합니다. 만일 "+tvName+"앱이 존재하지 않을 시 플레이스토어로 이동합니다");
        }
        return view;
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
