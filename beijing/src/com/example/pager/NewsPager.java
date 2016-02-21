package com.example.pager;



import android.app.Activity;
import android.widget.ImageButton;

public class NewsPager extends BasePager {

	public static ImageButton imagebutton; //定义一个静态的ImageButton

	public NewsPager(Activity activity) {
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
