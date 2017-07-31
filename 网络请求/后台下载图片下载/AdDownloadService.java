package com.a520it.newsmy.seriver;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.a520it.newsmy.bean.AdItemBean;
import com.a520it.newsmy.bean.AdsBean;
import com.a520it.newsmy.utils.HashUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AdDownloadService extends IntentService {
    public static final String DOWNLOAD_PIC = "download_pic";
    public AdDownloadService() {
        super("AdDownloadService");
    }

   //默认在后台运行,完成后自杀
    @Override
    protected void onHandleIntent(Intent intent) {
        //获取到传递的对象
        AdsBean  bean = (AdsBean) intent.getSerializableExtra(DOWNLOAD_PIC);
        //获取到图片的地址
        ArrayList<AdItemBean> ads = bean.getAds();
        OkHttpClient okHttpClient = new OkHttpClient();
        for (int i = 0; i < ads.size(); i++){
            //下载图片
            String[] url = ads.get(i).getRes_url();
            final String urlpath = url[0];
            //判断本地是否有图片
             File file = new File(getExternalCacheDir(), HashUtil.getHashCodeFileName(urlpath));
            if (file.exists() && file.length()>0){
                continue;
            }
            //下载到本地
            Request request = new Request.Builder().url(urlpath).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()){
                        onFailure(call,new IOException("下载失败"));
                    }
                    //获取到图片的输入流
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    //创建图片保存的位置
                    File  files = new File(getExternalCacheDir(),HashUtil.getHashCodeFileName(urlpath));
                    FileOutputStream outputStream = new FileOutputStream(files);
                    //保存图片到本地
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

                }
            });


        }

    }

}
