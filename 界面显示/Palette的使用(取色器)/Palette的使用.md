#### Palette的使用

Material Design 建议使用大色块来进行显示需要加重的内容，我们常常需要UI人员进行配合才能实现相应的功能，但是在Material Design中，google提供了一个工具类给我们，让我们可以根据图片获取相应的对应的颜色，能够动态的显示在UI上面。



使用时需要需要导入palette-v7-25.3.1.aar包

 布局:

```
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:id="@+id/top"

    />
<android.support.v4.view.ViewPager
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/viewpage"
    />
```

代码:

```
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    int [] photos;
    private ViewPager viewpage;
    ArrayList<ImageView> mImageViews;
    MyAdapter mMyAdapter;
    RelativeLayout top ;


    HandlerThread handlerThread;

    //子线程的handler
    Handler sub_handler;

    Handler mHandler;

    public static final int GET_COLOR = 0X001;
    public static final int CHANGE_COLOR = 0X002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        //开启一个带Handler的线程,用来执行取色
        handlerThread = new HandlerThread("worker");
        handlerThread.start();

        //这个handler是运行在子线程
        sub_handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {

                //当前的脚标
                int index = msg.arg1;
                //子线程
                getPhotoColor(index);
            }
        };


        //主线程的handler,修改Ui界面
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                int color = msg.arg1;
                changeColor(color);  //修改顶部的颜色
            }
        };

        initDate();
    }

    private void initView() {
        viewpage = (ViewPager) findViewById(R.id.viewpage);
        top = (RelativeLayout) findViewById(R.id.top);
        viewpage.addOnPageChangeListener(this);
    }

    //初始化数据
    private void initDate() {
        photos = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
        mImageViews = new ArrayList<>();
        for(int res:photos){
            ImageView imageView = new ImageView(this);
            mImageViews.add(imageView);
        }
        mMyAdapter = new MyAdapter(mImageViews,photos);
        viewpage.setAdapter(mMyAdapter);
        //默认给子线程发送一条消息
        cal_Index(0);
    }


	//发送取色的操作
    public void cal_Index(int position){
        Message msg = sub_handler.obtainMessage(GET_COLOR,position,0);
        sub_handler.sendMessage(msg);
    }


    public void getPhotoColor(int index){
        //取出一张图片
        Bitmap bitmap =  BitmapFactory.decodeResource(getResources(),photos[index]);
        //进行取色操作
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener(){
            @Override
            public void onGenerated(Palette palette) {
                //取得具体颜色
                int color = Color.BLACK;
                if(palette.getDarkMutedSwatch()!=null){
                    color  =  palette.getDarkMutedSwatch().getRgb();
                }
                else if(palette.getLightVibrantSwatch()!=null){
                    color  =  palette.getLightVibrantSwatch().getRgb();
                }
                //往主线程发送
                Message msg =  mHandler.obtainMessage(CHANGE_COLOR,color,0);
                mHandler.sendMessage(msg);
            }
        });
    }

    //主线程修改颜色
    public void changeColor(int color){
        //进行版本控制
        if(Build.VERSION.SDK_INT>=21){
            Window window = getWindow();
            window.setStatusBarColor(color);    //修改状态栏
            window.setNavigationBarColor(color);//修改Bar
           }
          top.setBackgroundColor(color);  //修改布局的颜色
        }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //handlerThread一定要退出,否者会一直循环
        handlerThread.quit();
    }

 //ViewPager的监听器
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        cal_Index(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
```