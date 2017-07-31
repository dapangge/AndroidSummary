[TOC]

### 											RxJava学习笔记

 #### 1.什么是RxJava

​	**一个实现异步操作的库**   

RxJava依赖:

```
   compile 'io.reactivex.rxjava2:rxjava:2.0.1'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
```

#### 2.RxJava的好处

​	**随着程序逻辑变得越来越复杂，它依然能够保持简洁。**

#### 3.RxJava 的观察者模式 

​	RxJava有四个观察者模式 :

​	**3.1基本概念**

​		`Observable` (被观察者)

​		 `Observer` (观察者)

​		 `subscribe` (订阅)、事件。

​		Observable` 和 `Observer` 通过 `subscribe()` 方法实现订阅关系，从而 `Observable` 可以在需要的时候发出事件来通知 `Observer`。

​	**3.2事件回调的三种方式**

​		onNext()   普通的回调方法

​		onCompleted()  发出事件的集合 

​		onError()   事件中的异常时调用 同时会终止事件发送

		总结: onCompleted()和onError()在一个队列中只能存在其中一种,并且在队列末端.观察者接收到其中一个回调之后就会停止接收事件.

​	**3.3 Observer的基本使用**

​		除了Observer的接口外,另外还有一个抽象类对其进行扩展:Subscriber  基本使用都一样

		创建方法: 

​	Subscriber<String> subscriber = new Subscriber<String>() {
​		    public void onNext(String s) {
​     		 	
   			 }

​    		
​    		 public void onCompleted() {
​       	
   			 }

   	     public void onError(Throwable e) {

  			  }
};

* 带有一个`Consumer`参数的方法表示下游只关心onNext事件, 其他的事件我假装没看见, 因此我们如果只需要onNext事件可以这么写:

  ```
  //订阅观察者
  subscribe(new Consumer<Integer>() {
              @Override
              public void accept(Integer integer) throws Exception {
                  Log.d(TAG, "onNext: " + integer);
              }
  ```

* Subscriber的不同

    onStart()   在事件开始之前调用 可以用作数据的清理和重置 但不能做耗时操作, 可以使用		  		doOnsubscrible方法进行耗时操作

   unsubscribe()  取消订阅的方法, 在此方法调用前可以使用isUnsubscribe进行状态判断,要注意关闭订阅,以免发生内存泄露

   ​

   **3.4 Observable的基本使用**

   Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
   ​    @Override
   ​    public void call(Subscriber<? super String> subscriber) {
   ​        subscriber.onNext("Hello");
   ​        subscriber.onNext("Hi");
   ​          subscriber.onNext("Aloha");
   ​        subscriber.onCompleted();
   ​    }
   });

* 被观察者调用create()方法创建对象, 传入OnSubscribe参数回调时使用,当被订阅时就会调用call方法,把事件传递给参数中的Subscriber(观察者)

* 除了调用create()方法传递参数还可以调用just()方法快捷传递参数

   **Observable observable = Observable.just("Hello", "Hi", "Aloha");**

* 调用form()方法可以吧一个对象/数组进行传递 

   **String[] words = {"Hello", "Hi", "Aloha"};**
   **Observable observable = Observable.from(words);**

   ​

**3.5 Subscribr(订阅)**

* 创建了observable和obssever之后,在调用subscribe()方法将之链接

  ​observable.subscribe(observer);
  ​// 或者：
  ​observable.subscribe(subscriber);

* `Action1` 可以将 `onNext(obj)` 和 `onError(error)` 打包起来传入 `subscribe()` 以实现不完整定义的回调。事实上，虽然 `Action0` 和 `Action1` 在 API 中使用最广泛，但 RxJava 是提供了多个 `ActionX` 形式的接口 (例如 `Action2`, `Action3`) 的，它们可以被用以包装不同的无返回值的方法

* 列如:

  ```
      //创建数组
      String[] name = new String[]{"a","b","c"};
      //创建被观察者调用订阅方法传递给观察者进行输出
       Observable.from(name).subscribe(new Action1<String>() {
          @Override
          public void call(String s) {
              Log.v("520",s);
          }
      });
  }
  ```

  例二:

```
//字符串的传递
    //创建一个被观察者          
Observable.create(new Observable.OnSubscribe<String>() {
    @Override
    public void call(Subscriber<? super String> subscriber) {
        //需要传递的对象
        observer.onNext("cccc");
    }
    //订阅观察者
}).subscribe(new Observer<String>() {
    @Override
    public void onCompleted() {
        
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(String s) {
        //接收传递的对象
    }
});
```

​	**上面两个例子都是在同一线程中执行.**

#### 4.线程控制器  Scheduler

​	RxJava通过它来指定每一段代码应该运行在怎样的线程,RxJava内置一下几种控制器:

* `Schedulers.immediate()`: 直接在当前线程运行，相当于不指定线程。这是默认的 `Scheduler`。

* `Schedulers.newThread()`: 总是启用新线程，并在新线程执行操作

* `Schedulers.io()`: I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 

* `Schedulers.computation()`: 计算所使用的 `Scheduler`。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 `Scheduler` 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 `computation()` 中，否则 I/O 操作的等待时间会浪费 CPU。

* 另外， Android 还有一个专用的 `AndroidSchedulers.mainThread()`，它指定的操作将在 Android 主线程运行。

  有了这几个 `Scheduler` ，就可以使用 `subscribeOn()` 和 `observeOn()` 两个方法来对线程进行控制了。

  `subscribeOn()`: 指定 `subscribe()` 所发生的线程，即 `Observable.OnSubscribe`被激活时所处的线程。或者叫做事件产生的线程。

  `observeOn()`: 指定 `Subscriber` 所运行在的线程。或者叫做事件消费的线程。

  ```
  //线程切换演示
  Observable.just(1,2,3)
          .subscribeOn(Schedulers.io())   //让事件在子线程在子线程执行
          .observeOn(AndroidSchedulers.mainThread())      //观察者返回主线程执行
          .subscribe(new Action1<Integer>() {
              @Override
              public void call(Integer integer) {
                  Toast.makeText(MainActivity.this, "出来了", Toast.LENGTH_SHORT).show();
              }
          });
  ```

**4.1 操作符**

​	**所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列**

* map转化符 : 可以转换发送数据的类型.

```
//变换的演示
Observable.just("图片地址")
        .map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                //调用方法把字符串转化图片
                return ;
            }
        })
        .subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                //显示图片
            }
        });
```

* flatMap :将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里.
* `Zip`通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件. 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
* sample : 个操作符每隔指定的时间就从上游中取出一个事件发送给下游. 这里我们让它每隔2秒取一个事件给下游, 来看看这次的运行结果吧:

**5 .Disposable对象**

​	当调用它的`dispose()`方法时, 它就会将两根管道切断, 从而导致下游收不到事件.

​	注意: 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件

​	那如果有多个`Disposable` 该怎么办呢, RxJava中已经内置了一个容器`CompositeDisposable`, 每当我们得到一个`Disposable`时就调用`CompositeDisposable.add()`将它添加到容器中, 在退出的时候, 调用`CompositeDisposable.clear()` 即可切断所有的水管.