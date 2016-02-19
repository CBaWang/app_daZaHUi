package com.example.pager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bean.bookList;
import com.example.beijing.R;
import com.example.utils.AllUrls;
import com.example.utils.BitmapHelper;
import com.example.utils.DataFormatUtils;
import com.example.utils.FileUtils;
import com.example.utils.MyContext;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePager extends BasePager {

    private PullToRefreshListView RefreahlistView;

    private LayoutInflater inflater;

    public HomePager(Activity activity) {
        super(activity);

    }

    private List<bookList> list;

    private Map<String, String> map;

    private BitmapUtils bitmapUtils;

    private MyBaseAdapter adapter;

    private int numPage = 1;

    private FileUtils fileUtils;

    private SharedPreferences sharedPreferences;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    RefreahlistView.setAdapter(adapter);
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    RefreahlistView.onRefreshComplete();
                    break;
            }
        }

    }; //end



    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        list = new ArrayList<bookList>();
        bitmapUtils = BitmapHelper.getInstance();
        fileUtils = new FileUtils();
        adapter = new MyBaseAdapter();
        inflater = LayoutInflater.from(mactivity);
        sharedPreferences = mactivity.getSharedPreferences("switchlist", Context.MODE_PRIVATE);
        View view = View.inflate(mactivity, R.layout.homepager_item, null);
        RefreahlistView = (PullToRefreshListView) view.findViewById(R.id.homePullListView);
        Frame.addView(view);

    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        Text.setText("App大杂烩");
        boolean HomePagerSwitch =  sharedPreferences.getBoolean("HomePagerSwitch", true);
        image.setVisibility(View.GONE);
        setSlidingMenuEnable(false);

        setThePullReFreshListView();//设置我的下拉刷新控件
        initMap();//初始化我的post参数
        if(HomePagerSwitch) {
            initFace();//初始化我的界面
            sharedPreferences.edit().putBoolean("HomePagerSwitch",false).commit();
        }
    }


    private void setThePullReFreshListView() {//设置reFreshListView


        RefreahlistView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = RefreahlistView.
                getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在载入...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示

        ILoadingLayout endLabels = RefreahlistView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在载入...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示


        RefreahlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                map.put("page", String.valueOf(++numPage));
                connectNetWork(1);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                map.put("page", String.valueOf(++numPage));
                connectNetWork(1);

            }
        });


    }//end


    private void initFace() {
        String JsonData = fileUtils.getLocalData("BookList");
        if (!TextUtils.isEmpty(JsonData)) {
            parseJson(JsonData, 0);
        }
        connectNetWork(0);//联网拿数据
    }

    private void connectNetWork(final int what) {

        StringRequest request = new StringRequest(Request.Method.POST, AllUrls.bookListsUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {


                fileUtils.SaveDataLocal(s, "BookList");
                parseJson(s, what);//解析Json数据

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                SystemClock.sleep(1500);
                bar.setVisibility(ProgressBar.GONE);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        MyContext.mQueue.add(request);

    }


    private void parseJson(String jsonData, int what) {  //解析Json数据
        try {
            bar.setVisibility(ProgressBar.GONE);
            JSONObject object = new JSONObject(jsonData);
            JSONObject body = object.getJSONObject("showapi_res_body");
            JSONArray array = body.getJSONArray("bookList");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String author = item.getString("author");
                String from = item.getString("from");
                int id = item.getInt("id");
                String img = item.getString("img");
                String name = item.getString("name");
                String summary = item.getString("summary");
                bookList book = new bookList(author, from, id, img, name, summary);
                list.add(book);
                handler.sendEmptyMessage(what);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initMap() {  //初始化我的post参数
        map = new HashMap<String, String>();
        map.put("limit", "10");
        map.put("page", "numPage");
        map.put("showapi_timestamp", DataFormatUtils.getNowTime());
        map.put("showapi_appid", "15648");
        map.put("showapi_sign", "11e4ac9b4826422d981b9b8c960e7829");


    }//end


    private class MyBaseAdapter extends BaseAdapter {

        private ViewHolder holder;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.read_item, parent, false);
                holder = new ViewHolder();
                holder.author = (TextView) convertView.findViewById(R.id.read_item_author);
                holder.discuss = (TextView) convertView.findViewById(R.id.read_item_discuss);
                holder.from = (TextView) convertView.findViewById(R.id.read_item_publish);
                holder.image = (ImageView) convertView.findViewById(R.id.read_item_image);
                holder.Indicator = (TextView) convertView.findViewById(R.id.read_item_indicator);
                holder.mobile = (TextView) convertView.findViewById(R.id.read_item_mobile);
                holder.summary = (TextView) convertView.findViewById(R.id.read_item_summary);
                holder.title = (TextView) convertView.findViewById(R.id.read_item_titile);
//                holder.RatingBar = (RatingBar) convertView.findViewById(R.id.read_item_ratingBar);
                holder.price1 = (TextView) convertView.findViewById(R.id.read_item_price1);
                holder.price2 = (TextView) convertView.findViewById(R.id.read_item_price2);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.author.setText("作者 ："+list.get(position).getAuthor());
            holder.mobile.setText("手机专享");
            holder.price2.setText("¥100");
            holder.price1.setText("¥58");
            holder.from.setText("出版社 ："+list.get(position).getFrom()+"出版");
            holder.discuss.setText("1970条评论");
            holder.title.setText(list.get(position).getName());
            holder.summary.setText(list.get(position).getSummary());
            bitmapUtils.display(holder.image, list.get(position).getImg());
            holder.Indicator.setText(String.valueOf(position + 1));
//            holder.RatingBar.setMax(5);
//            holder.RatingBar.setNumStars(5);


            return convertView;
        }
    }//end


    private class ViewHolder {
        TextView Indicator;

        TextView title;

        TextView author;

        TextView from;

        TextView discuss;

        TextView price1;

        TextView price2;

        TextView mobile;

        ImageView image;

//        RatingBar RatingBar;

        TextView summary;


    }//end


}
