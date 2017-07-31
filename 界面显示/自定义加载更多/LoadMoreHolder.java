package com.a520it.mygoogleplay.holeder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a520it.mygoogleplay.R;
import com.a520it.mygoogleplay.base.BaseHolder;
import com.a520it.mygoogleplay.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ASUS on 2017/6/14.
 * 加载更多
 */

public class LoadMoreHolder extends BaseHolder<Integer> {

    public  static  final  int LADMOREING=0;//正在加载更多
    public  static  final  int LOADMORE_ERROR=1;//正在加载更多失败
    public  static  final  int LOADMORE_EMPTY=2;//正在加载更多失败

    @Bind(R.id.item_loadmore_container_loading)
    LinearLayout mItemLoadmoreContainerLoading;
    @Bind(R.id.item_loadmore_tv_retry)
    TextView mItemLoadmoreTvRetry;
    @Bind(R.id.item_loadmore_container_retry)
    LinearLayout mItemLoadmoreContainerRetry;

    @Override
    public View initHolder() {
        View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void reshViewHolder(Integer data) {
        //隐藏所有
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (data) {
            case LADMOREING://正在加载更多
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;

            case LOADMORE_ERROR://加载更多失败
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
        }
    }




}
