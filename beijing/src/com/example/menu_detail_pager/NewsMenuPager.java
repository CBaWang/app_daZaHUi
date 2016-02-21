package com.example.menu_detail_pager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.beijing.ContentActivity;
import com.example.beijing.R;
import com.example.pager.BasePager;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuPager extends BaseMenuDetailPager {

	private ViewPager viewpager;

	private List<NewsMenupagerItem> list;


	private TabPageIndicator indicator;

	private BasePager basepager;

	private final static String[] TITLE = {"实事","民生","热点","社会"};//这是我准备的4个页面名字

	public NewsMenuPager(Activity activity, BasePager basepager) {
		super(activity);
		this.basepager=basepager;

	}

	@Override
	public View initView() {
		View view = ViewPager.inflate(Mactivity, R.layout.basemenudetailpager,
				null);
		viewpager = (ViewPager) view.findViewById(R.id.newMenuPagerViewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.newMenuPagerIndicator);//这是我的TabPageIndicator
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

		super.initData();
		basepager.Text.setText("新闻");


		initLsitData();

//		viewpager.setOffscreenPageLimit(2);
		viewpager.setAdapter(new MyPagerAdapter());
		indicator.setViewPager(viewpager);

	}



	private void initLsitData() {
		// TODO Auto-generated method stub
		list = new ArrayList<NewsMenupagerItem>();
		for (int i = 0; i < 4; i++) {
			list.add(new NewsMenupagerItem(Mactivity));
		}

	}

	class MyPagerAdapter extends PagerAdapter {


		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return TITLE[position];
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView(list.get(position).mRootView);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			Log.d("看看看啊看","++++++++++=="+position);
			container.addView(list.get(position).mRootView);
			return list.get(position).mRootView;
		}

	}

}
