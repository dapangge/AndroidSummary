#### NestedScrollView的使用

1.NestedScrollView是ScrollView的升级版

​	支持包裹内部的控件进行滑动,可以替代使用,在使用前需要导入相对应的Design包

2.代码示例:

```
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.it520.cardviews.MainActivity"
    android:orientation="vertical"
    >
<ScrollView   					 //外层包裹
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        >
        <android.support.v4.widget.NestedScrollView   //使用
            android:layout_width="match_parent"
            android:layout_height="400dp"
            android:background="@color/colorAccent"
            >
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@string/content"
                />
        </
```