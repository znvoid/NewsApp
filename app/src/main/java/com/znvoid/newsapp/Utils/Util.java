package com.znvoid.newsapp.Utils;

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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
       return sdf.format(date);
    }



}
