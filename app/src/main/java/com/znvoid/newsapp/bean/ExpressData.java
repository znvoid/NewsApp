package com.znvoid.newsapp.bean;

/**
 * 快递信息
 * Created by zn on 2017/4/11.
 */

public class ExpressData {
    private String time;
    private  String location;
    private String context;
    private String ftime;

    public ExpressData() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }
}
