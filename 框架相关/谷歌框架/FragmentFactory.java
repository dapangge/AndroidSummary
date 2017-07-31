package com.a520it.mygoogleplay.factory;

import android.support.v4.app.Fragment;

import com.a520it.mygoogleplay.base.BaseFragment;
import com.a520it.mygoogleplay.fragment.AppFragment;
import com.a520it.mygoogleplay.fragment.Categotyragment;
import com.a520it.mygoogleplay.fragment.GAMEFragment;
import com.a520it.mygoogleplay.fragment.HomeFragment;
import com.a520it.mygoogleplay.fragment.HotFragment;
import com.a520it.mygoogleplay.fragment.RecommondFragment;
import com.a520it.mygoogleplay.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xmg on 2017/6/9.
 */

public class FragmentFactory {
    private static final int FRAGMENT_HOME = 0;//首页
    private static final int FRAGMENT_APP = 1;//应用
    private static final int FRAGMENT_GAME = 2;//游戏
    private static final int FRAGMENT_SUBJECT = 3;//专题
    private static final int FRAGMENT_RECOMMOND = 4;//推荐
    private static final int FRAGMENT_CATEGORY = 5;//分类
    private static final int FRAGMENT_HOT = 6;//排行

    //创建集合储存
    private static Map<Integer, BaseFragment> map = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;

        //判断是否储存值
        if (map.containsKey(position)) {
            fragment = map.get(position);
            return fragment;
        }

        switch (position) {
            case FRAGMENT_HOME:
                fragment = new HomeFragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_GAME:
                fragment = new GAMEFragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_SUBJECT:
                fragment = new SubjectFragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_RECOMMOND:
                fragment = new RecommondFragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_CATEGORY:
                fragment = new Categotyragment();
                map.put(position, fragment);
                break;
            case FRAGMENT_HOT:
                fragment = new HotFragment();
                map.put(position, fragment);
                break;
        }
        return map.get(position);
    }
}
