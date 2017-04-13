package com.znvoid.newsapp.bean;

/**
 * 实时天气
 *
 * Created by zn on 2017/4/13.
 */

public class NowWeather {
    private Detail aqiDetail;
    private  String wind_direction;//风向
    private  String wind_power;//风力
    private  String weather;//天气
    private  String weather_pic;//天气图片
    private String temperature;//温度
    private  String temperature_time;
    private  String sd;//湿度
    public NowWeather() {
    }

    public Detail getAqiDetail() {
        return aqiDetail;
    }

    public void setAqiDetail(Detail aqiDetail) {
        this.aqiDetail = aqiDetail;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_power() {
        return wind_power;
    }

    public void setWind_power(String wind_power) {
        this.wind_power = wind_power;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather_pic() {
        return weather_pic;
    }

    public void setWeather_pic(String weather_pic) {
        this.weather_pic = weather_pic;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature_time() {
        return temperature_time;
    }

    public void setTemperature_time(String temperature_time) {
        this.temperature_time = temperature_time;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }
}
