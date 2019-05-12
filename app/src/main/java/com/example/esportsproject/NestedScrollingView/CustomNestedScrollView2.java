package com.example.esportsproject.NestedScrollingView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CustomNestedScrollView2 extends NestedScrollingView2{
    public CustomNestedScrollView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        final RecyclerView rv=(RecyclerView)target;
        if(dy<0 && isRvScrollToTop(rv) || (dy>0 && !isNsvScrolledToBottom(this))){
            scrollBy(0,dy);
            consumed[1] = dy;
            return;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    private static boolean isNsvScrolledToBottom(NestedScrollView nsv){
        return !nsv.canScrollVertically(1);
    }

    private static boolean isRvScrollToTop(RecyclerView rv){
        final LinearLayoutManager lm = (LinearLayoutManager) rv.getLayoutManager();
        return lm.findFirstVisibleItemPosition() == 0 && lm.findViewByPosition(0).getTop()==0;
    }
}
