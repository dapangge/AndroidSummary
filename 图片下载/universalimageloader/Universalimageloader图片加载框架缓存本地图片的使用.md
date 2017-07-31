#### Universalimageloader图片加载框架缓存本地图片的使用

 **首先需要添加依赖:**

​    ...............

在项目中创建一个自己的Application,编写以下代码:

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    
        //配置网络加载图片的信息
        File cacheDir = new File(getExternalCacheDir() + "/image");  //设置缓存位置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCacheExtraOptions(480, 800, null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))  //设置缓存大小
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
下面在附上一个加载工具类,可直接使用:

public class ImageUtil {

    private final ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;
    private int mDefaultPicId = R.drawable.icon_default;//记录每次设置的默认展示图片
    private static volatile ImageUtil sInstance;


    //UIL Volley(不推荐) Picasso  Glide(虽然是基于Picasso,有一些特点:可以加载动图gif,占用空间小)
    //Fresco (facebook 包非常大,加载图片比较专业 ,图片消耗的资源比较大,容易OOM )
    
    private ImageUtil(){
        mImageLoader = ImageLoader.getInstance();
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_default)// 加载的时候显示的图片
                .showImageOnFail(R.drawable.icon_default)   //失败的时候加载
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(100)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .build();
    }


    public static ImageUtil getSingleton() {
        if (sInstance == null) {
            synchronized (ImageUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtil();
                }
            }
        }
        return sInstance;
    }
    
    public void display(String picUrl, ImageView imageView){
        display(picUrl,imageView,R.drawable.icon_default);
    }



    public void display(String picUrl, ImageView imageView,int defaultPicId){
        if(defaultPicId!=mDefaultPicId){
            mDefaultPicId = defaultPicId;//记录
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(defaultPicId) // resource or drawable
                    .resetViewBeforeLoading(false)  // default
                    .delayBeforeLoading(100)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                    .build();
        }
        mImageLoader.displayImage(picUrl,imageView,mOptions);
    }
}



详细注解:

ImageLoader loader = ImageLoader.getInstance();

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_image_backage) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.default_image_backage) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.default_image_backage) // 设置图片加载或解码过程中发生错误显示的图片
            .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
            .delayBeforeLoading(1000)  // 下载前的延迟时间
            .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
            .considerExifParams(false) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
            .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
            .build();
    ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(options).build();
    loader.init(configuration);