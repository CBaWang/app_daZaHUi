package com.example.pager;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beijing.ContentActivity;
import com.example.beijing.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BasePager {

	public View mView;
	public  Activity mactivity;
	public TextView Text;
	public FrameLayout Frame;
	public ImageView image;
	public ImageButton imagebutton;

	public ProgressBar bar;

	public RelativeLayout relative;

	public BasePager(Activity activity) {
		this.mactivity = activity;
		initView();

	}

	public void initView() {

		mView = View.inflate(mactivity, R.layout.basepager, null);
		Text = (TextView) mView.findViewById(R.id.pager_text);
		Frame = (FrameLayout) mView.findViewById(R.id.pager_frame);
		image = (ImageView) mView.findViewById(R.id.pager_image);
		imagebutton = (ImageButton) mView.findViewById(R.id.BasePagerImageButton);
		relative  = (RelativeLayout) mView.findViewById(R.id.BasePagerRelativelayout);
		bar = (ProgressBar) mView.findViewById(R.id.basepager_progressBar);


	}

	public void initData() {
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentActivity CActivity = (ContentActivity)mactivity;
				SlidingMenu slidingmenu = CActivity.getSlidingMenu();
				slidingmenu.toggle();//设置左上角的菜单小图标显示和隐藏slidingMenu的效果
			}
		});
	}

	public void setSlidingMenuEnable(Boolean set) {
		ContentActivity CAvy = (ContentActivity) mactivity;

		SlidingMenu Mmenu = CAvy.getSlidingMenu();
		if (set) {
			Mmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			Mmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

}
