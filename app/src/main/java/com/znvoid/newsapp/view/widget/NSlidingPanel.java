package com.znvoid.newsapp.view.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by zn on 2017/4/11.
 */


/**
 * 解决SlidingPaneLayout 滑动冲突
 * 遍历子控件判断子控件是否可以滑动来拦截事件
 *
 */
public class NSlidingPanel extends SlidingPaneLayout {

    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mEdgeSlop;

    public NSlidingPanel(Context context) {
        this(context, null);
    }

    public NSlidingPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NSlidingPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ViewConfiguration config = ViewConfiguration.get(context);
        mEdgeSlop = config.getScaledEdgeSlop();
        //滑动的距离满足系统定义的从溢出屏幕处开始水平滑动的一段距离，在小于这个距离滑动会打开panel,这是为了在子控件滑出很远时也能打开panel
        //DrawerLayout的侧拉菜单的拉出方式
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                // 条件1.触摸位置大于mEdgeSlop  2,panel 没打开 ，3 子控件是否可以滑动

                if (mInitialMotionX > mEdgeSlop && !isOpen() && canScroll(this, false,
                        Math.round(x - mInitialMotionX), Math.round(x), Math.round(y))) {

                    // How do we set super.mIsUnableToDrag = true?

                    // send the parent a cancel event
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(cancelEvent);
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}

