package com.znvoid.newsapp.bean;

/**
 * Created by zn on 2017/4/13.
 */

public class WeatherRespondBody {
    private FutureWeather f1;
    private FutureWeather f2;
    private FutureWeather f3;
    private  FutureWeather f4;
    private  FutureWeather f5;
    private  FutureWeather f6;
    private  FutureWeather f7;
    private int ret_code;
    private  String  time;

    private  NowWeather now;

    public WeatherRespondBody() {
    }

    public FutureWeather getF1() {
        return f1;
    }

    public void setF1(FutureWeather f1) {
        this.f1 = f1;
    }

    public FutureWeather getF2() {
        return f2;
    }

    public void setF2(FutureWeather f2) {
        this.f2 = f2;
    }

    public FutureWeather getF3() {
        return f3;
    }

    public void setF3(FutureWeather f3) {
        this.f3 = f3;
    }

    public FutureWeather getF4() {
        return f4;
    }

    public void setF4(FutureWeather f4) {
        this.f4 = f4;
    }

    public FutureWeather getF5() {
        return f5;
    }

    public void setF5(FutureWeather f5) {
        this.f5 = f5;
    }

    public FutureWeather getF6() {
        return f6;
    }

    public void setF6(FutureWeather f6) {
        this.f6 = f6;
    }

    public FutureWeather getF7() {
        return f7;
    }

    public void setF7(FutureWeather f7) {
        this.f7 = f7;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NowWeather getNow() {
        return now;
    }

    public void setNow(NowWeather now) {
        this.now = now;
    }
}
