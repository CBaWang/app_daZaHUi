package com.example.Fragment;

import com.example.beijing.ContentActivity;
import com.example.beijing.R;
import com.example.custom.NoScrollViewPager;
import com.example.pager.BasePager;
import com.example.pager.HomePager;
import com.example.pager.NewsPager;
import com.example.pager.OfficerPager;
import com.example.pager.SetPager;
import com.example.pager.SmartPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment {

	private View view;

	private RadioGroup group;

	public NoScrollViewPager Mpager;

	private List<BasePager> list;

	private ContentActivity activity;

	public TabFragment() {

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {


		this.activity = (ContentActivity) getActivity();

		view = inflater.inflate(R.layout.tab_fragment, container, false);

		Mpager = (NoScrollViewPager) view.findViewById(R.id.content_viewpager);
		initViewPager();
		setcheckAndListener();

		return view;
	}


	private void initViewPager() {

		list = new ArrayList<BasePager>();
		list.add(new HomePager(activity));
		list.add(new NewsPager(activity));
		list.add(new SmartPager(activity));
//		list.add(new OfficerPager(activity));
		list.add(new SetPager(activity));
		Mpager.setAdapter(new MyPagerAdapter());


		list.get(0).initData();// 设置默认的ViewPager 显示页面
		list.get(1).initData();
		list.get(2).initData();
		list.get(3).initData();

//		Mpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//				if (arg0 == 2) {
//
//				} else {
//					list.get(arg0).initData(); // 初始化BasePager的View和切换View
//				}
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

	}

	private void setcheckAndListener() {
		// TODO Auto-generated method stub
		group = (RadioGroup) view.findViewById(R.id.tab_group);
		group.check(R.id.tab_button1);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
					case R.id.tab_button1:
						Mpager.setCurrentItem(0);
						break;
					case R.id.tab_button2:
						Mpager.setCurrentItem(1);
						break;
					case R.id.tab_button3:
						Mpager.setCurrentItem(2);
						break;
					case R.id.tab_button5:
						Mpager.setCurrentItem(4);
						break;
					default:
						break;
				}
			}
		});
	}




	class MyPagerAdapter extends PagerAdapter {

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
			// TODO Auto-generated method stub

			container.removeView(list.get(position).mView);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position).mView);
			return list.get(position).mView;
		}

	}


	public NewsPager getNewPagerInstance(){  //提供新闻页面去切换
		return (NewsPager) list.get(1);
	}
}
