package com.example.esportsproject.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageItem extends HashMap<String, ArrayList> {

    String game_id;
    String message;
    String time;
    String title;
    String userToken;
    String userNickname;

    ArrayList<String> boardContent;

    public MessageItem(String game_id, String message, String time, String title, String userToken, String userNickname) {
        super();

    }

     

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
