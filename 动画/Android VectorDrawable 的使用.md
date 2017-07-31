## Android VectorDrawable 的使用

​	SVG的全称是Scalable Vector Graphics，叫可缩放矢量图形。它和位图（Bitmap）相对，SVG不会像位图一样因为缩放而让图片质量下降。它的优点在于节约空间，使用方便
Android 5.0中引入了 VectorDrawable 来支持矢量图(SVG)，同时还引入了 AnimatedVectorDrawable 来支持矢量图动画，在最近几次Support包更新之后，SVG的兼容性问题得以大大改善。  

VectorDrawable 并没有支持所有的 SVG 规范，目前只支持 PathData 和有限的 Group 功能。另外还有一个 clip-path 属性来支持后面绘图的区域。 所以对于使用 VectorDrawable 而言，我们只需要了解 SVG 的 PathData 规范即可。  

// 阿里矢量图标库	

http://www.iconfont.cn/home/index?spm=a313x.7781069.1998910419.1.V1n5x6

#### 一 .创建方式:

* 点击项目的File ----> New --->Vector Asset即可
* 点击项目的Drawable目录右键创建文件 ,修改类型为vector

VectorDrawable，最低到api 7，但AnimatedVectorDrawableCompat到api 11。如果想要兼容之下的版本。需要对gradle进行配置。

```
android {
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}
```

定义好的矢量图文件，在布局中使用app:srcCompat标签，需要使用activity继承于AppCompatActivity，需要用这个标签 ，首页的HomeActivity继承于AppCompatActivity，而AppCompatActivity也继承了FragmentActivity 



#### 二.属性介绍

##### 1.vector元素介绍

有以下几个属性：  

*  name：定义该矢量图形的名字。通过名字找到这个矢量图。  
* width，height：定义该矢量图形的固有宽高(必须的，矢量图内部的宽高intrinsic) ,与外部的宽高无关
*  viewportHeight，viewportWidth：定义画布(viewport)的大小，不需要指定单位。但大小可以理解为一个虚拟单位，将drawable的宽高分成多少等份，在定义path的时候所有数值都是说取drawable宽高的多少份。如viewportWidth和viewportHeight分别为32，32，在path中(16,16)便表示在drawable宽高的中间。所有控制点都必须在viewportWidth和viewportHeight内，超出的部分交不予显示。该属性为必需值。
* alpha：图片的不透明度。  
*  tint 定义该 drawable 的 tint 颜色。默认是没有 tint 颜色的。一般不需设置
*  tintMode 定义 tint 颜色的 Porter-Duff blending 模式，默认值为 src_in。  
*  autoMirrored 设置当系统为 RTL (right-to-left) 布局的时候，是否自动镜像该图片。比如 阿拉伯语。  

##### 2.Path元素

* name：路径的名称。可以在其他地方来引用。  

​	要画动画了，路径1， 到路径2.

* fillColor：图形的填充颜色。设置该属性值后，得到的svg图形就会填充满.(画满)
*  strokeColor：边界的颜色。(画边框)  
*  strokeWidth：边框的宽度。 
*  strokeAlpha：边界透明度。 
*  trimPathEnd：从开始到结束，显示百分比，0，不显示，1 显示。
* trimPathStart：从开始到结束，隐藏百分比，0 不隐藏，1 隐藏。 
*  trimPathOffset：设置截取范围。  
*  strokeLineCap 设置路径线帽的形状，取值为 butt, round, square.  (设置线头为圆滑)
* strokeLineJoin 设置路径交界处的连接方式，取值为 miter,round,bevel.

 strokeMiterLimit 设置路径交叉时候，斜角的上限。当 strokeLineJoin 为 “round” 或 “bevel” 的时候，这个属性无效。为miter时，锐角相交，可能斜面会很长，不协调。所以就为交界的斜面设置上限。默认是10.意味着一个斜面的长度不应该超过线条宽度的 10 倍。

*   **pathData**：定义控制点的位置。 

  所有的参数，都可以大写小写，大写表示是绝对位置。小写是相对位置

  所有的值，都可以用空格来间隔开，或者用逗号间隔开

  ```java
  M：move to 移动绘制点, 一个坐标
  L：line to 直线，一个坐标
  Z：close 闭合，不要参数
  C：三次贝塞尔曲线，三个坐标，前两个为贝塞尔曲线的控制点的坐标，最后一个终点的坐标。
  S：同C，但比C要更平滑。
  Q：二次贝塞尔曲线，两个坐标，第一个表示贝塞尔曲线的控制点坐标，第二个终点的坐标。
  T：同Q，但比q平滑。
  A：ellipse 圆弧，七个参数，
  	(rx ry rotation big_flag sweep_flag x y)
  	rx ry，弧线所属椭圆的半轴的xy的长度，如果xy相等，那么就是一个圆。
  	rotation 旋转角度
  	big_flag 是否大圆 1 为 是， 0 不是
  	sweep_flag 是否顺时针 1 为 是， 0 不是
  	x y 终点坐标
  H:水平画一条直线到指定位置，一个坐标
  v:垂直画一条直线到指定位置 ，一个坐标
  //指令的大小写分别代表着绝对定位与相对定位。绝对定位指的是这个点在drawable中的坐标，而相对定位指的是这个点相较于前一个点移动的坐标。
  ```

