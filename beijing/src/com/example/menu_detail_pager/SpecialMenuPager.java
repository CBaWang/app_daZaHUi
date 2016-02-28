package com.example.menu_detail_pager;

import android.app.Activity;

import android.view.View;
import com.example.beijing.R;

public class SpecialMenuPager extends BaseMenuDetailPager {

    public SpecialMenuPager(Activity activity) {
        super(activity);

    }

    @Override
    public View initView() {
        View view = View.inflate(Mactivity, R.layout.special_layout, null);


        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }


}
