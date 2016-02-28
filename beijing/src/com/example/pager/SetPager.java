package com.example.pager;

import android.app.Activity;
import android.view.View;

import com.example.beijing.R;

public class SetPager extends BasePager {

	private View view;

	public SetPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initView() {
		super.initView();
		view = View.inflate(mactivity, R.layout.set_layout,null);

		Frame.addView(view);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();

		Text.setText("App大杂烩");
		setSlidingMenuEnable(false);
	}

}