##### 3.group元素

有时候我们需要对几个路径一起处理，这样就可以使用 group 元素来把多个 path 放到一起。group 主要是用来设置路径做动画的关键属性的。  

* name 定义 group 的名字  
* rotation 定义该 group 的路径旋转多少度  
*  pivotX 定义缩放和旋转该 group 时候的 X 参考点。该值相对于 vector 的 viewport 值来指定的。  
* pivotY 定义缩放和旋转该 group 时候的 Y 参考点。该值相对于 vector 的 viewport 值来指定的。  
* scaleX 定义 X 轴的缩放倍数  
* scaleY 定义 Y 轴的缩放倍数  
* translateX 定义移动 X 轴的位移。相对于 vector 的 viewport 值来指定的。  
* translateY 定义移动 Y 轴的位移。相对于 vector 的 viewport 值来指定的。  

##### 4.clip_path元素

vector 还支持 clip-path 元素。定义当前绘制的剪切路径。注意，clip-path 只对当前的 group 和子 group 有效。 

name 定义 clip path 的名字  

pathData 和 android:pathData 的取值一样。   



#### 三.示例

* 画太极图的示例:

```java
<vector xmlns:android="http://schemas.android.com/apk/res/android"
android:height="50dp"
    android:width="50dp"
    android:viewportHeight="100.0"
    android:viewportWidth="100.0"
>
<!--左边的是两个四分之一圆-->
<path
    android:fillColor="#000000"
    android:name="黑鱼"
    android:pathData="M0 50,
        A50 50 0 0 1 50 0,
        A25 25 0 0 0 50 50,
        A25 25 0 0 1 50 100,
        A50 50 0 0 1 0 50"/>
<path
    android:fillColor="#ffffff"
    android:name="白眼"
    android:pathData="M43 75,A7 7 0 1 1 57 75, A7 7 0 1 1 43 75"/>
<!--右边画一个半圆-->
<path
    android:fillColor="#FFFFFF"
    android:name="白鱼"
    android:pathData="M50 0,
        A50 50 0 1 1 50 100,
        A25 25 0 1 0 50 50,
        A25 25 0 1 1 50 0"/>
<path
    android:fillColor="#000000"
    android:name="黑眼"
    android:pathData="M43 25,A7 7 0 1 1 57 25, A7 7 0 1 1 43 25"/>
</vector>	
```


#### 四.VectorDrawable动画

1. 创建animator_vector 资源文件
2. 在资源文件中定义target，指定为某个path或者group去设定动画
3. 创建objectAnimator资源文件。注意，这个文件，需要定义在values/animator文件夹下。

定义一个笑脸，从哭脸变成笑脸。  

```java
<?xml version="1.0" encoding="utf-8"?>
	<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:name="smile"
    android:width="30dp"
    android:height="30dp"
    android:viewportHeight="100"
    android:viewportWidth="100"
>
    <!--脸-->
    <path
        android:name="smile_circle"
        android:fillColor="#f7ec22"
        android:pathData="M0 50, A50 50 0 0 1 100 50, A50 50 0 0 1 0 50"/>
    <!--嘴巴-->
    <path
        android:name="mouth"
        android:strokeWidth="5"
        android:strokeColor="#00ff00"/>
</vector>
```

定义笑脸动画：

```java
<?xml version="1.0" encoding="utf-8"?>
<objectAnimator xmlns:android="http://schemas.android.com/apk/res/android"
                android:duration="1000"
                android:propertyName="pathData"
                android:valueFrom="M30 70, Q50 50 70 70"
                android:valueTo="M30 70, Q50 90 70 70"
                android:valueType="pathType"
    >
</objectAnimator>
```

关联动画和矢量图  

```java
<animated-vector xmlns:android="http://schemas.android.com/apk/res/android"
     android:drawable="@drawable/smile"
    >

    <target
        android:animation="@animator/smile_anim"
        android:name="mouth"/>
</animated-vector>
```

在布局中使用vector矢量图文件  

```java
<ImageView
    android:id="@+id/iv"
    android:background="#000000"
    android:layout_width="60dp"
    android:layout_height="60dp"
    app:srcCompat="@drawable/smile_anim"
    />
    
    
// 在代码中开启动画

ImageView iv = (ImageView) findViewById(R.id.iv);

Animatable a = (Animatable) iv.getDrawable();

a.start();
 
    
    
```

