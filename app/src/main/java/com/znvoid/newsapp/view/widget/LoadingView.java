package com.znvoid.newsapp.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zn on 2017/4/10.
 */

public class LoadingView extends View{

    private static final int LOADING=1;
    private int precent;//动画进度0-100；
    private int stat=1;

    private int mWidth;
    private int mHeight;

    private ValueAnimator animator;

    private Paint mPaint;
    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
          mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        startAmin();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
         mWidth=getMeasuredWidth();
         mHeight=getMeasuredHeight();
        switch (stat){
            case LOADING:

                drawRuningCircle(canvas);

                break;



        }




    }
    public void startAmin(){
        if (animator!=null&&animator.isRunning())
            animator.cancel();
        animator = ValueAnimator.ofInt(0,100);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                precent= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }

    private void drawRuningCircle(Canvas canvas) {
        int minLeng=mWidth>mHeight?mHeight:mWidth;
        int radius=minLeng/9;
        for (int i = 0; i <6 ; i++) {
            float cx=mWidth/2+(float) (radius*Math.sin(2*Math.PI*precent/100-2*Math.PI*i/6));
            float cy= mHeight/2-(float) (radius*Math.cos(2*Math.PI*precent/100-2*Math.PI*i/6));
            canvas.drawCircle(cx,cy,1+5*i,mPaint);

        }


    }

    public void stopAnimator(){
        animator.cancel();
        animator=null;
    }
}
