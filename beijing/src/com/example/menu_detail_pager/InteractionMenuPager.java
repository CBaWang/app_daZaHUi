package com.example.menu_detail_pager;

import com.example.beijing.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

public class InteractionMenuPager extends BaseMenuDetailPager {

	public InteractionMenuPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		ImageView image = new ImageView(Mactivity);
		image.setImageResource(R.drawable.image4);
		
		return image;
		
	}

}
