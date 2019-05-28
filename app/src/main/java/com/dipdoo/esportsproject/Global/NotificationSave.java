package com.dipdoo.esportsproject.Global;

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
