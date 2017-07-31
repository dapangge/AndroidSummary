#### CoordinatorLayout的使用

1.使用之前导入Desgin包

  

2.在布局中的使用:

标准模板:

```
<android.support.design.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.it520.coordinatorlayout.MainActivity">

    <android.support.design.widget.AppBarLayout  
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/top"
        >
       <android.support.design.widget.CollapsingToolbarLayout
           android:layout_width="match_parent"
           android:layout_height="300dp"      
           app:layout_scrollFlags="scroll|exitUntilCollapsed|snap"  //设置绑定的类型
           app:contentScrim="@android:color/holo_blue_dark"       //折叠后的颜色
           app:expandedTitleMarginStart="100dp"    	        	
           app:expandedTitleMarginEnd="6dp"		
           android:id="@+id/ctb"
           >
           <ImageView
               android:layout_width="match_parent"
               android:layout_height="300dp"					//设置头部显示的高度
               android:background="@drawable/material_flat"   //设置为背景
               app:layout_collapseMode="parallax"				//设置折叠的模式:视差值的滚动 
               app:layout_collapseParallaxMultiplier="0.8"     //设置视差值
               />
           <android.support.v7.widget.Toolbar		//在顶部的显示
               android:layout_width="match_parent"		
               android:layout_height="?android:attr/actionBarSize"		//设置顶部的高度  
               app:layout_collapseMode="pin"   		//折叠的模式:设置为不滚动	     
               />
       </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>
```

```
//下部分的显示
<android.support.v4.widget.NestedScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"  //头部和顶部的链接   自己定义是																				写该类的全路径		
    android:id="@+id/scroll"
    >
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/content"
        android:textSize="22sp"
        android:textColor="@android:color/black"
        />
</android.support.v4.widget.NestedScrollView>

```

属性值:

app:layout_scrollFlags 属性值介绍:

- scroll：值设为scroll的子控件会与滚动控件一起发生移动

- enterAlways： 先滚动子控件，在滚动可滚动的控件

  - enterAlwaysCollapsed：当滚动View向下滑动时，先enterAlways，当View的高度达到最小高度时，View就暂时不去往下滚动，直到滚动View滑动到顶部不再滑动时，View再继续往下滑动，直到滑到View的顶部结束。

    exitUntilCollapsed：当滚动View向上滑动时， View先缩小到最小尺寸前，滚动View都不会滚动。

  - snap: 子控件滚动到一定位置的时候，自动显示和隐藏。



继续添加控件;

```
    <android.support.design.widget.FloatingActionButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_brightness_medium_white_36dp"
        app:backgroundTint ="@android:color/black"		//颜色
        app:rippleColor="@android:color/darker_gray"   //点击是颜色
        android:clickable="true"
        app:fabSize ="mini"			//设置大小
        app:elevation="5dp"			//阴影
        app:layout_anchor="@id/top"			//锚点  在那个控件的那个位置
        app:layout_anchorGravity="bottom|right"		//具体位置
        android:layout_marginRight="40dp"		//位置
        />

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@mipmap/ic_launcher"
        app:layout_anchor="@id/scroll"		//设置锚点
        app:layout_anchorGravity="center|top"
        />
</android.support.design.widget.CoordinatorLayout>
```

**需要自定义布局内两个控件关系时可以按照下面模板:**

```
//TextView作为观察者  跟着被观察者一起动
public class TextViewEaxyBehavior extends CoordinatorLayout.Behavior<TextView> {

    //实现构造方法
    public TextViewEaxyBehavior() {
    }

    public TextViewEaxyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //查找依赖的对象
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
      return dependency instanceof ImageView; //判断是否是要观察的对象
    }

    //当依赖对象发生改变后
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
    //child:观察者  dependency:被观察者
    //动态改变位置
        child.setX(dependency.getX());
        child.setY(dependency.getY()+dependency.getHeight()+10);
        return true;
    }
}
```

然后在布局中的绑定中引用此协议.

x