package com.dipdoo.esportsproject.Global;

import java.util.ArrayList;

public class FirebaseMatchField {

    int team1Name;
    int team2Name;
    ArrayList tokenList;

    public FirebaseMatchField() {
        tokenList = new ArrayList();
    }

    public int getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(int team1Name) {
        this.team1Name = team1Name;
    }

    public int getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(int team2Name) {
        this.team2Name = team2Name;
    }

    public ArrayList getTokenList() {
        return tokenList;
    }

    public void setTokenList(ArrayList tokenList) {
        this.tokenList = tokenList;
    }
}
