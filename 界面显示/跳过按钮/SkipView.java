package com.a520it.newsmy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xmg on 2017/5/31.
 */

public class SkipView extends View {

    public static final int ARC_WIDTH = 5;    //间距
    public static final int TEXT_PADDING = 10;  //间距
    public static final int TEXT_SIZE =22;    //大小
    public static final String TEXT = "跳过";
    //draw绘制
    private int mCurcentTime = 0;//当前时间
    private int mTotalTime = 0;//总的时间

    private Paint mOutArcPaint;
    private Paint mInnerCirclePaint;
    private Paint mTextPaint;
    private float mMeasureTextWidth;
    private float mInnerCircleDoubleRadius;
    private float mOutArcDoubleRadius;
    private RectF mRectF;
    private Handler mHandler;

    public SkipView(Context context) {
        super(context);
    }

    public SkipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //外部圆弧对应画笔
        mOutArcPaint = new Paint();
        mOutArcPaint.setColor(Color.RED);
        mOutArcPaint.setStrokeWidth(ARC_WIDTH);
        mOutArcPaint.setStyle(Paint.Style.STROKE);
        mOutArcPaint.setAntiAlias(true);
        //内部圆对应画笔
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setColor(Color.GRAY);
        //文字对应画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(TEXT_SIZE);

        //计算圆的半径大小
        //先计算出文字的宽度
        mMeasureTextWidth = mTextPaint.measureText(TEXT);

        //内部圆直径=文字的宽度+2PADDING
        mInnerCircleDoubleRadius = mMeasureTextWidth + 2 * TEXT_PADDING;
        //外部圆直径=内部圆直径+2ARC_WIDTH
        mOutArcDoubleRadius = mInnerCircleDoubleRadius + 2 * ARC_WIDTH;

        //准备一个外部圆弧绘制所需要的RectF对象
        mRectF = new RectF(0 + ARC_WIDTH / 2, 0 + ARC_WIDTH / 2,
                mOutArcDoubleRadius - ARC_WIDTH / 2, mOutArcDoubleRadius - ARC_WIDTH / 2);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCurcentTime += 100;
                //判断一下,超过或等于总时间,就不要再发消息了
                if (mCurcentTime >= mTotalTime) {
                    //最后一次重绘,将会画出360度的圆弧
                    mCurcentTime = mTotalTime;
                    invalidate();
                    //关闭页面
                    if(mOnSkipListener!=null){
                        mOnSkipListener.onSkip();
                    }
                    return;
                }
                //重新绘制
                invalidate();
                mHandler.sendEmptyMessageDelayed(0, 100);
            }
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                setAlpha(1f);
                //不要再让Handler一直发消息去重绘了
                mHandler.removeCallbacksAndMessages(null);
                if(mOnSkipListener!=null){
                    mOnSkipListener.onSkip();
                }
                break;
            default:
                break;
        }

        return true;
    }
    //发送消息
    public void start() {
        start(3000);
    }

    //停止发送消息
    public  void  stop(){
        mHandler.removeCallbacksAndMessages(null);
    }

    public void start(int totalTime) {
        mTotalTime = totalTime;
        mHandler.sendEmptyMessageDelayed(0, 100);
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                widthSize = Math.min(widthSize, (int) mOutArcDoubleRadius);
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                heightSize = Math.min(heightSize, (int) mOutArcDoubleRadius);
                break;
        }
        //取最小值
        int min = Math.min(widthSize, heightSize);
        setMeasuredDimension(min, min);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        //绘制3个
        canvas.save();
        canvas.rotate(-90, measuredWidth / 2, measuredHeight / 2);
        float percent = mCurcentTime * 1f / mTotalTime;
        canvas.drawArc(mRectF, 0, percent * 360, false, mOutArcPaint);
        canvas.restore();

        canvas.drawCircle(measuredWidth / 2, measuredHeight / 2, mInnerCircleDoubleRadius / 2, mInnerCirclePaint);
        //// 通过FontMetrics中的属性来修改Y轴位置
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        //公式: baseLine = 你想让控件的中间位于的Y上的坐标位置-(top+Bottom)/2;
        int baseLine = (int) (measuredHeight / 2 - (top + bottom) / 2);
        canvas.drawText(TEXT, measuredWidth / 2 - mMeasureTextWidth / 2, baseLine, mTextPaint);
    }

    //3 写一个成员变量来接收
    private OnSkipListener mOnSkipListener;

    //2写一个set方法,用来传递一个接口的实例过来
    public void setOnSkipListener(OnSkipListener onSkipListener){
        mOnSkipListener = onSkipListener;
    }

    //1 创建一个接口作为监听器
    public interface  OnSkipListener{
        void onSkip();
    }
}
