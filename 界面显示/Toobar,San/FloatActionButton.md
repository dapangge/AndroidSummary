#### FloatActionButton

 android 在 Material Designer 中提供了一种漂浮的按钮

1.使用需要导入Desgin包

2.常用的属性：

- android:src 中间的icon
- app:backgroundTint 背景的颜色
- app:rippleColor 点击时的颜色
- app:borderWidth 应该设置为0，否则会在4.1会显示为正方形，而在5.0以后没有阴影效果
- app:fabSize 控件的大小，有两个值 normal和mini
- app:elevation 默认显示的阴影的大小

3.代码示例:

```
    <android.support.design.widget.FloatingActionButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_brightness_medium_white_36dp"
        app:backgroundTint ="@android:color/black"
        app:rippleColor="@android:color/darker_gray"
        android:layout_centerInParent="true"
        android:clickable="true"
        app:fabSize ="mini"
        app:elevation="5dp"
        />
</RelativeLayout>
```

#### ToolBar的使用

  google在旧版本推出过一个控件ActionBar,但是ActionBarzhi只能固定在顶部，不能灵活使用，所以google推出了一个ToolBar来替换ActionBar,我们可以配合一些新的控件实现很多酷炫的效果

代码示例:

```
<android.support.v7.widget.Toolbar
    android:layout_width="match_parent"
    android:layout_height="?android:attr/actionBarSize"
    android:id="@+id/tool"
    android:background="@color/colorAccent"
    />
```

```
tool = (Toolbar) findViewById(R.id.tool);
tool.setLogo(R.drawable.ic_brightness_medium_white_36dp);   //设置标题图片
tool.setTitle("我是一个toolBar");		//设置名称
```

#### Snackbar的使用

  可以进行下一步操作

```
    Snackbar bar = Snackbar.make(view,"下课了,我要去吃饭",Snackbar.LENGTH_INDEFINITE);
    //设置可以点击的按钮名字 以及点击事件的实现
    bar.setAction("下课", new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                finish();
        }
    });
    bar.show();
}
```