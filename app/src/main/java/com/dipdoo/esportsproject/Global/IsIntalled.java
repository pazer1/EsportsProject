package com.dipdoo.esportsproject.Global;

public class IsIntalled  {

    private static IsIntalled isIntalled;

    public static IsIntalled getInstance() {
        if(isIntalled == null){
            isIntalled = new IsIntalled();
        }
        return isIntalled;
    }

    private boolean isTwitch;
    private boolean isYoutube;

    public boolean isTwitch(){return isTwitch;}
    public void setTwitch(boolean twitch){isTwitch = twitch;}
    public boolean isYoutube(){return isYoutube;}
    public void setYoutube(boolean youtube){isYoutube = youtube;}
}
