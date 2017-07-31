package com.a520it.mygoogleplay.base;


import android.os.SystemClock;

import com.a520it.mygoogleplay.conf.Constants;
import com.a520it.mygoogleplay.utils.FileUtils;
import com.a520it.mygoogleplay.utils.HttpUtils;
import com.a520it.mygoogleplay.utils.IOUtils;
import com.a520it.mygoogleplay.utils.UIUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;



//访问网络的基类
public abstract class BaseProtocol<T> {

    public T  loadData(int index) throws Exception {
        //从缓存中读取数据
        T t = getDataFromMem(index);
        if (t != null){
            return t;
        }
        //从本地文件获取
         t = getDataFromSD(index);
        if (t != null){
            return t;
        }
        //从网络获取数据
         t = getDataFromNet(index);
        if (t != null){
            return t;
        }

        return null;
    }

    //从网络获取数据
    private T getDataFromNet(int index) throws Exception {

        OkHttpClient okHttpClient = new OkHttpClient();
        //创建地址
        String url = Constants.URL.BASE_URL + getInterfaceParse() + "?";
        Map map = getParamsHashMap(index);
        String urlParamsByMap = HttpUtils.getUrlParamsByMap(map);
        url = url+ urlParamsByMap;

        //创建请求
        Request request = new Request.Builder().get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        //判断链接成功
        if (response.isSuccessful()){
            //获取到json
            String jsonStr = response.body().string();
            //保存到内存中
            MyApplication context = (MyApplication) UIUtils.getContext();
            Map<String, String> memoryData = context.getMemoryData();
            String key = paramsMapKey(index);
            memoryData.put(key,jsonStr);
            //保存到本地
            BufferedWriter bw = null;
            try {
                File file = getFile(index);
                 bw  = new BufferedWriter(new FileWriter(file));
                //获取到时间
                long tiemr = SystemClock.currentThreadTimeMillis();
                bw.write(tiemr+"");
                bw.newLine();
                bw.write(jsonStr);
            }finally {
                IOUtils.close(bw);
            }
            //解析
            T t = paramsJsonBean(jsonStr);
            return t;
        }
            return null;

    }

    //从本地获取缓存数据
    private  T getDataFromSD(int index){
        BufferedReader br = null;
        try {
            //获取一个文件
            File file = getFile(index);
            //创建读取文件
            br = new BufferedReader(new FileReader(file));
            //旧的时间
            String oldTimer = br.readLine();
            //获取现在的时间
            long newTimer = SystemClock.currentThreadTimeMillis();
            //比对时间是否超时
            if (newTimer - Long.parseLong(oldTimer) <= Constants.SAFETIMER){
                String jsonStr = br.readLine();
                //解析
                T t = paramsJsonBean(jsonStr);
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            IOUtils.close(br);
        }
        return null;
    }

    //从内存获取缓存
    public T getDataFromMem( int index){
       MyApplication context = (MyApplication) UIUtils.getContext();
       //获取集合
       Map<String, String> memoryData = context.getMemoryData();
       //获取到Key
       String key = paramsMapKey(index);
       //判断是否保存值
       if (memoryData.containsKey(key)){
           //获取到保存的Json
           String jsonStr = memoryData.get(key);
           //解析
           T t = paramsJsonBean(jsonStr);
           return t;
       }
        return null;
   }


    //放回一个唯一的key
    private String paramsMapKey(int index) {
        Map<String,Object> hashMap = getParamsHashMap(index);
        for (Map.Entry<String,Object> enter: hashMap.entrySet()){
            Object value = enter.getValue();// 0.20.com.m520it.www
            String key = enter.getKey();//index,packageNmae
            return getInterfaceParse()+"."+value;//home.0,packageName=com.m520it.www
        }
        return null;
    }



    //获取到文件的路径
    private File getFile(int index) {
        String path = FileUtils.getDir("json");
        String key = paramsMapKey(index);
        return new File(path,key);
    }

    public Map getParamsHashMap(int index) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("index", String.valueOf(index));
        return paramsMap;
    }
    //获取到具体的访问界面的路径
    public abstract String getInterfaceParse();
    //解析bean
    protected abstract T paramsJsonBean(String jsonStr);
}
