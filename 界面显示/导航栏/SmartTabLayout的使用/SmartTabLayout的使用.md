### SmartTabLayout的使用

布局中使用:

```
<com.ogaclejapan.smarttablayout.SmartTabLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/viewPagerTab"
    android:layout_width="match_parent"
    android:layout_height="35dp"
    app:stl_defaultTabTextColor="@color/tab_text_color"
    app:stl_defaultTabTextHorizontalPadding="18dp"
    app:stl_defaultTabTextSize="16sp"
    app:stl_dividerThickness="0dp"
    app:stl_indicatorColor="@color/colorMain"
    app:stl_indicatorInterpolation="linear"
    app:stl_indicatorThickness="2dp"
    app:stl_underlineThickness="0dp"
    />
```

```

    <android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/viewPager"/>

    
```

代码中使用:

```
//获取到标题的名字
String[] stringArray = getResources().getStringArray(R.array.news_tiltes);
mNewsAdapter = new NewsAdapter(getChildFragmentManager(),stringArray);
mFragmentNewsVp.setAdapter(mNewsAdapter);
//绑定标题
mTl.setViewPager(mFragmentNewsVp);
```

Adapter代码:

```
public class NewsAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment>  mFragments = new ArrayList<>();
    String[] mTilts	//列表名字 

    public NewsAdapter(FragmentManager fm ,String[] stringArray) {
        super(fm);
        for (int i = 0; i < 10 ; i++){
            if (i == 0){
                mFragments.add(new HotlistsFragment());

            }else {
                mFragments.add(new OtherFragment());
            }
        }
        mTilts = stringArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTilts[position];
    }
}
```