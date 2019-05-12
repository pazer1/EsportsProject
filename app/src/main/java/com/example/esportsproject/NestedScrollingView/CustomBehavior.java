package com.example.esportsproject.NestedScrollingView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.example.esportsproject.R;

public class CustomBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {

    public CustomBehavior(Context context, AttributeSet attrs){super(context,attrs);}

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull View dependency) {
        return dependency.getId() == R.id.parent_container;
    }


}
