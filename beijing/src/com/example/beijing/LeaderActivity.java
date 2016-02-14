package com.example.beijing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LeaderActivity extends Activity {

	private ViewPager pager;

	private List<View> list;

	private View LeaderView1, LeaderView2, LeaderView3;

	private ImageView redCircle;

	private Button button;

	private LinearLayout linear;

	private float PointWidth;

	private Intent intent;

	private SharedPreferences share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaderactivity);
		intent = new Intent(LeaderActivity.this, ContentActivity.class);
		controlLeaderPager();

	}

	private void initView() {
		// TODO Auto-generated method stub
		pager = (ViewPager) findViewById(R.id.viewpager);
		linear = (LinearLayout) findViewById(R.id.group_circle);
		button = (Button) findViewById(R.id.leader_button);
		redCircle = (ImageView) findViewById(R.id.red_circle);

		initList();
		initPoint();

		pager.setAdapter(new MyPagerAdater());
		PagerListener();

	}

	private void controlLeaderPager() {
		// TODO Auto-generated method stub
		share = getSharedPreferences("Tag", this.MODE_PRIVATE);
		boolean isFirst = share.getBoolean("isOne", true);
		if (isFirst) {
			initView();
		} else {
			startActivity(intent);
			finish();
		}
	}

	private void initPoint() {
		// TODO Auto-generated method stub

		for (int i = 0; i < list.size(); i++) {
			ImageView point = new ImageView(LeaderActivity.this);
			point.setBackgroundResource(R.drawable.circle_white);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(11,
					11);

			if (i > 0) {
				param.leftMargin = 8;
			}
			point.setLayoutParams(param);
			linear.addView(point);

			linear.getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							// TODO Auto-generated method stub
							linear.getViewTreeObserver()
									.removeGlobalOnLayoutListener(this);
							PointWidth = linear.getChildAt(1).getLeft()
									- linear.getChildAt(0).getLeft();
						}
					});
		}
	}

	private void PagerListener() {
		// TODO Auto-generated method stub
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub

				if (position != 2) {
					button.setVisibility(View.INVISIBLE);
				} else {
					button.setVisibility(View.VISIBLE);
					button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Editor editor = share.edit();
							editor.putBoolean("isOne", false);
							editor.commit();
							startActivity(intent);
							finish();
						}
					});
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
									   int positionOffsetPixels) {
				// TODO Auto-generated method stub

				int Length = (int) ((PointWidth * positionOffset) + position
						* PointWidth);
				Log.d("看我就对了", "---------------" + Length);
				RelativeLayout.LayoutParams redParams = (RelativeLayout.LayoutParams) redCircle
						.getLayoutParams();
				redParams.leftMargin = Length;
				redCircle.setLayoutParams(redParams);

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initList() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(LeaderActivity.this);
		LeaderView1 = inflater.inflate(R.layout.leaderimage1, null, false);
		LeaderView2 = inflater.inflate(R.layout.leaderimage2, null, false);
		LeaderView3 = inflater.inflate(R.layout.leaderimage3, null, false);
		list = new ArrayList<View>();
		list.add(LeaderView1);
		list.add(LeaderView2);
		list.add(LeaderView3);

	}

	class MyPagerAdater extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(list.get(position));

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));
			return list.get(position);

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

}
