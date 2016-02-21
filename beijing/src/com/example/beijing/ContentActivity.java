package com.example.beijing;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;

import com.example.Fragment.LeftMenu;
import com.example.Fragment.TabFragment;
import com.example.custom.NoScrollViewPager;
import com.example.pager.BasePager;
import com.example.pager.HomePager;
import com.example.pager.NewsPager;
import com.example.pager.OfficerPager;
import com.example.pager.SetPager;
import com.example.pager.SmartPager;
import com.example.utils.FileUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ContentActivity extends SlidingFragmentActivity {

    private FragmentManager manager;

    private FragmentTransaction transaction;

    private NoScrollViewPager pager;

    private List<BasePager> list;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_activity);
        sharedPreferences = getSharedPreferences("switchlist", Context.MODE_PRIVATE);
        setBehindContentView(R.layout.left_menu);
        SlidingMenu slidingmenu = getSlidingMenu();
        slidingmenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingmenu.setBehindOffset(200);

        initViewPager();
        initFragmnet();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        FileUtils.ResetAllFiles();
        sharedPreferences.edit().putBoolean("HomePagerSwitch", true).commit();
    }

    private void initViewPager() {
        // TODO Auto-generated method stub
        pager = (NoScrollViewPager) findViewById(R.id.content_viewpager);
//        pager.setOffscreenPageLimit(5);
        list = new ArrayList<BasePager>();
        list.add(new HomePager(this));
        list.add(new NewsPager(this));
        list.add(new SmartPager(this));
        list.add(new OfficerPager(this));
        list.add(new SetPager(this));
        pager.setAdapter(new MyPagerAdapter());


        list.get(0).initData();// 设置默认的ViewPager 显示页面
        list.get(1).initData();
        list.get(2).initData();
        list.get(3).initData();
        list.get(4).initData();

//        pager.setOnPageChangeListener(new OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int arg0) {
//                // TODO Auto-generated method stub
//                list.get(arg0).initData(); // 初始化BasePager的View和切换View
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });

    }

    private void initFragmnet() { // 初始化和切换fragment
        // TODO Auto-generated method stub
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.leftmenu, new LeftMenu(this));
        transaction.replace(R.id.tab_bottom, new TabFragment(pager));
        transaction.commit();
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

    public BasePager getFrame() { // 设立一个方法。为LeftMenu提供一个原先生成的BasePager对象去插入View
        return list.get(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("重点提示");
        dialog.setMessage("你确定要退出吗？");
        dialog.setNegativeButton("取消", null);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentActivity.this.finish();

            }
        });

        dialog.show();

        return super.onKeyDown(keyCode, event);
    }
}
