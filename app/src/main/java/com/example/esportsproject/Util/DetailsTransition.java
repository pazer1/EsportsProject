package com.example.esportsproject.Util;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.widget.Toast;

public class DetailsTransition extends TransitionSet {

    public DetailsTransition(){
        init();
    }

    public DetailsTransition(Context context, AttributeSet attrs){
        super(context,attrs);
        Toast.makeText(context, "sdsd", Toast.LENGTH_SHORT).show();
        init();
    }

    private void init(){
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds().
                setDuration(30000L)).
                addTransition(new ChangeTransform().setDuration(30000L)).
                addTransition(new ChangeImageTransform().setDuration(30000L));
    }
}
