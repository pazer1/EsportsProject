package com.example.esportsproject.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Matches extends LinkedHashMap<String,ArrayList>{

    private  static Matches matches;

    public static Matches getMatches(){
        if(matches == null)matches = new Matches();
        return matches;
    }
}
