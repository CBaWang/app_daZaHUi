package com.example.menu_detail_pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bean.JsonBean;
import com.example.bean.Showapi_res_body;
import com.example.bean.newList;
import com.example.beijing.ContentActivity;
import com.example.beijing.R;
import com.example.beijing.WebActivity;
import com.example.custom.DisallowStopListenerLastViewpager;
import com.example.utils.DataFormatUtils;
import com.example.utils.MyContext;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;

public class NewsMenupagerItem {

    private DisallowStopListenerLastViewpager viewpager;

    private PullToRefreshListView refreshlistview;

    private final static int[] Photos = {R.drawable.image2, R.drawable.image3,
            R.drawable.image4, R.drawable.image5};

    private List<ImageView> list;

    private static String Url;

    private List<newList> conList;

    private LayoutInflater inflater;

    private CirclePageIndicator circle;

    private View viewTitle;

    private ListView listview;

    private MyBaseAdapter MyAdapter;

    private Handler handler;

    private ContentActivity activity;

    private BitmapUtils bitmapU;

    private Map<String, String> map;

    private int page = 1;//第1页的数据


    public NewsMenupagerItem(Activity activity, String url) {
        this.activity = (ContentActivity) activity;

        this.Url = url;

    }

    public View initView() {

        View view = View
                .inflate(activity, R.layout.news_menu_pager_item, null);

        viewTitle = View.inflate(activity, R.layout.news_menu_pager_title,
                null);

        viewpager = (DisallowStopListenerLastViewpager) viewTitle
                .findViewById(R.id.NewsMenupagerItemViewPager);
        refreshlistview = (PullToRefreshListView) view
                .findViewById(R.id.NewsMenupagerItemListView);

        circle = (CirclePageIndicator) viewTitle
                .findViewById(R.id.NewsMenupagerCirclePageIndicator);

        map = new HashMap<>();

        MyAdapter = new MyBaseAdapter();

        inflater = LayoutInflater.from(activity);

        bitmapU = new BitmapUtils(activity);

        initData();

        return view;
    }

    public void initData() {

        initPhotosData();

        viewpager.setAdapter(new MyPagerAdapter());
        circle.setViewPager(viewpager);
        setAutoRotateViewPager(); // 设置自动循环播放ViewPager页面

        getTheServiceData();

        setTheRefreshListView();// 设置我的下拉刷新 的下拉头的参数


    }

    private void setTheRefreshListView() { // 设置我的下拉刷新 的下拉头的参数
        refreshlistview.setMode(Mode.BOTH);

        ILoadingLayout ill = refreshlistview.getLoadingLayoutProxy(true, false);
        ill.setPullLabel("我被下拉啦");
        ill.setRefreshingLabel("我在加载数据啦");
        ill.setReleaseLabel("我松开就刷新啦");

//        ill.setLoadingDrawable(activity.getResources().getDrawable(R.drawable.juhua));

        listview = refreshlistview.getRefreshableView();
        listview.addHeaderView(viewTitle);

        listviewListener();


        PullToRefreshListViewListener();
    }

    private void setAutoRotateViewPager() { // 设置自动循环播放ViewPager页面的方法
        // TODO Auto-generated method stub
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub

                    int nowPosition = viewpager.getCurrentItem();
                    if (nowPosition < Photos.length - 1) {
                        nowPosition++;

                    } else {
                        nowPosition = 0;
                    }

                    viewpager.setCurrentItem(nowPosition);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }

            };
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private void getTheServiceData() { // 用httpUtils读取Url上的流

        map.put("showapi_appid", "15648");
        map.put("showapi_timestamp", DataFormatUtils.getNowTime());
        map.put("num", "10");
        map.put("page", String.valueOf(page));
        map.put("showapi_sign", "11e4ac9b4826422d981b9b8c960e7829");

        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ParseJsonData(s);

                refreshlistview.setAdapter(MyAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(activity, "请检查网络或者手机时间是否为当前时间", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        MyContext.mQueue.add(request);


    }

    private void listviewListener() {
        // TODO Auto-generated method stub
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (position > 1) {

                    Intent intent = new Intent();
                    intent.putExtra("contentUrl", conList.get(position - 2).getUrl());
                    intent.setClass(activity, WebActivity.class);

                    activity.startActivity(intent);
                }
            }
        });
    }

    private void PullToRefreshListViewListener() { // 我的下拉刷新的listView的监听事件
        // TODO Auto-generated method stub
        refreshlistview
                .setOnRefreshListener(new OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        new MyAsyncTask().execute();

                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        new MyAsyncTask().execute();
                    }
                });
    }


    private void ParseJsonData(String result) { // 解析Json数据


        Gson gson = new Gson();
        JsonBean bean = gson.fromJson(result, JsonBean.class);
        Showapi_res_body showapi = bean.getShowapi_res_body();
        conList = showapi.getNewLists();


    }

    private void initPhotosData() {// 初始化我的Viewpager的几张图片
        // TODO Auto-generated method stub
        list = new ArrayList<ImageView>();
        for (int i = 0; i < Photos.length; i++) {
            ImageView image = new ImageView(activity);
            image.setScaleType(ScaleType.CENTER_CROP);
            image.setBackgroundResource(Photos[i]);
            image.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            Log.d("被按下了", "+=============");
                            handler.removeCallbacksAndMessages(null);

                            break;
                        case MotionEvent.ACTION_CANCEL:
                            Log.d("拖动了", "+=============");
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d("没按了", "+=============");
                            handler.sendEmptyMessageDelayed(0, 3000);
                            break;

                    }
                    return true;
                }
            });
            list.add(image);

        }
    }

    class MyPagerAdapter extends PagerAdapter { // ViewPager的适配器

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
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(list.get(position));
            return list.get(position);
        }

    }

    class MyBaseAdapter extends BaseAdapter { // listView的适配器


        ViewHolder holder;


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return conList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return conList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                bitmapU.configDefaultLoadFailedImage(R.drawable.news_pic_default);
                bitmapU.configDefaultLoadingImage(R.drawable.news_pic_default);
                convertView = inflater.inflate(
                        R.layout.news_menu_listview_item, parent, false);
                holder = new ViewHolder();
                holder.text1 = (TextView) convertView
                        .findViewById(R.id.NewsMenuListViewItem_TextView1);
                holder.text2 = (TextView) convertView
                        .findViewById(R.id.NewsMenuListViewItem_TextView2);
                holder.image = (ImageView) convertView
                        .findViewById(R.id.NewsMenuListViewItem_imageView);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }
            holder.text1.setText(conList.get(position).getTitle());
            holder.text2.setText(conList.get(position).getCtime());

            bitmapU.display(holder.image, conList.get(position).getPicUrl());

            return convertView;
        }

    }

    class ViewHolder { // ViewHolder类
        ImageView image;

        TextView text1;

        TextView text2;
    }


    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            SystemClock.sleep(1000);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            conList.addAll(conList);
            MyAdapter.notifyDataSetChanged();
            refreshlistview.onRefreshComplete();

        }

    }

}
