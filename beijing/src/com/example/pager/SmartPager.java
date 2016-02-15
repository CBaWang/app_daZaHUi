package com.example.pager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.beijing.R;


public class SmartPager extends BasePager {

	private View view;

	private Button button;

	private int progress = 0;

	public SmartPager(Activity activity) {
		super(activity);

	}

	@Override
	public void initData() {

		super.initData();
		Text.setText("智慧服务");
		setSlidingMenuEnable(true);
		view = View.inflate(mactivity, R.layout.smart_server, null);

	}




}
