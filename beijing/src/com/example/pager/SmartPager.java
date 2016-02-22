package com.example.pager;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.beijing.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class SmartPager extends BasePager {

    private View view;

    private PullToRefreshListView pullToRefreshListView;

    private LayoutInflater Inflater;

    public SmartPager(Activity activity) {
        super(activity);

    }

    private SmartBaseAdapter adapter;

    @Override
    public void initView() {
        super.initView();
        Inflater = LayoutInflater.from(mactivity);
        view = View.inflate(mactivity, R.layout.smart_layout_conent, null);
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


    class SmartBaseAdapter extends BaseAdapter {

        public static final int ITEM_ONE = 0;

        public static final int ITEM_TWO = 1;

        public static final int ITEM_THREE = 2;


        @Override
        public int getCount() {
            return 8;//假设它有8个
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


            }
            return typeReturn;

        }

        @Override
        public int getViewTypeCount() {
            return 3;
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

                }
            }//end


            return convertView;
        }


    }


}
