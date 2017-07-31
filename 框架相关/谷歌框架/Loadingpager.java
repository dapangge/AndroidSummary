package com.a520it.mygoogleplay.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.a520it.mygoogleplay.R;
import com.a520it.mygoogleplay.factory.ThreadPoolFactory;
import com.a520it.mygoogleplay.utils.UIUtils;


//创建一个View加载的管理器
public abstract class Loadingpager extends FrameLayout {


    private View mLoadingView;//正在加载数据
    private View mLoadingViewError;//加载数据错误
    private View mLoadingViewEmpty;//没有更多数据加载了
    private View mLoadingViewSuccess;//加载成功视图
    public static final int MCURRENTSTATE_LOADING = 0;//正在加载的状态
    public static final int MCURRENTSTATE_ERROR = 1;//加载错误
    public static final int MCURRENTSTATE_EMPTY = 2;//加载为空
    public static final int MCURRENTSTATE_SUCCESS = 3;//成功
    public int mCurrentState = MCURRENTSTATE_LOADING;  //默认的显示状态
    private LoadDataTask mDataTask;

    public Loadingpager(Context context) {
        super(context);
        initCommOnView();
    }

    //创建出共同的显示View
    private void initCommOnView() {
        //正在加载
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading,null);
        this.addView(mLoadingView);
        //加载失败
        mLoadingViewError = View.inflate(UIUtils.getContext(), R.layout.pager_error,null);
        //重试按钮
        Button button = (Button) mLoadingViewError.findViewById(R.id.error_btn_retry);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击从新加载数据
                troggleData();
            }
        });
        this.addView(mLoadingViewError);

        //数据为空
        mLoadingViewEmpty = View.inflate(UIUtils.getContext(), R.layout.pager_empty,null);
        this.addView(mLoadingViewEmpty);

        //更具状态显示布局文件
        refreshViewByState();

    }

    //View显示与隐藏
    private void refreshViewByState() {
        //控制正在加载的页面是否显示
        mLoadingView.setVisibility(mCurrentState == MCURRENTSTATE_LOADING ? View.VISIBLE : View.GONE);
        //控制错误页面是否显示
        mLoadingViewError.setVisibility(mCurrentState == MCURRENTSTATE_ERROR ? View.VISIBLE : View.GONE);
        //控制空的页面是否显示
        mLoadingViewEmpty.setVisibility(mCurrentState == MCURRENTSTATE_EMPTY ? View.VISIBLE : View.GONE);
       //控制成功的页面
        if (mCurrentState == MCURRENTSTATE_SUCCESS && mLoadingViewSuccess == null){
            //创建一个成功的视图
            mLoadingViewSuccess=initSuccessView();
        }
        //创建完成之后
        if (mLoadingViewSuccess != null){
            this.removeView(mLoadingViewSuccess);
            this.addView(mLoadingViewSuccess);
            mLoadingViewSuccess.setVisibility(mCurrentState ==MCURRENTSTATE_SUCCESS ? View.VISIBLE : View.GONE );

        }
    }


    //加载数据的方法
    public  void  troggleData(){
        //改变视图
        mCurrentState = MCURRENTSTATE_LOADING;
        //刷新
        refreshViewByState();
        //判断任务
        if (mDataTask == null){
            mDataTask = new LoadDataTask();
            //创建线程池执行任务
            ThreadPoolFactory.cureatThreadPoolProxyNormal().cxecute(mDataTask);
        }

    }



    //创建一个任务类
    private  class LoadDataTask implements  Runnable{
        @Override
        public void run() {
            //在子线程执行耗时操作
            ResultCurrentState state = initData();
            //修改状态
            mCurrentState = state.getState();
            //修改ui界面
            UIUtils.safePostTast(new Runnable() {
                @Override
                public void run() {
                    refreshViewByState();
                }
            });
            //任务执行完成,将任务制空
            mDataTask = null;
        }
    }

    //正在加载数据的抽象方法
    protected abstract ResultCurrentState initData();
    //加载视图的抽象方法
    public abstract View initSuccessView();



    //枚举规定返回值
    public  enum  ResultCurrentState{
        //枚举值有3个 成功 失败 空
        SUCCESS(MCURRENTSTATE_SUCCESS),ERROE(MCURRENTSTATE_ERROR),EMPTY(MCURRENTSTATE_EMPTY);
        int state;
        public int getState() {
            return state;
        }
        ResultCurrentState(int state) {
            this.state = state;
        }
    }

}
