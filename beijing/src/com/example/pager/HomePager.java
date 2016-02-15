package com.example.pager;

import com.example.bean.bookList;
import com.example.beijing.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomePager extends BasePager {

    private PullToRefreshListView RefreahlistView;

    private LayoutInflater inflater;

    public HomePager(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
    }

    private List<bookList> list;


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        list = new ArrayList<bookList>();
        inflater = LayoutInflater.from(mactivity);
        View view = View.inflate(mactivity, R.layout.homepager_item, null);
        RefreahlistView = (PullToRefreshListView) view.findViewById(R.id.homePullListView);
        Frame.addView(view);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        Text.setText("智慧北京");
        image.setVisibility(View.GONE);
        setSlidingMenuEnable(false);
    }


    private class MyBaseAdapter extends BaseAdapter{

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
            if(convertView==null){
                convertView = inflater.inflate(R.layout.read_item,parent,false);
                holder = new ViewHolder();
                holder.author = (TextView) convertView.findViewById(R.id.read_item_author);
                holder.discuss = (TextView) convertView.findViewById(R.id.read_item_discuss);
                holder.from = (TextView) convertView.findViewById(R.id.read_item_publish);
                holder.image = (ImageView) convertView.findViewById(R.id.read_item_image);
                holder.Indicator = (TextView) convertView.findViewById(R.id.read_item_indicator);
                holder.mobile = (TextView) convertView.findViewById(R.id.read_item_mobile);
                holder.summary = (TextView) convertView.findViewById(R.id.read_item_summary);
                holder.title = (TextView) convertView.findViewById(R.id.read_item_titile);
                holder.RatingBar = (RatingBar) convertView.findViewById(R.id.read_item_ratingBar);
                holder.price1 = (TextView) convertView.findViewById(R.id.read_item_price1);
                holder.price2 = (TextView) convertView.findViewById(R.id.read_item_price2);
                convertView.setTag(holder);

            }else{
              holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
    }//end


    private class ViewHolder{
       TextView Indicator;

        TextView title;

        TextView author;

        TextView from;

        TextView discuss;

        TextView price1;

        TextView price2;

        TextView mobile;

        ImageView image;

        RatingBar RatingBar;

        TextView summary;


    }//end


}
