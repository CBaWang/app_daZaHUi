package com.example.custom;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DisallowStopListenerViewpager extends ViewPager {

	public DisallowStopListenerViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DisallowStopListenerViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (getCurrentItem() == 0) {
			getParent().requestDisallowInterceptTouchEvent(false);
		} else {
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		return super.dispatchTouchEvent(ev);
	}

}
