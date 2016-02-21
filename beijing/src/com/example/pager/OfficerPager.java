package com.example.pager;

import android.app.Activity;

public class OfficerPager extends BasePager {

	public OfficerPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Text.setText("App大杂烩");
		setSlidingMenuEnable(true);
	}

}
