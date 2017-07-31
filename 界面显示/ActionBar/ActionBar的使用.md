## ActionBar的使用

```
// 获取ActionBar
//mActionBar.setSubtitle("SubTitle");// 设置子title部分
//mActionBar.setIcon(R.mipmap.ic_launcher);// 设置应用图标
//mActionBar.setDisplayShowTitleEnabled(true);// 设置菜单 标题是否可见
// mActionBar.setDisplayShowHomeEnabled(true);// 设置应用图标是否
// mActionBar.setDisplayUseLogoEnabled(false);// 设置是否显示Logo优先
mActionBar = getSupportActionBar();
mActionBar.setTitle("GooglePlayer");// 设置主title部分
mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮是否可见
```

```
//设置箭头和DrawerLayout 的相互监听
mToggle.syncState();
//设置监听到toggle
mDrawerLayout.setDrawerListener(mToggle);
```

```
//复写actiobar里面的箭头回退和出来时间
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            mToggle.onOptionsItemSelected(item);
            break;
    }
    return super.onOptionsItemSelected(item);
}
```