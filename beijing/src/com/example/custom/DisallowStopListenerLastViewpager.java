package com.example.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DisallowStopListenerLastViewpager extends ViewPager {

    public DisallowStopListenerLastViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public DisallowStopListenerLastViewpager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    int startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截,

                startY = (int) ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:


                int endY = (int) ev.getRawY();

                if (Math.abs(endY - startY) > 100){
                    getParent().requestDisallowInterceptTouchEvent(false);

                }else{
                    getParent().requestDisallowInterceptTouchEvent(true);
                }





        }


        return super.dispatchTouchEvent(ev);
    }

}
