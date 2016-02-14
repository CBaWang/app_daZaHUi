package com.example.pager;

import android.app.Activity;
import android.view.View;

public class SetPager extends BasePager {

	public SetPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();

		Text.setText("设置");
		image.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
	}

}
