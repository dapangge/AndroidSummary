#### TextInputLayout的使用

1. 这是Editinput的是升级版本,在使用之间需要导入相对的Design包在项目中,在安卓21之后使用

   常用属性如下：

- app:hintAnimationEnabled 是否开启提示的动画
- app:counterEnabled 是否开启计数功能
- app:counterMaxLength 最多能够输入的字数总数
- android:hint 提示语

常用的方法如下：<p>
   setError 设置错误提示语

 在代码中找到控件直接调用即可

2.在Xml中使用示例:

```
<android.support.design.widget.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hintAnimationEnabled="true"   //是否开启动画
    app:hintEnabled="true"    			
    app:counterEnabled="true"			//是否开启计算
    app:counterMaxLength="10"			//设置最多输入的个数
    android:hint="one"					//提示语
    >
<EditText  						//包裹一个输入框
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/edt"
    />
</android.support.design.widget.TextInputLayout>
```

3.根据需求需要更改提示语与错误提示语的UI

```
//修改提示语的样式 
<style name="TextLabel" >
    <item name="android:textSize">33sp</item>
</style>

//修改错误提示语的样式 
<style name="myError" >
    <item name="android:textSize">33sp</item>
    <item name="android:textColor">@android:color/holo_red_dark</item>
</style>
```

4.设置提示语的样式
​    在xml中直接使用

- app:counterOverflowTextAppearance 超出提示的样式
- app:hintTextAppearance 顶部提示语的样式
- app:errorTextAppearance 错误提示语的样式

！！！！注意，TextInputLayout有一个Bug，这个Bug在统计字数时超出个数时会抛出。建议使用统计字数功能的需要在style文件中加入这句话。

 ![](http://i.imgur.com/k8sHKCQ.png)