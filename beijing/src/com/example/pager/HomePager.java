package com.example.pager;

import com.example.beijing.R;

import android.app.Activity;
import android.view.View;

public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		View view = View.inflate(mactivity, R.layout.homepager_item, null);
		Frame.addView(view);
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Text.setText("智慧北京");
		image.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
	}



}
