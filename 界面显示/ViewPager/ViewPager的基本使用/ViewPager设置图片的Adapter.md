#### ViewPager设置图片的Adapter

```
public class HomePagerAdapter extends PagerAdapter {

    Context mContext; //传递上下文
    ArrayList<ImageView> mImageViews = new ArrayList<>();  //储存图片

    public HomePagerAdapter(Context c) {
        mContext = c;
    }

    //创建一个方法传递数据
    public  void  setDatas(List<RBanner> datas){
        //清除原先的图片
        mImageViews.clear();
        for (int i = 0; i< datas.size(); i++){
            //循环一次创建一个图片控件
            ImageView view = new ImageView(mContext);
            //获取到图片
           String  url =  NetworkConstant.BASE_URL +  datas.get(i).getAdUrl();
            AsyncImageLoader.getInst(mContext).displayImage(url,view);
            //设置图片的宽高
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                    );
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(params);
            //添加到容器
            mImageViews.add(view);

        }

    }

    @Override
    public int getCount() {
        return mImageViews.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //添加一个到容器中
        ImageView imageView = mImageViews.get(position);
        container.addView(imageView);
        return imageView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //删除
        ImageView imageView = mImageViews.get(position);
        container.removeView(imageView);
    }
}
```