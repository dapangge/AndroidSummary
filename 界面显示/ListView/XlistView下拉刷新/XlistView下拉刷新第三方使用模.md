#### XlistView下拉刷新第三方使用模

配置:

```
<string name="xlistview_header_hint_normal">下拉刷新</string>
<string name="xlistview_header_hint_ready">松开刷新数据</string>
<string name="xlistview_header_hint_loading">正在加载...</string>
<string name="xlistview_header_last_time">上次更新时间：</string>
<string name="xlistview_footer_hint_normal">查看更多</string>
<string name="xlistview_footer_hint_ready">松开载入更多</string>
```

```
//设置下拉刷新
all_order_lv.setPullRefreshEnable(true);
//禁止上啦刷新
all_order_lv.setPullLoadEnable(false);
//设置一个监听器
all_order_lv.setXListViewListener(this);
all_order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long itemId = mAllOrderAdapter.getItemId(i);
        //进行页面跳转
        Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
        intent.putExtra(OrderDetailsActivity.ID_KEY,itemId);
        startActivity(intent);
    }
});
```



```
//刷新时间
protected String getCurrentTime() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    return formatter.format(new Date());
}
```

```
//显示刷新时间
all_order_lv.setRefreshTime(getCurrentTime());
//停止刷新
all_order_lv.stopRefresh();
```

布局使用:

```
<com.m520it.www.jdmall.ui.xlistview.XListView
    android:id="@+id/complete_order_lv"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:cacheColorHint="@android:color/transparent"
    android:listSelector="@android:color/transparent"
    android:scrollbars="none" />
```