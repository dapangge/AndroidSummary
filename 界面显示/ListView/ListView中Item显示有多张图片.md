#### ListView中Item显示有多张图片

```
private void initImageContainer(String imgUr, LinearLayout layout) {
    //获取图片的数据
    List<String> imageUrls = JSON.parseArray(imgUr, String.class);
    //获取到图片的数量
    int dataCount = imageUrls.size();
    //获取到控件中子容器个数
    int childCount = layout.getChildCount();
    //获取到中间数
    int size=Math.min(dataCount,childCount);
    //隐藏原先的控件
    for (int i = 0; i < childCount; i++) {
        layout.getChildAt(i).setVisibility(View.INVISIBLE);
    }
    //4.让需要显示的控件先设置图片源 再显示出来
    for (int i = 0; i < size; i++) {
        ImageView iv = (ImageView) layout.getChildAt(i);
        mImageLoader.displayImage(NetworkConstant.BASE_URL+imageUrls.get(i),iv);
        iv.setVisibility(View.VISIBLE);
    }
    //判断是否有图片
    layout.setVisibility(dataCount>0?View.VISIBLE:View.GONE);
}
```