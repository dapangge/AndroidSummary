package com.a520it.mygoogleplay.base;

import android.view.View;

/**
 * Created by ASUS on 2017/6/12.
 */

public abstract class BaseHolder<T> {

    public View mHoleder;
    public  T  mData;


    public BaseHolder() {
        mHoleder = initHolder();
        mHoleder.setTag(this);
    }

    public  void setDataAndRefreshViewHolder(T data){
        this.mData=data;
        reshViewHolder(mData);
    }

    //赋值的具体操作
    protected abstract void reshViewHolder(T data);


    //初始化控件
    public abstract View initHolder();
}
