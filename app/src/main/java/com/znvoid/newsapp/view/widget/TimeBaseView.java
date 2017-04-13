package com.znvoid.newsapp.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.znvoid.newsapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by zn on 2017/4/11.
 */

public class TimeBaseView extends View {

    private List<TimeEvent> mTimeEvents;//时间事件bean
    private int mWidth=0;//view的宽度
    private int mHeight=0;//view的高度
    private int mTimeLine =0;//时间线位置
    private float mTimeLineFr;//时间线为宽度的百分比，范围0-1；
    private int mPadding=30;
    private int mPadingBase=10;//两个Timebase之间的距离
    private TextPaint mTextPaint;//画笔
    private Vector<String> timeStrings;
    private Vector<String> eventStrings;
    private int ridus=12;
    private Paint mPaint;//绘制分隔线的画笔
    private int totalHight;
    private int mScaledTouchSlop;//最小滑动距离

    public TimeBaseView(Context context) {
        this(context,null);
    }

    public TimeBaseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doInit(context,attrs,defStyleAttr);
    }

    private void doInit(Context context,AttributeSet attrs, int defStyleAttr) {

        mTimeEvents =new ArrayList<>();
        mTextPaint=new TextPaint();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.TimeBaseView);
        float textSize= array.getFloat(R.styleable.TimeBaseView_android_textSize,30f);
        int textColor=array.getColor(R.styleable.TimeBaseView_android_textColor,Color.BLACK);
        mTimeLineFr=array.getFraction(R.styleable.TimeBaseView_timeLine,0,1,0.5f);
        ridus=array.getInt(R.styleable.TimeBaseView_timeLineSize,10);
        array.recycle();



        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        eventStrings=new Vector<>();
        timeStrings=new Vector<>();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(ridus);

        ViewConfiguration configuration=ViewConfiguration.get(context);
        mScaledTouchSlop= configuration.getScaledTouchSlop();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
        mTimeLine = (int) (mWidth*mTimeLineFr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得控件宽高
        if (mWidth==0||mHeight==0){
            mWidth=getMeasuredWidth();
            mHeight=getMeasuredHeight();
            mTimeLine =(int) (mWidth*mTimeLineFr);
        }
        if (mWidth==0||mHeight==0){
            System.out.println("kong-----");
            canvas.drawText("绘制失败",0,0,mPaint);
            return;
        }
        int baseHight= mPadding;//h画每个时间事件的基准起始高度

        for (int i = 0; i < mTimeEvents.size() ; i++) {
            TimeEvent dao= mTimeEvents.get(i);
            int height=claHight(dao);
            drawTime(canvas,baseHight,i);
            drawEvent(canvas,baseHight,i);
            drawSpliteLine(canvas,baseHight,height,i);
            baseHight+=(height+mPadingBase);
        }
        totalHight=baseHight;



    }

    private void drawSpliteLine(Canvas canvas,int baseHight, int height, int index) {
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(mTimeLine,baseHight, mTimeLine, mTimeLine +height+mPadingBase,mPaint);
        canvas.drawCircle(mTimeLine,baseHight+ridus*1.5f,ridus*2,mPaint);

    }

    private void drawEvent(Canvas canvas, int baseHight, int index) {
        float lineHeight=getTextLineHight();
        for (int i = 0; i < eventStrings.size(); i++) {
            String s=eventStrings.get(i);
            canvas.drawText(s, mTimeLine+ridus*2.5f,baseHight+lineHeight/2+lineHeight*i,mTextPaint);
        }

    }

    private void drawTime(Canvas canvas, int baseHight, int index) {
        float lineHeight=getTextLineHight();
        for (int i = 0; i < timeStrings.size(); i++) {
            String s=timeStrings.get(i);
            float w= mTextPaint.measureText(s);
            canvas.drawText(s, (mTimeLine -w-ridus*2.5f)/2,baseHight+lineHeight/2+lineHeight*i,mTextPaint);
        }
    }

    /**
     * 根据TimeEvent计算需要的高度
     * @param timeEvent
     */
    private int claHight(TimeEvent timeEvent) {
        int line1=0,line2=0;
        int index=0;
        CharSequence temp=timeEvent.getTime();
        timeStrings.clear();
        do {
           int n= mTextPaint.breakText(temp,index,temp.length(),false, mTimeLine -mPadding-ridus*2,null);
            timeStrings.add((String) temp.subSequence(index,index+n));
            index+=n;
            line1++;
        }while (index<temp.length());
        temp=timeEvent.getEvent();
        eventStrings.clear();
        index=0;
        int measureWidth=mWidth- mTimeLine -mPadding-ridus*2;
        do {
            int n= mTextPaint.breakText(temp,index,temp.length(),false,measureWidth,null);
            eventStrings.add((String) temp.subSequence(index,index+n));
            index+=n;
            line2++;
        }while (index<temp.length());

        return (int)Math.ceil ((line1>line2?line1:line2)*getTextLineHight());
    }

    /**
     * 获取文字行高
     */
    private float getTextLineHight(){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        return fontMetrics.bottom-fontMetrics.top;
    }

    public void addTimeEvent(List<TimeEvent> timeBases){
        mTimeEvents.addAll(timeBases);
        postInvalidate();
    }
    public void addTimeEvent(TimeEvent timeBase){
        mTimeEvents.add(timeBase);
    }
    public void setTimeEvent(List<TimeEvent> timeEvents){
        if (timeEvents==null)
            throw new RuntimeException("TimeEvent 不能为空");
        mTimeEvents=timeEvents;
        postInvalidate();
    }
    private float y0,x0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                y0=event.getY();
                x0=event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
              float  dy=event.getY()-y0;
              if(Math.abs(dy)>mScaledTouchSlop&&canScroll((int) dy)){
                  scrollBy(0, (int) -dy);
                  y0=event.getY();

              }


            break;

        }



        return true;
    }

    private boolean canScroll(int dy){

        return getScrollY()-dy>=0&&totalHight>mHeight&&getScrollY()-dy<totalHight-mHeight+mPadingBase;

    }

}
