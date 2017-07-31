#### Scene（场景）的使用

android 在transition动画中提出一个概念叫做场景，什么叫做场景？场景就是一个布局或部分布局，我们可以使用场景来创建动画。需要运行在5.0以上

示例代码:

```
//确定好舞台的父控件   需要移动的地方
content = (RelativeLayout) findViewById(R.id.content);
//把场景实例出来
//把需要动画的对象设置相同的id,两个场景根据开始和结束的位置,系统会自动的计算出动画了
if(Build.VERSION.SDK_INT>=19){
	//创建出2个场景
    one    =   Scene.getSceneForLayout(content,R.layout.activity_scence,this);
    two    =   Scene.getSceneForLayout(content,R.layout.activity_scence2,this);
}
```

```
//移动场景
public void gotoTwo(View view){
    if(Build.VERSION.SDK_INT>=19){
        TransitionManager.go(two,new ChangeBounds());
    }
}

public void backtoOne(View view){
    if(Build.VERSION.SDK_INT>=19){
        TransitionManager.go(one,new ChangeBounds());
    }
}
```

3 延迟场景变化，TransitionManager就会根据场景变化自动执行动画

​	TransitionManager.beginDelayedTransition(Viewgroup group) 



**Transition（变化）**

监听一个容器内部有控件的状态发生改变后执行的动画

一个有四种动画效果:

- Slide   划入
- Explode 爆炸
- Fade    隐藏
- changeBounds 位置与大小变化  

示例代码:

```
TransitionManager.beginDelayedTransition(recyle,  //开始监听的父容器
new Slide(Gravity.RIGHT)); //开始划入划出的动画  可以设置方向
```

```
Explode explode = new Explode(); //创建动画 
explode.setDuration(1000);  //设置动画的时间
TransitionManager.beginDelayedTransition(recyle,explode); //开始监听动画
```





**Activity跳转的动画**



在代码中设置可以动画,只有在21版本后可以使用:

Transition 与 Activity的跳转动画有以下几个

- setEnterTransition 进入的动画
- setExitTransition  退出的动画
- setReturnTransition 返回的动画
- setReenterTransition 重入的动画



- **示例代码(在代码中定义动画):**

```
//进行版本控制
if (Build.VERSION.SDK_INT >=21){
    Slide slide = new Slide(Gravity.RIGHT);  //创建动画
    getWindow().setExitTransition(slide);   //设置界面退出是动画
    //开启允许transition切换
    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
}
```

在界面跳转时执行一个动画

```
//按照这个格式来写
ActivityCompat.startActivity(this,
intent,
ActivityOptions.makeSceneTransitionAnimation(this).toBundle());  //执行设置的退出时动画
```





* **在Xml中定义的动画:**

在资源文件中创建transition的文件夹.

```
<?xml version="1.0" encoding="utf-8"?>
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
      <explode/>  //设置动画
      <fade/>
</transitionSet>
```

```
//进行版本控制
if (Build.VERSION.SDK_INT >=21){
    //引入Xml中设置的动画
    TransitionInflater inflater = TransitionInflater.from(this);
    //加载动画
    Transition transition = inflater.inflateTransition(R.transition.one);
    //设置动画
    getWindow().setExitTransition(transition);
    //开启允许transition切换
    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
}
```

在界面跳转时同上设置跳转.