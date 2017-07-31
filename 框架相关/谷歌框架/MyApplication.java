package com.a520it.mygoogleplay.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 2017/6/9.
 */

public class MyApplication extends Application {


    private static Context mContext;
    private static  Handler mHandler;
    private static  int mThreeID;

    //创建集合储存数据
    public Map<String ,String> memoryData=new HashMap<>();

    public Map<String, String> getMemoryData() {
        return memoryData;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }
    public static  int getThreeID() {
        return mThreeID;
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        mHandler = new Handler();
        //获取主线程的ID
        mThreeID = Process.myTid();
        super.onCreate();
    }
}
