#### Android下拉刷新PtrFrameLayout的使用

**1.介绍:**

* 可以包含所有的控件 :ListView, GridView, ScrollView, FrameLayout, 甚至 TextView.

* 可以自定义刷新头(这点非常实用)

* 使用简单方便

  不足就是不支持上拉加载.

**2.使用**

* 首先添加依赖到项目

  ```
  compile 'in.srain.cube:ultra-ptr:1.0.11'
  ```

* 在Xml中使用

  ```
  <in.srain.cube.views.ptr.PtrFrameLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:id="@+id/food_refreshLayout"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      app:ptr_resistance="1.7"				//设置下拉的阻尼系数，值越大感觉越难下拉
      app:ptr_ratio_of_header_height_to_refresh="1.2"	//设置超过头部的多少时，释放可以执行刷新操作
      app:ptr_duration_to_close="200"				//：设置下拉回弹的时间
      app:ptr_duration_to_close_header="300"   //设刷新完成，头部回弹时间，注意和前一个进行区别
      app:ptr_keep_header_when_refresh="true"	//设置刷新的时候是否保持头部
      app:ptr_pull_to_fresh="false">		//设置下拉过程中执行刷新，我们一般设置为false

      <ScrollView
          android:layout_width="match_parent"
          android:layout_height="match_parent"
        >
        </ScrollView>

  </in.srain.cube.views.ptr.PtrFrameLayout>

  ```

* 在代码中使用

  在代码中使用非常简单,简单几部搞定:

  1.找到控件,添加头部刷新布局

  ```
  mFoodRefreshLayout = (PtrFrameLayout) findViewById(R.id.food_refreshLayout);
  //这里是一个自定义的头部刷新布局,自带的也有一个布局   new PtrDefaultHandler(); 
  PtrClassicHeader header = new PtrClassicHeader(this); 
  //将头布局添加
  mFoodRefreshLayout.addPtrUIHandler(header);
  ```

  2.不仅仅是添加头布局,还需要设置到控件中  注:特别重要,不然没显示

  ```
  mFoodRefreshLayout.setHeaderView(header); //设置刷新头布局
  ```

  3.给刷新控件设置下拉监听

  ```
  mFoodRefreshLayout.setPtrHandler(new PtrHandler() {
      @Override
      public void onRefreshBegin(PtrFrameLayout frame) {
  		//在这里写自己下拉刷新数据的请求
  		//需要结束刷新头
          mFoodRefreshLayout.refreshComplete();
      }

      @Override
      public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
          // 默认实现，根据实际情况做改动
          return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
      }
  });
  ```

**3.自定义请求头**

​	上面是对基本使用进行了介绍,相信大家在使用下拉刷新时都需要用到自定义布局,其实也很简单,在上面代码添加刷新头时就创建自定义的头部即可,下面对自定义头部的几个方法做简单介绍:

```
public class PtrClassicHeader extends FrameLayout  implements PtrUIHandler{  //实现接口
    private ImageView mPush;
	//在代码创建对象
    public PtrClassicHeader(Context context) {
        super(context);
        initView();   
    }
    public PtrClassicHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public PtrClassicHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化自定义布局文件
    private void initView() {
    //这里加载自定义的布局文件
    View header = 	LayoutInflater.from(getContext()).inflate(R.layout.item_push_header_layout, this);
    //找到布局内部的控件
        mPush = (ImageView) header.findViewById(R.id.header_iv);
    }

	//定义一个动画,方便下面的调用
   public void  initAnim(){
       ObjectAnimator anim = ObjectAnimator.ofFloat(mPush, "rotation", 0f, 180f);
       anim.setDuration(500);
       anim.start();

   }
	//初始化状态
    @Override
    public void onUIReset(PtrFrameLayout frame) {
		//这个方法可以不用管   也可以在这里关闭动画
    }

    //开始向下拉的时候调用
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
            initAnim();  //这里可以执行动画效果
    }

    //刷新过程时调用
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
			//可以不断的改变动画效果以及切换显示的控件
			//判断是否可以刷新 
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText("释放刷新");
        } else {
            mTitleTextView.setText("下拉加载");
        }
    }

    //刷新完成后调用,向上移动时调用
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
		//可以不断的改变动画效果以及切换显示的控件
		 mTitleTextView.setText("加载中...");
        animationDrawable.stop();  //模拟动画
        animationDrawable.start();
    }

    //重复下拉
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
    	//在同一次下拉中不断向上向下移动,这里可以不断改变显示效果
    	//示例代码:  可以当模板使用  
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();  //获取到下拉的高度
        final int lastPos = ptrIndicator.getLastPosY();      //最大下拉的高度
		//根据下拉的位置进行控件的显示
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame); //调用方法
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);  //调用方法
            }
        }
    }
	//下拉到可以刷新时显示
    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setText("释放刷新");
        }
    }
    //动态改变文字
    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText("释放刷新");
        } else {
            mTitleTextView.setText("下拉加载");
        }
    }
    }
}
```

**4.解决冲突**

ViewPager滑动冲突: `直接调用:  disableWhenHorizontalMove()`

如有不懂可查看:https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh/blob/master/README-cn.md