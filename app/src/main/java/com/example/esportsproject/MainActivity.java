package com.example.esportsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esportsproject.Global.Match;
import com.example.esportsproject.Global.Matches;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Matches matches = Matches.getMatches();
        Set keyset = matches.keySet();
        Iterator it = keyset.iterator();
        TextView tv  = findViewById(R.id.tv);
        StringBuffer buffer  = new StringBuffer();
        while(it.hasNext()){
            String key = (String)it.next();
            for(int j =0; j<matches.get(key).size(); j++){
                Match match = (Match) matches.get(key).get(j);
                buffer.append(match.getName());
            }
        }
        Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();

    }
}
