### PagerSlidingTabStrip的使用

添加依赖:

```
dependencies { compile 'com.astuetz:pagerslidingtabstrip:1.0.1' }
```

```
<com.astuetz.PagerSlidingTabStrip
    android:id="@+id/tabs"
    android:layout_width="match_parent"
    android:layout_height="48dip"
    app:pstsIndicatorHeight="1dp"
    app:pstsIndicatorColor="#f00"/>

<android.support.v4.view.ViewPager
    android:id="@+id/main_vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

```
//设置导航栏
mMainAdapter = new MainAdapter(getSupportFragmentManager());
mMainVp.setAdapter(mMainAdapter);
mTabs.setViewPager(mMainVp);
```

```
public class MainAdapter extends FragmentStatePagerAdapter {


    private String[] mTitleName;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        //导航的标题名字
        mTitleName = UIUtils.getStringArray(R.array.main_titles);
    }

    @Override
    public Fragment getItem(int position) {
        //工厂类加载
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return mTitleName != null ? mTitleName.length :0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleName[position];
    }
}
```