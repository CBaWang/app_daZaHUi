package com.example.pager;



import android.app.Activity;
import android.widget.ImageButton;

public class NewsPager extends BasePager {


	public NewsPager(Activity activity) {
		super(activity);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Text.setText("App大杂烩");
		setSlidingMenuEnable(true);

	}


}
