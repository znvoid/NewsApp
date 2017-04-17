package com.znvoid.newsapp.bean;

/**
 * Created by zn on 2017/4/17.
 */

public class WeatherIndexInfo {

    private WeatherIndexInfoItem uv;//紫外线
    private WeatherIndexInfoItem clothes;//穿衣指数
    private WeatherIndexInfoItem cold;//感冒
    private WeatherIndexInfoItem travel;//旅游
    private WeatherIndexInfoItem sports;//运动
    private WeatherIndexInfoItem wash_car;//洗衣

    public WeatherIndexInfo() {
    }

    public WeatherIndexInfoItem getUv() {
        return uv;
    }

    public void setUv(WeatherIndexInfoItem uv) {
        this.uv = uv;
    }

    public WeatherIndexInfoItem getClothes() {
        return clothes;
    }

    public void setClothes(WeatherIndexInfoItem clothes) {
        this.clothes = clothes;
    }

    public WeatherIndexInfoItem getCold() {
        return cold;
    }

    public void setCold(WeatherIndexInfoItem cold) {
        this.cold = cold;
    }

    public WeatherIndexInfoItem getTravel() {
        return travel;
    }

    public void setTravel(WeatherIndexInfoItem travel) {
        this.travel = travel;
    }

    public WeatherIndexInfoItem getSports() {
        return sports;
    }

    public void setSports(WeatherIndexInfoItem sports) {
        this.sports = sports;
    }

    public WeatherIndexInfoItem getWash_car() {
        return wash_car;
    }

    public void setWash_car(WeatherIndexInfoItem wash_car) {
        this.wash_car = wash_car;
    }
}
