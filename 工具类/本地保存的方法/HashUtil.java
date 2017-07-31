package com.a520it.newsmy.utils;

/**
 * Created by xmg on 2017/5/31.
 */

//保存图片名字的工具类
public class HashUtil {

    public static String getHashCodeFileName(String picUrl){
        int hashCode = picUrl.hashCode();
        return hashCode+".jpg";
    }
}
