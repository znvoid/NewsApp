package com.znvoid.newsapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.znvoid.newsapp.MainActivity;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.Channel;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.MyImageDownloader;
import com.znvoid.newsapp.model.NewsLoadLisenter;
import com.znvoid.newsapp.view.activity.NewsReadActivity;
import com.znvoid.newsapp.view.activity.SettingActivity;
import com.znvoid.newsapp.view.activity.WeatherActivity;
import com.znvoid.newsapp.view.activity.ZXingActivity;
import com.znvoid.newsapp.view.fragment.NewsFragment;
import com.znvoid.newsapp.view.fragment.PictureFragemnt;
import com.znvoid.newsapp.view.widget.LoadingView;
import com.znvoid.newsapp.view.widget.ShowImagePopup;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zn on 2017/4/9.
 */

public class Presenter implements NewsLoadLisenter<Channel>,LogicLink{
    private static Presenter presenter=new Presenter();
    private WeakReference<MainActivity> activityWeakReference;
    private ShowImagePopup showImagePopup;
    private SharedPreferences sp;
    private BottomSheetBehavior<NestedScrollView> bottomSheetBehavior;
    private Handler mHandler=new Handler(Looper.getMainLooper());
    private Presenter() {
    }
    public static Presenter getInstance(){
        return presenter;
    }
    public void init(Activity activity){
        activityWeakReference =new WeakReference<MainActivity>((MainActivity) activity);
        ImageLoaderConfiguration configuration =new  ImageLoaderConfiguration.Builder(activity).imageDownloader(new MyImageDownloader(activity)).build();
        ImageLoader.getInstance().init(configuration);
        sp = activity.getSharedPreferences("configs", Activity.MODE_PRIVATE);
        long time=sp.getLong("Date",System.currentTimeMillis());
        Date date=new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat("D");
        String d1=sdf.format(date);
        date.setTime(System.currentTimeMillis());
        String d2=sdf.format(date);
        System.out.println(d1+"--"+d2);
        if (!d1.equals(d2)) {
            sp.edit().putLong("Date", System.currentTimeMillis()).putInt("page",1).commit();
        }else {
            ApiWorkManager.getInstance().setPage(sp.getInt("page", 1));

        }
        initView();
    }
    private void initView(){
        MainActivity activity=activityWeakReference.get();
        NestedScrollView nestedScrollView= (NestedScrollView) activity.findViewById(R.id.share_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollView);

    }
//添加新闻阅读界面
    public void StartFragemt(){
        LoadingView view= (LoadingView) activityWeakReference.get().findViewById(R.id.content_loadingview);
        String userChannelsString= getDataFromPerference("userNewsChannels","");
        if (userChannelsString!=null&&!userChannelsString.equals("")){
            final List<Channel> channels = JSON.parseArray(userChannelsString, Channel.class);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onComplete(channels);
                }
            },2000);

        }else {

            String channelsString= getDataFromPerference("newsChannels","");
            if (channelsString!=null&&!channelsString.equals("")){
                final List<Channel> channels = JSON.parseArray(channelsString, Channel.class);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onComplete(channels);
                    }
                },2000);


            }else {
                int netype = Util.getNetype(activityWeakReference.get());
                switch (netype){
                    case 0:
                        Snackbar.make(activityWeakReference.get().getWindow().getDecorView(),"网络异常，去设置",Snackbar.LENGTH_LONG)
                                .setAction("设置", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        toSetting();
                                    }
                                }).show();
                        break;
                    case 1:
                    case 2:
                        if (checkNetStat()) {
                            ApiWorkManager.getInstance().getNewsChannel(this);
                        }else {
                            showToast("未设置移动网络可用，初始化频道失败！");
                        }
                        break;
                }

            }

        }



        view.startAmin();
        view.setVisibility(View.VISIBLE);
        activityWeakReference.get().getSupportActionBar().setTitle("新闻阅读");
    }

    //加载图片浏览界面
    public void loadPicFragment(){
//
        PictureFragemnt pictureFragemnt=new PictureFragemnt();
        activityWeakReference.get().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment,pictureFragemnt).commit();
        activityWeakReference.get().getSupportActionBar().setTitle("欣赏图片");
    }

    @Override
    public void onComplete(List<Channel> items) {
        if (items.size()>0){
            ArrayList<String>  channelIds=new ArrayList<>();
            ArrayList<String>  channelNames=new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                channelIds.add(items.get(i).getChannelId());
                channelNames.add(items.get(i).getName());
            }
            NewsFragment newsFragment=new NewsFragment();
            Bundle bundle=new Bundle();
            bundle.putStringArrayList("channelIds",  channelIds);
            bundle.putStringArrayList("channelNames",  channelNames);
            newsFragment.setArguments(bundle);

            activityWeakReference.get().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_fragment,newsFragment).commit();

           LoadingView view= (LoadingView) activityWeakReference.get().findViewById(R.id.content_loadingview);
            view.stopAnimator();
            view.setVisibility(View.GONE);
        }


    }

    @Override
    public void onError() {

        Toast.makeText( activityWeakReference.get(),"获取频道失败",Toast.LENGTH_SHORT).show();

    }

    private void toSetting(){
        Intent intent =new Intent();
        intent.setAction(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        activityWeakReference.get().startActivity(intent);

    }

    public void  showImage(String url){
        if (showImagePopup==null)
            showImagePopup=new ShowImagePopup(activityWeakReference.get());
        showImagePopup.Show(url);

    }

    public void startNewsReadActivity(String url){
        Intent intent =new Intent(activityWeakReference.get(), NewsReadActivity.class);
        intent.putExtra("url",url);
        activityWeakReference.get().startActivity(intent);
    }


    public void startZxing() {
        Intent intent=new Intent(activityWeakReference.get(), ZXingActivity.class);
        intent.putExtra("zxing",true);
        activityWeakReference.get().startActivity(intent);

    }

    @Override
    public void pageMark(int page) {
       SharedPreferences.Editor editor= sp.edit();
        editor.putInt("page",page);
        editor.commit();
    }

    @Override
    public void saveDataToPreference(String key, String values) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activityWeakReference.get());

        defaultSharedPreferences.edit().putString(key,values).commit();

    }

    @Override
    public String getDataFromPerference(String key,String defaul) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activityWeakReference.get());
        return defaultSharedPreferences.getString(key,defaul);
    }

    @Override
    public void onClick(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        showToast("暂不支持此功能");

    }

    @Override
    public void showBottomSheet() {
        if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

    }

    public void jumpToSettingActivity(){
        Intent intent=new Intent(activityWeakReference.get(), SettingActivity.class);
        activityWeakReference.get().startActivity(intent);

    }
    public void jumpToWeatherActivity(){
        Intent intent=new Intent(activityWeakReference.get(), WeatherActivity.class);
        activityWeakReference.get().startActivity(intent);
    }

    public void showToast(String msg){

        Toast.makeText(activityWeakReference.get(),msg,Toast.LENGTH_SHORT).show();


    }

    public boolean checkNetStat(){
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activityWeakReference.get());
       boolean flag= defaultSharedPreferences.getBoolean("net_onMoblie",false);
        int type=Util.getNetype(activityWeakReference.get());
        return flag?type>=1:type==1;
    }
    public int  getBottomSheetSata(){
        if (bottomSheetBehavior!=null)
            return bottomSheetBehavior.getState();
        return -1;
    }

}
