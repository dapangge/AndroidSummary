#### Androii内存优化

**一. 内存泄露的原因:**

​    对无用对象的引用一直未被释放,就会导致内存泄露.

**二. Java中的四种引用:**

​	1.强引用

```
  //引用类型  变量  = new  类型(构造器) 
  String str = new String("abac");
```

​	在上述代码中，这个str对象就是强可及对象。**强可及对象永远不会被GC回收。**它宁愿被抛出OOM异常，也不会回收掉强可及对象。需要手动清空:

```
str = null;
```

​	2.软引用

	//软引用SoftReference
	SoftReference<String> softReference = new SoftReference<String>(str);

​	在上述代码中，这个str对象就是软可及对象。**当系统内存不足时，软可及对象会被GC回收。**

清除软引用对象中的引用链可以通过模拟系统内存不足来清除，也可以手动清除，手动清除如下：

		SoftReference<String> softReference = new SoftReference<String>(str);
		softReference.clear();
​    3.弱引用

		//弱引用WeakReference
		WeakReference<String> weakReference = new WeakReference<>(str);
​	在上述代码中，这个str对象就是弱可及对象。**当每次GC时，弱可及对象就会被回收。**

清除弱引用对象中的引用链可以通过手动调用gc代码来清除，如下：

```java
	WeakReference<String> weakReference = new WeakReference<>(str);
	System.gc();
```

当然，也可以通过类似软引用，调用clear()方法也可以。

​	4.虚引用

	//虚引用PhantomReference
	PhantomReference phantomReference = new PhantomReference<>(arg0, arg1);
​	虚引用一般在代码中出现的频率极低，主要目的是为了检测对象是否已经被系统回收。它在一些用来检测内存是否泄漏的开源项目中使用到过，如LeakCanary。

