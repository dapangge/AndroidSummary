package com.a520it.mygoogleplay.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a520it.mygoogleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/6/10.
 */

public abstract class BaseFragment extends Fragment {

    public Loadingpager mLoadingpager;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //创建一个View
        mLoadingpager = new Loadingpager(UIUtils.getContext()) {
            @Override
            public View initSuccessView() {
                return BaseFragment.this.initView();
            }

            @Override
            protected ResultCurrentState initData() {
                return  BaseFragment.this.initData();
            }
        };
        return mLoadingpager;
    }


    //交由子类具体实现的抽象方法
    protected abstract Loadingpager.ResultCurrentState initData();
    protected abstract View initView();



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //用来检测获得的数据是否为空,是否正常
    public Loadingpager.ResultCurrentState checkeResult(Object obj){
        // 1判断obj是否为空
        if (obj==null){
            return Loadingpager.ResultCurrentState.ERROE;
        }else{
            //判断内部是否有集合
            if (obj instanceof List){
                if (((List)obj).size()>0){
                    return Loadingpager.ResultCurrentState.SUCCESS;
                }

            }
        }
        return Loadingpager.ResultCurrentState.SUCCESS;
    }

}
