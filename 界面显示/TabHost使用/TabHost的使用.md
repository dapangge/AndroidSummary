## TabHost的使用

布局:

```
<FrameLayout
    android:id="@+id/main_fl"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    />


<com.a520it.newsmy.ui.MyFragmentTabHost
    android:id="@+id/main_fh"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    >

</com.a520it.newsmy.ui.MyFragmentTabHost>
```

```
//初始化
private void initTabHost() {
    //设置内容
    mMainFh.setup(getApplicationContext(),getSupportFragmentManager(),R.id.main_fl);
    //准备数据
    String[]  tabNanme = new String[]{"新闻","直播","话题","我的"};
    int[]  tabId = new int[]{R.drawable.tab_news,R.drawable.tab_va,R.drawable.tab_topic,R.drawable.tab_my};
    Class[] framents = new Class[]{NewsFragment.class, LiveFragment.class, TopicFragment.class, MyFragment.class};
    //创建内容
    for (int i = 0;i< tabNanme.length; i++){
        TabHost.TabSpec spec = mMainFh.newTabSpec(String.valueOf(i)); //设置每一个的标示
        //加载一个布局
        View inflate = View.inflate(getApplicationContext(), R.layout.main_bottom_view, null);
        ImageView view_iv = (ImageView) inflate.findViewById(R.id.buttom_view_iv);
        TextView view_tv = (TextView) inflate.findViewById(R.id.buttom_view_tv);
        view_iv.setImageResource(tabId[i]);
        view_tv.setText(tabNanme[i]);
        //设置布局
        spec.setIndicator(inflate);
        Class frament = framents[i];
        //添加
        mMainFh.addTab(spec,frament,null);
    }
}

private void initView() {
    mMainFl = (FrameLayout) findViewById(R.id.main_fl);
    mMainFh = (MyFragmentTabHost) findViewById(R.id.main_fh);
    mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
}
```
添加点击事件

```
mMainFh.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("0")){
            Toast.makeText(MainActivity.this, "第一个", Toast.LENGTH_SHORT).show();
        }
        if (tabId.equals("1")){
            Toast.makeText(MainActivity.this, "第二个", Toast.LENGTH_SHORT).show();
        }
        if (tabId.equals("2")){
            Toast.makeText(MainActivity.this, "第三个", Toast.LENGTH_SHORT).show();
        }
        if (tabId.equals("3")){
            Toast.makeText(MainActivity.this, "第四个", Toast.LENGTH_SHORT).show();
        }
    }
});
```