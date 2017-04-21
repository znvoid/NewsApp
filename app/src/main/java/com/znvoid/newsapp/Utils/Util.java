package com.znvoid.newsapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;

import com.znvoid.newsapp.view.widget.TimeEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zn on 2017/4/10.
 */

public class Util {
    public static List<TimeEvent> makedata(){
        List<TimeEvent> timeEvents=new ArrayList<>();
         timeEvents.add(new TimeEvent("2011-4-4 22:21:12", "快件到达 【杭州萧山集散中心】"));
         timeEvents.add(new TimeEvent("2017-04-07 08:09:47", "快件交给邓亚续，正在派送途中（联系电话：13136110930)"));
         timeEvents.add(new TimeEvent("2017-04-07 00:58:59", "快件在【杭州拱墅德胜巷营业点】已装车，准备发往"));
         timeEvents.add(new TimeEvent("2011-4-4 22:21:12", "快件到达 【杭州萧山集散中心】"));
        timeEvents.add(new TimeEvent("2017-04-07 08:09:47", "快件交给邓亚续，正在派送途中（联系电话：13136110930)"));
        timeEvents.add(new TimeEvent("2017-04-07 00:58:59", "快件在【杭州拱墅德胜巷营业点】已装车，准备发往"));


        timeEvents.add(new TimeEvent("2011-4-4 22:21:12", "快件到达 【杭州萧山集散中心】"));
        timeEvents.add(new TimeEvent("2017-04-07 08:09:47", "快件交给邓亚续，正在派送途中（联系电话：13136110930)"));
        timeEvents.add(new TimeEvent("2017-04-07 00:58:59", "快件在【杭州拱墅德胜巷营业点】已装车，准备发往"));
        timeEvents.add(new TimeEvent("2011-4-4 22:21:12", "快件到达 【杭州萧山集散中心】"));
        timeEvents.add(new TimeEvent("2017-04-07 08:09:47", "快件交给邓亚续，正在派送途中（联系电话：13136110930)"));
        timeEvents.add(new TimeEvent("2017-04-07 00:58:59", "快件在【杭州拱墅德胜巷营业点】已装车，准备发往"));
        return timeEvents;
    }
    //返回今天日期
    public static String getCurrentTime(){
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       return sdf.format(date);
    }
    public static <T> List<T> getOutList(List<T> m,List<T> c){
        List<T> result=new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            T temp=m.get(i);
            if (!c.contains(temp))
                result.add(temp);

        }


        return result;
    }

    /**
     * 检查网络类型
     * @param context
     * @return  0 无网络，1 wifi 网络，2 移动网络
     */
    public static int getNetype(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            netType= 2;
        }
        return netType;
    }

    /**
     * 复制view
     * @param view
     * @return
     */
    public static ImageView getDrawingCacheView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(view.getContext());
        iv.setImageBitmap(cache);
        return iv;
    }

    /**
     * 获取View 在当前窗口中的位置坐标
     * @param view 要获取位置的view
     * @return 坐标数组 ，int[0] 为x坐标，int[1] 为y坐标
     */
    public static int[] getViewLocation(View view){
        int[] locaton=new int[2];
        view.getLocationInWindow(locaton);
        return locaton;
    }
}
