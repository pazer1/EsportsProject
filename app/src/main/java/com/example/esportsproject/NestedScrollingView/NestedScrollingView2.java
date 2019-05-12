package com.example.esportsproject.NestedScrollingView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

public class NestedScrollingView2 extends NestedScrollView implements NestedScrollingParent2 {

    private final NestedScrollingParentHelper parentHelper;

    public NestedScrollingView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        parentHelper.onNestedScrollAccepted(child,target,axes);
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL,type);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed,int type) {
        dispatchNestedPreScroll(dx,dy,consumed,null,type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        final int oldScrollY = getScrollY();
        scrollBy(0,dyUnconsumed);
        final int myConsumed = getScrollY()-oldScrollY;
        final int myUncomsumed = dyUnconsumed - myConsumed;
        dispatchNestedScroll(0,myConsumed,0,myUncomsumed,null,type);
    }

    @Override
    public void onStopNestedScroll(View target,int type) {
        parentHelper.onStopNestedScroll(target,type);
        stopNestedScroll(type);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(child, target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(child, target, ViewCompat.TYPE_TOUCH);
    }
    @Override
    public void onNestedPreScroll(
            @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(
            @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }
}
