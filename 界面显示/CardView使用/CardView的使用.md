#### CardView的使用

CardView 是MD提供的一种带圆角、阴影的容器。效果如下图：

![image](http://www.jcodecraeer.com/uploads/20151025/1445743798116099.png)

CardView是google在suppurt包提供的，如果我们需要使用这个控件，我们需要增加这个控件的依赖。为项目增加依赖的方式有两种：

- 1 使用Gradle依赖
- 2 在本地找到相应版本的aar包，绑定  注意导入的arr包必须和项目的版本保持一致

本地的资源包目录如下

![](http://i.imgur.com/9qLH00t.png)



示例代码:

```
<android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:cardCornerRadius="20dp"   //圆角的大小
    android:layout_margin="30dp"    //距离
    app:cardElevation="10dp"		//阴影背景	
    app:cardBackgroundColor="@android:color/holo_blue_dark"		//背景颜色
    >
```

![image](http://www.jcodecraeer.com/uploads/20151025/1445743798116099.png)

![image](http://www.jcodecraeer.com/uploads/20151025/1445743798116099.png)