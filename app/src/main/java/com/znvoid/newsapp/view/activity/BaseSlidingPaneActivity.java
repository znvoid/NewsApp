package com.znvoid.newsapp.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.znvoid.newsapp.view.widget.NSlidingPanel;

import java.lang.reflect.Field;

/**
 * Created by zn on 2017/4/8.
 * 可以测滑的activity
 * 条件：继承类同时使用主题DIY.SlideClose.Transparent.Theme
 */

public class BaseSlidingPaneActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener{
    private SlidingPaneLayout mSlidingPaneLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSlidingPaneLayout=new NSlidingPanel(this);
//        mSlidingPaneLayout=new SlidingPaneLayout(this);
        try {
            Field field=SlidingPaneLayout.class.getField("mOverhangSize");
            field.setAccessible(true);
            field.set(mSlidingPaneLayout,0);//使pane宽度为屏幕宽度
        } catch (Exception e) {
            e.printStackTrace();
        }
        mSlidingPaneLayout.setPanelSlideListener(this);
        mSlidingPaneLayout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        //左边pane
        View lefeView=new View(this);
        lefeView.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lefeView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mSlidingPaneLayout.addView(lefeView,0);


      ViewGroup decorView= (ViewGroup) getWindow().getDecorView();

      ViewGroup child= (ViewGroup) decorView.getChildAt(0);
        child.setBackgroundColor(getResources()
                .getColor(android.R.color.white));
        decorView.removeView(child);
        decorView.addView(mSlidingPaneLayout,0);

        mSlidingPaneLayout.addView(child,1);



    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(View panel) {
        finish();//当panel打开即滑到最右边时关闭activity
    }

    @Override
    public void onPanelClosed(View panel) {

    }



}
