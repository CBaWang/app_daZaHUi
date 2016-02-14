package com.example.menu_detail_pager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class SpecialMenuPager extends BaseMenuDetailPager {

	public SpecialMenuPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView text = new TextView(Mactivity);
		text.setText("我是专题");
		text.setGravity(Gravity.CENTER);
		text.setTextSize(25);
		text.setTextColor(Color.BLUE);

		return text;
	}

}
