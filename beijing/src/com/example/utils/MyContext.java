package com.example.utils;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class MyContext extends Application {
    private static Context mContext;

    public static RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mQueue = Volley.newRequestQueue(mContext);
    }

    public static Context getContext(){
        return mContext;
    }


}
