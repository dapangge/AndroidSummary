### Fragment + ViewPage的使用

fragemt:

```
  //设置
    mVp = (ViewPager) findViewById(R.id.vp);
    mOrdersAdapter = new OrdersContainerAdapter(getSupportFragmentManager());
    mVp.setAdapter(mOrdersAdapter);
    //设置一个监听
    mVp.addOnPageChangeListener(new JDPagerChangeListener(){
        @Override
        public void onPageSelected(int position) {
            //动态修改指示器
            switch (position){
                case  0 :
                    changeIndicatorView(mAllOrderView);
                    break;
                case  1:
                    changeIndicatorView(mWaitPayView);
                    break;
                case  2 :
                    changeIndicatorView(mWaitReceiveView);
                    break;
                case  3 :
                    changeIndicatorView(mWaitSureView);
                    break;
            }
        }
    });

}
//点击事件
@Override
public void onClick(View view) {
    switch (view.getId()){
        case R.id.all_order_ll:
            mVp.setCurrentItem(0,true);
            break;
        case R.id.wait_pay_ll:
            mVp.setCurrentItem(1,true);
            break;
        case R.id.wait_receive_ll:
            mVp.setCurrentItem(2,true);
            break;
        case R.id.wait_sure_ll:
            mVp.setCurrentItem(3,true);
            break;
    }
}
//动态改变指示器
private void changeIndicatorView(View v){
    mAllOrderView .setVisibility(v==mAllOrderView?View.VISIBLE:View.INVISIBLE);
    mWaitPayView  .setVisibility(v==mWaitPayView?View.VISIBLE:View.INVISIBLE);
    mWaitReceiveView .setVisibility(v==mWaitReceiveView?View.VISIBLE:View.INVISIBLE);
    mWaitSureView .setVisibility(v==mWaitSureView?View.VISIBLE:View.INVISIBLE);
}
```

ViewPager:

```
public class OrdersContainerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> mFragments=new ArrayList();

    //创建对象
    public OrdersContainerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new AllOrderFragment());
        mFragments.add(new WaitPayFragment());
        mFragments.add(new WaitReceiveFragment());
        mFragments.add(new CompleteOrderFragment());
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
```