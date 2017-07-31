package com.a520it.mygoogleplay.manager;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//创建一个线程池
public class ThreadPoolProxy {

    private int mCorePoolSize;  //核心线程的个数
    private int mMaximumPoolSize; //总的线程个数
    private long mKeepAliveTime; //线程空闲的时间
    private ThreadPoolExecutor mPoolExecutor;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
        mKeepAliveTime = keepAliveTime;
    }

    //初始化线程池
    public void initThreadPoolExecutor(){
        //判断不能重复创建
        if (mPoolExecutor ==null || mPoolExecutor.isShutdown() || mPoolExecutor.isTerminated()){
            //同步锁
            synchronized (ThreadPoolProxy.class){
                //创建参数
                TimeUnit unit  = TimeUnit.MICROSECONDS; //毫秒
                BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                ThreadFactory threadFactory = Executors.defaultThreadFactory();
                RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                mPoolExecutor = new ThreadPoolExecutor(
                        mCorePoolSize,
                        mMaximumPoolSize,
                        mKeepAliveTime,
                        unit, //时间的单位
                        workQueue,//任务队列
                        threadFactory,//线程的工厂
                        handler);  //异常捕获器
            }
        }
    }

    //提交任务的方法
    public  void submit(Runnable task){
        //先初始化线程池
        initThreadPoolExecutor();
        mPoolExecutor.submit(task);
    }

    //执行任务的方法
    public  void cxecute(Runnable task){
        //先初始化线程池
        initThreadPoolExecutor();
        mPoolExecutor.execute(task);
    }

    //移除任务的方法
    public  void remove(Runnable task){
        //先初始化线程池
        initThreadPoolExecutor();
        mPoolExecutor.remove(task);
    }



}
