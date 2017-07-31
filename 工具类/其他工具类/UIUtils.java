package com.a520it.mygoogleplay.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Process;

import com.a520it.mygoogleplay.base.MyApplication;


/**
 * Created by ASUS on 2017/6/9.
 */

public class UIUtils {

    //返回上下文
    public  static Context getContext(){
        return  MyApplication.getContext();
    }

    //得到资源对象
    public  static Resources getResources(){
        return  getContext().getResources();
    }
    //获取字符串
    public static  String getString(int id){
        return getResources().getString(id);
    }
    //获取字符串数组
    public static  String[] getStringArray(int id){
        return getResources().getStringArray(id);
    }

    //获取颜色值
    public  static  int getColor(int id){
        return  getResources().getColor(id);
    }

    public static String getPackageName() {
        return getContext().getPackageName();
    }

    //获得主线程的id
    public  static  long getMaindThredId(){
        return MyApplication.getThreeID();
    }
    //得到主线程的handler
    public static Handler getHandler(){
        return  MyApplication.getHandler();
    }


    public  static  void safePostTast(Runnable task){
        // 1获得主线程的id
        long maindThredId = getMaindThredId();
        //2获得当前线程的id
        int currentThreadId = Process.myTid();
        //3 判断当前的任务是否在主线程中
        if (maindThredId==currentThreadId){
            //说明当前safePostTast 在主线程中运行
            task.run();
        }else {
            //说明safePostTast在子线程中运行
            Handler handler = getHandler();
            handler.post(task);
        }
    }
}
