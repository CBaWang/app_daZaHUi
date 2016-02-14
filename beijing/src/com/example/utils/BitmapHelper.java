package com.example.utils;

/**
 * Created by Administrator on 2016/2/14.
 */


import com.lidroid.xutils.BitmapUtils;


public class BitmapHelper {

    //我的简单创建BitmapUtils单例模式类
    private static BitmapUtils utils = null;


    private BitmapHelper() {

    }

    public static BitmapUtils getInstance(){
        if(utils==null) {
            utils = new BitmapUtils(MyContext.getContext());
        }
        return utils;
    }
}
