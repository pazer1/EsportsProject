package com.example.esportsproject.Global;

import android.view.View;

import java.util.HashMap;

public class NotificationSave extends HashMap<String, Boolean> {

    private static NotificationSave notificationSave;


    public static NotificationSave getInstance(){
        if(notificationSave == null){
            notificationSave = new NotificationSave();
        }
        return notificationSave;
    }
}
