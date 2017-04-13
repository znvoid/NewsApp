package com.znvoid.newsapp.bean;

/**
 * 天气详细信息
 * Created by zn on 2017/4/13.
 */
public class Detail {

    private String area;
    private String quality;
    private  String pm2_5;
    private  String aqi;

    public Detail() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}
