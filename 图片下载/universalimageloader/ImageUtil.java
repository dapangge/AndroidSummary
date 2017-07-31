package com.a520it.newsmy.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.a520it.newsmy.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;



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
