package com.example.menu_detail_pager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
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
import android.widget.Toast;

import com.example.bean.PhotosMenuPagerItem;
import com.example.beijing.R;
import com.example.pager.BasePager;
import com.example.pager.NewsPager;
import com.example.utils.DataFormatUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PhotosMenuPager extends BaseMenuDetailPager {

	private ListView listview;

	private GridView gridview;

	private List<PhotosMenuPagerItem> list;

	private String NowTime;

	private final static String Url = "http://route.showapi.com/197-1";

	private LayoutInflater inflater;

	private BitmapUtils utils;

	private BasePager basepager;

	public PhotosMenuPager(Activity activity,BasePager basePager) {
		super(activity);
		this.basepager = basePager;
		basepager.imagebutton.setVisibility(View.VISIBLE);

	}

	@Override
	public View initView() {
		View view = View.inflate(Mactivity, R.layout.photos_menu_pager, null);


		listview = (ListView) view.findViewById(R.id.photosMenuPagerLisview);

		gridview = (GridView) view.findViewById(R.id.photosMenuPagerGridView);

		list = new ArrayList<PhotosMenuPagerItem>();

		inflater = LayoutInflater.from(Mactivity);


		initData();


		return view;
	}

	@Override
	public void initData() {

		getDataFromserver();//联网拿数据

	}

	private void getDataFromserver() {//联网拿数据

		RequestParams params = new RequestParams();
		params.addBodyParameter("showapi_appid","15648");
		params.addBodyParameter("showapi_timestamp", DataFormatUtils.getNowTime());
		params.addBodyParameter("num","10");
		params.addBodyParameter("page","1");
		params.addBodyParameter("showapi_sign","11e4ac9b4826422d981b9b8c960e7829");



		HttpUtils utils = new HttpUtils();

		utils.send(HttpMethod.POST, Url,params, new RequestCallBack<String>() {

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
				Toast.makeText(Mactivity, "请检查网络或者手机时间是否为当前时间", Toast.LENGTH_LONG).show();
				error.printStackTrace();
			}
		});
	}

	private void parseTheJson(String result) { // 解析Json数据
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(result);
			JSONObject showapi = json.getJSONObject("showapi_res_body");

			JSONArray newList = showapi.getJSONArray("newslist");

			for(int i=0;i<newList.length();i++){
				JSONObject object = newList.getJSONObject(i);
				String title = object.getString("title");
				String image = object.getString("picUrl");
				list.add(new PhotosMenuPagerItem(title,image));
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


	static class  ViewHolder {
		ImageView image;

		TextView text;
	}

}
