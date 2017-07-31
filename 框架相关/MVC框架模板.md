### MVC框架模板

Activity:

```
public class BaseActivity extends AppCompatActivity implements IModelChangeListener {
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //具体操作由子类实现
            handleUI(msg);
            super.handleMessage(msg);
        }
    };

    //子类实现修改UI的方法
    protected  void  handleUI(Message msg){
        //空实现
    };
    
    //获取到传回的数据
    public void onModelChanged(int action, Object bean) {
        //发回主线程修改ui
        mHandler.obtainMessage(action,bean).sendToTarget();
    }

    //返回
    public  void goBack(View view){
        finish();
    }
}
```

Controller:

```
//所有网络请求的基类
public abstract class BaseController {

    //创建一个弱引用接受
    protected  WeakReference<Context> mWeakReference ;
    //创建一个接口对象
    IModelChangeListener mListener;

    //创建一个构造器传递
    public BaseController(Context context) {

        //把传递的对象转化为一个弱引用,防止内存泄露
        mWeakReference = new  WeakReference<Context>(context);
    }

    //定义方法在子线程中执行网络请求,传递一个标示和可变的参数
    public   void  sendAsyncMessage(final int action , final Object...values){
        new Thread(){
            @Override
            public void run() {
                //具体的实现方法由子类实现
                handleMessage(action,values);
            }
        }.start();
    }

    //创建抽象方法
    public abstract void handleMessage(int action,Object...values);
    //创建方法返回数据
   public void setIModelChangeListener(IModelChangeListener listener){
      this.mListener = listener;

   }
}
```

回调数据接口:

```
public interface IModelChangeListener {

     void  onModelChanged(int action,Object bean);
}
```