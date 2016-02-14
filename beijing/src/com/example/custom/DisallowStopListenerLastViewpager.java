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

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		getParent().requestDisallowInterceptTouchEvent(true);

		return super.dispatchTouchEvent(ev);
	}

}
