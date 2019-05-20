package com.example.esportsproject.ChatMessage;

public class MessageItem {

    String game_id;
    String message;
    String time;
    String title;
    String userToken;

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

    public MessageItem(String game_id, String title, String message, String time) {
        this.game_id = game_id;
        this.message = message;
        this.time = time;
        this.title = title;
    }

    public MessageItem() {
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

}
