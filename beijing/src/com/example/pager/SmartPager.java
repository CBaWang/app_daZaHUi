package com.example.pager;

import android.app.Activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.beijing.R;
import com.example.custom.DisallowStopListenerLastViewpager;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class SmartPager extends BasePager {

    private View view;

    private PullToRefreshListView pullToRefreshListView;

    private LayoutInflater Inflater;

    private Handler handler;
    private List<ImageView>  PagerList;
    private View titleView;
    private DisallowStopListenerLastViewpager pager;//titleView里面的Viewpager

    public SmartPager(Activity activity) {
        super(activity);

    }

    private SmartBaseAdapter adapter;

    @Override
    public void initView() {
        super.initView();
        Inflater = LayoutInflater.from(mactivity);
        PagerList = new ArrayList<ImageView>();
        view = View.inflate(mactivity, R.layout.smart_layout_conent, null);
        titleView = View.inflate(mactivity,R.layout.smart_layout_title,null);

        pager = (DisallowStopListenerLastViewpager) titleView.findViewById(R.id.smart_layout_viewpager);  //titleView 里面的ViewPager

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.smart_layout_PullListView);

        adapter = new SmartBaseAdapter();
        Frame.addView(view);
    }

    @Override
    public void initData() {

        super.initData();
        Text.setText("App大杂烩");
        setSlidingMenuEnable(true);
        setThePullRefreshListView();//设置上下拉刷新LstView


    }

    private void setThePullRefreshListView() {  //设置上下拉刷新LstView

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout proxy =   pullToRefreshListView.getLoadingLayoutProxy();
        proxy.setPullLabel("拉动刷新");
        proxy.setRefreshingLabel("正在刷新中");
        proxy.setReleaseLabel("松开刷新");
        proxy.setLoadingDrawable(mactivity.getResources().getDrawable(R.drawable.juhua));

        setTheViewPager(pager);  //设置我的ViewPager

        ListView listview = pullToRefreshListView.getRefreshableView();

        listview.addHeaderView(titleView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);

               mactivity.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       bar.setVisibility(ProgressBar.GONE);
                       pullToRefreshListView.setAdapter(adapter);
                   }
               });

            }
        }).start();



    }



    private void setTheViewPager(final ViewPager pager) {//设置我的ViewPager

        final int[] id = {R.drawable.page1,R.drawable.page2,R.drawable.page3,R.drawable.page4,R.drawable.page5};

        for(int i=0;i<id.length;i++){
            ImageView image = new ImageView(mactivity);
            image.setImageResource(id[i]);

            image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                        break;
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(0,3000);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(0,3000);
                            break;
                    }
                    return true;
                }
            });


            PagerList.add(image);
        }


        pager.setAdapter(new MyPagerAdapter());



        handler = new Handler(){

            int viewpagerPosition = pager.getCurrentItem();
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(viewpagerPosition< id.length){

                    pager.setCurrentItem(viewpagerPosition);
                    viewpagerPosition++;
                    handler.sendEmptyMessageDelayed(0,3000);
                }else{
                    viewpagerPosition=0;
                    handler.sendEmptyMessageDelayed(0,3000);
                }

            }
        };
        handler.sendEmptyMessageDelayed(0,3000);


    }


    class SmartBaseAdapter extends BaseAdapter {

        public static final int ITEM_ONE = 0;

        public static final int ITEM_TWO = 1;

        public static final int ITEM_THREE = 2;

        public static final int ITEM_FOUR = 3;


        @Override
        public int getCount() {
            return 9;//假设它有9个
        }

        @Override
        public int getItemViewType(int position) {

            int typeReturn = 0;//返回的页面类型
            switch (position) {
                case 0:
                    typeReturn = ITEM_ONE;
                    break;
                case 1:
                    typeReturn = ITEM_THREE;
                    break;
                case 2:
                    typeReturn = ITEM_ONE;
                    break;
                case 3:
                    typeReturn = ITEM_TWO;
                    break;
                case 4:
                    typeReturn = ITEM_THREE;
                    break;
                case 5:
                    typeReturn = ITEM_ONE;
                    break;
                case 6:
                    typeReturn = ITEM_TWO;
                    break;
                case 7:
                    typeReturn = ITEM_ONE;
                    break;
                case 8:
                    typeReturn = ITEM_FOUR;
                    break;


            }
            return typeReturn;

        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case ITEM_ONE:
                        convertView = Inflater.inflate(R.layout.homepager_pull_item1, parent, false);
                        break;
                    case ITEM_TWO:
                        convertView = Inflater.inflate(R.layout.homepager_pull_item2, parent, false);
                        break;
                    case ITEM_THREE:
                        convertView = Inflater.inflate(R.layout.homepager_pull_item3, parent, false);
                        break;
                    case ITEM_FOUR:
                        convertView = Inflater.inflate(R.layout.smart_layout_tial,parent,false);
                        break;

                }
            }//end


            return convertView;
        }


    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return PagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view ==o ;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(PagerList.get(position));
            return PagerList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView(PagerList.get(position));
        }
    }


}
