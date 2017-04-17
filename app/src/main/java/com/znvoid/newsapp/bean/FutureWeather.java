package com.znvoid.newsapp.bean;

/**
 * 未来天气
 * Created by zn on 2017/4/13.
 */

public class FutureWeather {
    private String day_weather;
    private String day_weather_pic;
    private String night_air_temperature;
    private String day_air_temperature;
    private  String day;
    private  WeatherIndexInfo index;
    public FutureWeather() {
    }

    public String getDay_weather() {
        return day_weather;
    }

    public void setDay_weather(String day_weather) {
        this.day_weather = day_weather;
    }

    public String getDay_weather_pic() {
        return day_weather_pic;
    }

    public void setDay_weather_pic(String day_weather_pic) {
        this.day_weather_pic = day_weather_pic;
    }

    public String getNight_air_temperature() {
        return night_air_temperature;
    }

    public void setNight_air_temperature(String night_air_temperature) {
        this.night_air_temperature = night_air_temperature;
    }

    public String getDay_air_temperature() {
        return day_air_temperature;
    }

    public void setDay_air_temperature(String day_air_temperature) {
        this.day_air_temperature = day_air_temperature;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public WeatherIndexInfo getIndex() {
        return index;
    }

    public void setIndex(WeatherIndexInfo index) {
        this.index = index;
    }
}
