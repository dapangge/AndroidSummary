package com.a520it.mygoogleplay.factory;

import com.a520it.mygoogleplay.manager.ThreadPoolProxy;

/***
 * 线程池工厂类
 */
public class ThreadPoolFactory {

    // 普通下载线程池
    private static ThreadPoolProxy ThreadPoolProxyNormal;
    // 专门用来下载的线程
    private static ThreadPoolProxy ThreadPoolProxyDownLoad;

    //创建一个普通线程池
    public static  ThreadPoolProxy cureatThreadPoolProxyNormal(){
        if (ThreadPoolProxyNormal == null){
            ThreadPoolProxyNormal = new ThreadPoolProxy(3,3,3000);
        }
        return  ThreadPoolProxyNormal;
    }

    //创建一个固定任务的线程池
    public  static  ThreadPoolProxy cureatThreadPoolProxyDownLoad(){
        if (ThreadPoolProxyDownLoad == null){
            ThreadPoolProxyDownLoad = new ThreadPoolProxy(3,3,3000);
        }
        return  ThreadPoolProxyDownLoad;
    }
}
