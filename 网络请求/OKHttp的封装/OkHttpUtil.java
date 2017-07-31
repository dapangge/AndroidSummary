package com.a520it.newsmy.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ASUS on 2017/5/31.
 */

public class OkHttpUtil {

    private   static OkHttpUtil mOkHttpUtil;
    private final OkHttpClient mOkHttpClient;

    private OkHttpUtil(){
        mOkHttpClient = new OkHttpClient();
    }

    public static  OkHttpUtil  getInstent(){

        if (mOkHttpUtil == null){
            synchronized (OkHttpUtil.class){

                if (mOkHttpUtil == null){
                    return  mOkHttpUtil = new OkHttpUtil();
                }
            }
        }
          return mOkHttpUtil;
    }



    public void doGet(final int action, String url, final Listerner listerner){


        final Request request = new Request.Builder().url(url).build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listerner.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    onFailure(call,new IOException("请求为成功"));
                }else {
                    String s = response.body().string();
                    listerner.onSuccess(action,s);
                }
            }
        });
    }



    public interface Listerner{
        void onError(IOException e);
        void onSuccess(int Action,String s);
    }
}
