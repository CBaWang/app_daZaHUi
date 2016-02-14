package com.example.menu_detail_pager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bean.PhotosMenuPagerItem;
import com.example.beijing.R;
import com.example.pager.BasePager;
import com.example.pager.NewsPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PhotosMenuPager extends BaseMenuDetailPager {

	private ListView listview;

	private GridView gridview;

	private List<PhotosMenuPagerItem> list;

	private String NowTime;

	private String Url;

	private LayoutInflater inflater;

	private BitmapUtils utils;

	private BasePager basepager;

	public PhotosMenuPager(Activity activity,BasePager basepager) {
		super(activity);
		this.basepager = basepager;


	}

	@Override
	public View initView() {
		View view = View.inflate(Mactivity, R.layout.photos_menu_pager, null);


		listview = (ListView) view.findViewById(R.id.photosMenuPagerLisview);

		gridview = (GridView) view.findViewById(R.id.photosMenuPagerGridView);

		inflater = LayoutInflater.from(Mactivity);



		return view;
	}

	@Override
	public void initData() {

		basepager.Text.setText("组图");

		getNowTime();// 得到当前时间方法
		Url = "http://route.showapi.com/197-1?showapi_appid=10562&showapi_timestamp="
				+ NowTime
				+ "&num=10&page=3&showapi_sign=ca3cc8fb1be046709e15a0400f407283";

		getDataFromserver();// 从服务器得到数据

	}

	private void getDataFromserver() {
		// TODO Auto-generated method stub
		list = new ArrayList<PhotosMenuPagerItem>();
		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.GET, Url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub

				String result = responseInfo.result;
				parseTheJson(result);// 解析Json数据

				listview.setAdapter(new MyBaseAdapter());
				gridview.setAdapter(new MyBaseAdapter());
				changeView(); // 切换的界面的按钮

			}

			private void changeView() {



				basepager.imagebutton.setOnClickListener(new OnClickListener() {

					boolean isChange = true;  // 切换View用的变量
					@Override
					public void onClick(View v) {

						if (isChange) {
							listview.setVisibility(View.GONE);
							gridview.setVisibility(View.VISIBLE);
							basepager.imagebutton.setImageResource(R.drawable.icon_pic_grid_type);

							isChange = false;
						} else {
							listview.setVisibility(View.VISIBLE);
							gridview.setVisibility(View.GONE);
							basepager.imagebutton.setImageResource(R.drawable.icon_pic_list_type);
							isChange = true;
						}
					}
				});
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				error.printStackTrace();
			}
		});
	}

	private void parseTheJson(String result) { // 解析Json数据
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(result);
			JSONObject showapi = json.getJSONObject("showapi_res_body");
			Log.d("娃娃哇哇我爱我啊", "======" + showapi);
			for (int i = 0; i < 10; i++) {
				JSONObject NumberData = showapi
						.getJSONObject(String.valueOf(i));
				String title = NumberData.getString("title");
				String picUrl = NumberData.getString("picUrl");
				list.add(new PhotosMenuPagerItem(title, picUrl));

				Log.d("啦啦啦啦啦啦啦啦", title + picUrl);// 我拿到 的集合
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyBaseAdapter extends BaseAdapter {

		ViewHolder holder;
		public MyBaseAdapter() {
			// TODO Auto-generated constructor stub

			utils = new BitmapUtils(Mactivity);
			utils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}



		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.photos_menu_pager_item,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.PhotosMenuPagerItemImageView);
				holder.text = (TextView) convertView
						.findViewById(R.id.PhotosMenuPagerItemTextView);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			utils.display(holder.image, list.get(position).getImage());
			holder.text.setText(list.get(position).getTitle());

			return convertView;
		}

	}

	private void getNowTime() { // 获得一个我想要的当前时间的格式

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		Date now = new Date();
		NowTime = myFormat.format(now);

	}

	class ViewHolder {
		ImageView image;

		TextView text;
	}

}
