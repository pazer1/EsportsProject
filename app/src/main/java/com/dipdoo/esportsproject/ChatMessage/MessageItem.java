package com.dipdoo.esportsproject.ChatMessage;

public class MessageItem {

    String game_id;
    String message;
    String time;
    String title;
    String userToken;
    String userNickname;
    String documentKey;


    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public MessageItem(String game_id, String userNickname, String title, String message, String time) {
        this.game_id = game_id;
        this.message = message;
        this.time = time;
        this.title = title;
        this.userToken = userToken;
        this.userNickname = userNickname;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
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

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
}
