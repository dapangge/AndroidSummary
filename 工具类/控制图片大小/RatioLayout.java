package com.a520it.mygoogleplay.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.a520it.mygoogleplay.R;


/**
 * Created by xmg on 2017/6/14.
 * RatioLayout 是一个容器 必须测量孩子 ,还行将自己的宽高设置回去
 */

public class RatioLayout extends FrameLayout {
    //宽度和高度的比值
    public float relato=2.43f;

    public static final  int  RELATIVE_WIDTH=0;
    public  static  final  int RELATIVE_HEIGHT=1;
    public  int currnet_relative=RELATIVE_WIDTH;
    public RatioLayout(@NonNull Context context) {
        super(context);
    }

    //设置一个可以在代码中new的方法使用


    public void setRelato(float relato) {
        this.relato = relato;
    }

    public void setCurrnet_relative(int relative) {
        if (relative>1||relative<0){
            return;
        }
        this.currnet_relative=relative;

    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        //获取自定义属性的值
        relato=typedArray.getFloat(R.styleable.RatioLayout_relato,0.0f);
        currnet_relative=typedArray.getInt(R.styleable.RatioLayout_relative,RELATIVE_WIDTH);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量的前提必须白: 必须宽度或者高度有一个是确定模式,只有这样,才能测量出想要的高度或者宽度
        //MeasureSpec 是Android非常重要的一个类,Android里面的所有控件的测量的工具方法都封装在这里面
        //当前的高度值,和高度的模式
        //在本项目中,宽度是一个精确模式,高度是一个非精确模式,所有要通过宽度,求得高度
        //当前的宽度值,和宽度的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //宽高模式分为 三种 精确模式 不精确模式 之多模式(用的不多)
        //如果宽度是一个精确的模式,要求得高度 有一个宽度/搞得比值
        if (currnet_relative==RELATIVE_WIDTH&&widthMode==MeasureSpec.EXACTLY&&relato!=0){
            //代表宽度是精确模式,保证宽高比必须有一个比值
           int widthChild= widthSize-getPaddingLeft()-getPaddingRight();
            //根据宽度求高度
           int heightChild= (int) (widthChild/relato+0.5f);
            int chiledWidthMeasuresPec=MeasureSpec.makeMeasureSpec(widthChild,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(heightChild,MeasureSpec.EXACTLY);
            measureChildren(chiledWidthMeasuresPec,childHeightMeasureSpec);
            //设置会父容器的框高
            int wh=widthSize;
            int ht=heightChild+getPaddingBottom()+getPaddingTop();
            setMeasuredDimension(wh,ht);

        }else if(currnet_relative==RELATIVE_HEIGHT&&heightMode==MeasureSpec.EXACTLY&&relato!=0){
            //高度确定
            //获得子控件的高度
            int heightChild=heightSize-getPaddingTop()-getPaddingBottom();
            int widthChild= (int) (heightChild*relato+0.5f);
            //获得子控件的宽度
            //测量子控件的宽高
            int chiledWidthMeasuresPec=MeasureSpec.makeMeasureSpec(widthChild,MeasureSpec.EXACTLY);
            int childHeightMeasureSpec=MeasureSpec.makeMeasureSpec(heightChild,MeasureSpec.EXACTLY);
            measureChildren(chiledWidthMeasuresPec,childHeightMeasureSpec);
            //设置会自己的宽度高度
            int ht=heightSize;
            int wd=widthChild+getPaddingRight()+getPaddingLeft();
            setMeasuredDimension(wd,ht);

        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
