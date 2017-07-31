package com.a520it.mygoogleplay.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.a520it.mygoogleplay.activity.DetailActivity;
import com.a520it.mygoogleplay.base.BaseHolder;
import com.a520it.mygoogleplay.bean.ListBean;
import com.a520it.mygoogleplay.conf.Constants;
import com.a520it.mygoogleplay.factory.ThreadPoolFactory;
import com.a520it.mygoogleplay.holeder.LoadMoreHolder;
import com.a520it.mygoogleplay.utils.LogUtils;
import com.a520it.mygoogleplay.utils.UIUtils;

import java.util.List;

/**
 * Created by ASUS on 2017/6/12.
 */

public abstract class SuperMyBaseAdapter<T> extends MyBaseAdapter {



    public static final int LISTVIEW_ITEM_LOAD_MORE = 0;//当前的item是加载更多的item
    private static final int LISTVIEW_ITEM_NORMAL = 1;//当前的item是一个普通的item
    protected LoadMoreHolder mLoadMoreHolder;
    private int mStae;


    //点击事件
    @Override
    public void onItemClick(AdapterView adapterView, View view, int i, long l) {
        i = i -mListView.getHeaderViewsCount();
        //判断是否是最后一个
        if (getItemViewType(i) == LISTVIEW_ITEM_LOAD_MORE){
            //状态是加载失败
            if (mStae == mLoadMoreHolder.LOADMORE_ERROR){
                //继续加载
                troggleLoadMoreData();
            }else {

            }
        }else{
            //页面跳转
            startactivity(adapterView,view,i,l);
        }
    }

    //页面跳转
    private void startactivity(AdapterView dapterView, View view, int i, long l) {
        ListBean  bean = (ListBean) mData.get(i);
        String packageName = bean.getPackageName();
        // Toast.makeText(UIUtils.getContext(),packageName,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(UIUtils.getContext(), DetailActivity.class);
        //如果在acitivity外面跳转到activity需要添加flag
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.PACKAGENAME,packageName);
        UIUtils.getContext().startActivity(intent);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHolder  baseHolder;
        if (view == null){
            //寻找控件
            if (getItemViewType(i) == LISTVIEW_ITEM_LOAD_MORE){
                baseHolder =initLoadMoreHolder();      //加载更多布局
            }else {
                baseHolder =specalHolder(i);
            }
        }else {
            //获取缓存
            baseHolder = (BaseHolder) view.getTag();
        }


        //设置值
        if (getItemViewType(i) ==LISTVIEW_ITEM_LOAD_MORE ) {
                  if (haseLoadMore()){
                      //进行数据请求
                     troggleLoadMoreData();
                  }else {
                      mLoadMoreHolder.reshViewHolder(3);
                  }
        }else {
            baseHolder.setDataAndRefreshViewHolder(mData.get(i));
        }
        //返回界面
        return baseHolder.mHoleder;
    }

    //加载更多的数据请求
    private void troggleLoadMoreData() {
        mStae = LoadMoreHolder.LADMOREING;
        mLoadMoreHolder.reshViewHolder(mStae); //修改显示
       //线程池执行加载任务
        ThreadPoolFactory.cureatThreadPoolProxyNormal().cxecute(new LoadMoreDataTask());
    }

    //判断是否有跟多加载
    public boolean haseLoadMore() {
        return false;
    }

    //数据加载的具体实现
    protected List<T> initLoadData() {
        return null;
    }


    //加载更多
    private BaseHolder initLoadMoreHolder() {
        if (mLoadMoreHolder == null){
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() -1){
            return  LISTVIEW_ITEM_LOAD_MORE;
        }else {
            return getListviewItemNormalitem(position);
        }

    }

    //多的布局
    public int getListviewItemNormalitem(int position) {
        return LISTVIEW_ITEM_NORMAL;
    }


    @Override
    public int getCount() {
        //添加了一个布局 总数加1
        return super.getCount() + 1 ;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public abstract BaseHolder specalHolder(int i);



     //加载任务
    private  class  LoadMoreDataTask implements  Runnable{
        @Override
        public void run() {
             //创建集合储存返回的数据
            final List<T> loadData;
            try {
            loadData = initLoadData();  //子类具体实现
            //数据效验
            if (loadData == null){
                mStae = LoadMoreHolder.LOADMORE_ERROR;
            }else {
                //判断数据条数
                if (loadData.size() == Constants.PAGESIZE){
                    mStae = LoadMoreHolder.LADMOREING;
                }else {
                    mStae = LoadMoreHolder.LOADMORE_EMPTY;
                }
            }
                //加载完成
            final int finalStae = mStae;
                UIUtils.safePostTast(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.v("l",mData.size() +"");
                        mData.addAll(loadData);           //添加数据
                        notifyDataSetChanged();          //刷新界面
                        mLoadMoreHolder.reshViewHolder(finalStae);  //显示
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
