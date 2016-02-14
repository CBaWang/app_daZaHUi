package com.example.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.beijing.ContentActivity;
import com.example.beijing.R;
import com.example.menu_detail_pager.BaseMenuDetailPager;
import com.example.menu_detail_pager.InteractionMenuPager;
import com.example.menu_detail_pager.NewsMenuPager;
import com.example.menu_detail_pager.PhotosMenuPager;
import com.example.menu_detail_pager.SpecialMenuPager;
import com.example.pager.BasePager;

public class LeftMenu extends Fragment {

	private ListView listview;

	private View view;

	private Activity MActivity;

	private List<String> list;

	private LayoutInflater inflater;

	private List<BaseMenuDetailPager> listMenu;

	private BasePager basepager;

	public LeftMenu(Activity activity) {
		super();
		this.MActivity = activity;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.left_menu_fragmnet, container, false);

		basepager = ((ContentActivity) MActivity).getFrame();  //在这里拿到BasePager的对象
		completeListview();
		return view;
	}

	private void completeListview() {
		// TODO Auto-generated method stub
		list = new ArrayList<String>();
		list.add("新闻");
		list.add("专题");
		list.add("组图");
		list.add("互动");
		inflater = LayoutInflater.from(MActivity);
		listview = (ListView) view.findViewById(R.id.Left_menu_Listview);// 这个是我用一个listView
		// 当做slidingmenu显示的内容
		listview.setDivider(null);
		listview.setAdapter(new MyBaseAdapter());
		initListMenu();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				setSelectMenuPager(position);
			}

		});

	}

	private void initListMenu() {
		// TODO Auto-generated method stub
		listMenu = new ArrayList<BaseMenuDetailPager>();
		listMenu.add(new NewsMenuPager(MActivity,basepager));
		listMenu.add(new SpecialMenuPager(MActivity));
		listMenu.add(new PhotosMenuPager(MActivity,basepager));//在这里把原先的BasePager的对象传过去
		listMenu.add(new InteractionMenuPager(MActivity));

		setSelectMenuPager(0); // 设置默认的左边的slidingMenu显示的View

		// listMenu.get(0).initData();
	}

	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.left_menu_item, parent,
						false);
			}
			TextView text = (TextView) convertView
					.findViewById(R.id.left_menu_item_text);
			text.setText(list.get(position));

			return convertView;
		}

	}

	private void setSelectMenuPager(int position) { // 这就是我用listView的setOnItemClickListener（）切换frameLayout
		// 的内容实现

		basepager.Frame.removeAllViews();
		basepager.Frame.addView(listMenu.get(position).mRootView);
		listMenu.get(position).initData();
		if(listMenu.get(position) instanceof PhotosMenuPager){
			basepager.imagebutton.setVisibility(View.VISIBLE);
		}else{
			basepager.imagebutton.setVisibility(View.GONE);
		}
		((ContentActivity) MActivity).toggle();

	}

}
