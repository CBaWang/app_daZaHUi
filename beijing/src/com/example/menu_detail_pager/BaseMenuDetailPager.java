package com.example.menu_detail_pager;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuDetailPager {

	public Activity Mactivity;
	
	public View mRootView;
	
	public BaseMenuDetailPager(Activity activity){
		this.Mactivity = activity;
		this.mRootView = initView();

	}
	
	public abstract View initView(); 
	
	public void initData(){};
}
