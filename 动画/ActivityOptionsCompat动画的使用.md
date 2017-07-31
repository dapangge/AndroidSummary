#### ActivityOptionsCompat动画的使用

1. **makeCustomAnimation平移动画**

类似overridePendingTransition的功能，为两个activity设置了过渡动画，enterResId 是进入activity的动画，exitResId是退出的activity的动画

```
//1使用自定义动画切换
public void gotoDetailCustomAnimation(View view){
    ActivityOptionsCompat compat =  			        ActivityOptionsCompat.makeCustomAnimation(this,R.anim.in,R.anim.out);  //进来和出去的动画
    Intent intent  =  new Intent();
    intent.setClass(this,DetailActivity.class);
    //一定要这么写
    ActivityCompat.startActivity(this,intent,compat.toBundle());
}
```

**2 makeScaleUpAnimation缩放动画**

新的activity产生一个缩放的动画，这个动画的起点是，相对于source这个View 的左上角偏移（startX，startY）位置产生一个动画，新的activity的初始宽度和高度是width 和 height。

```
//2使用一个缩放动画切换
public void gotoScaleAnimation(View view){
    ActivityOptionsCompat compat =  ActivityOptionsCompat.makeScaleUpAnimation(view,
    (int)(view.getWidth()/2f),  //从点击的View的宽度一半
    (int)(view.getHeight()/2f),	//高度的一半
    0,0);						//偏移量
    Intent intent  =  new Intent();
    intent.setClass(this,DetailActivity.class);
    //一定要这么写
    ActivityCompat.startActivity(this,intent,compat.toBundle());
}
```

**3 makeThumbnailScaleUpAnimation指定图片缩放动画**

 新的activity产生一个缩放的动画，这个动画的起点是，相对于source这个View 的左上角偏移（startX，startY）位置产生一个动画，新的activity的初始宽度和高度是这个bitmap的宽高      

```
//3使用一个缩放动画切换
public void gotoThumbnailAnimation(View view){
	//打开一个图片
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a1);
    ActivityOptionsCompat compat =  		ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, //位置
    bitmap,	//缩放的图片
    0,0);	//偏移量
    Intent intent  =  new Intent();
    intent.setClass(this,DetailActivity.class);
    //一定要这么写
    ActivityCompat.startActivity(this,intent,compat.toBundle());
}
```

4 **makeSceneTransitionAnimation共享元素动画**

新的activity产生一个过渡动画，动画由两个页面的相同元素生成，这种效果只支持5.0以上的手机。

//两个Activity设置相同的控件 ,相同的ID,系统自动生成动画

```
//4使用一个共享元素切换
public void gotoShareAnimation(View view){
	//打开一个图片
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a2);
    ActivityOptionsCompat compat =  ActivityOptionsCompat.makeSceneTransitionAnimation(
    this,
    findViewById(R.id.share),  //获取到共享元素的id
    "one");       //元素的名字
    Intent intent  =  new Intent();
    intent.setClass(this,DetailActivity.class);
    //一定要这么写
    ActivityCompat.startActivity(this,intent,compat.toBundle());
}
```
```
<ImageView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/a1"
    android:id="@+id/share"
    android:clickable="true"
    android:transitionName="@string/share_one"   //共享元素的名字
    />
```

多个共享元素的过渡动画:

```
//5使用多个共享元素切换
public void gotoPairShareAnimation(View view){
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a2);
    ActivityOptionsCompat compat =  ActivityOptionsCompat.makeSceneTransitionAnimation(
    this,
 Pair.create(findViewById(R.id.share),  //第一个共享元素
 getResources().getString(R.string.share_one)),
 
 Pair.create(findViewById(R.id.share2),"two"));  //第二个共享元素
    Intent intent  =  new Intent();
    intent.setClass(this,DetailActivity.class);
    //一定要这么写
    ActivityCompat.startActivity(this,intent,compat.toBundle());
}
```