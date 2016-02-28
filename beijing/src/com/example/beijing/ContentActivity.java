package com.example.beijing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;

import com.example.Fragment.LeftMenu;
import com.example.Fragment.TabFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class ContentActivity extends SlidingFragmentActivity {

    private FragmentManager manager;

    private FragmentTransaction transaction;

    private SharedPreferences sharedPreferences;

    private final static String Fragment_LEFT_Tag = "fragment_left";

    private final static String Fragment_CONTENT_Tag = "frgamnet_content";

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
        initFragmnet();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putBoolean("HomePagerSwitch", true).commit();
    }



    private void initFragmnet() { // 初始化和切换fragment
        // TODO Auto-generated method stub
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.tab_bottom, new TabFragment(), Fragment_CONTENT_Tag);

        transaction.replace(R.id.leftmenu, new LeftMenu(),Fragment_LEFT_Tag);

        transaction.commit();
    }

    public  TabFragment getTabFragmnetInstance(){  //拿到TabFragmnet的对象
        return (TabFragment) getSupportFragmentManager().findFragmentByTag(Fragment_CONTENT_Tag);
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
