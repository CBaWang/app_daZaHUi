package com.example.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/15.
 */
public class DataFormatUtils {

    private static String Time;

    public static  String  getNowTime() { // 获得一个我想要的当前时间的格式

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date now = new Date();
        Time = myFormat.format(now);
        return Time;
    }
}
