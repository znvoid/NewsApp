package com.znvoid.newsapp.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.FutureWeather;
import com.znvoid.newsapp.bean.NowWeather;
import com.znvoid.newsapp.bean.WeatherRespondBody;
import com.znvoid.newsapp.model.ApiRespond;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.LoadLisenter;
import com.znvoid.newsapp.presenter.Presenter;

/**
 * Created by zn on 2017/4/13.
 */

public class WeatherActivity extends AppCompatActivity implements LoadLisenter<WeatherRespondBody>, SwipeRefreshLayout.OnRefreshListener {


    private ImageView bgPicImg;//背景图片
    private ScrollView weatherLayout;
    private ImageView weatherPic;//天气图片
    private TextView degreeText;//温度
    private TextView weatherStatText;//天气
    private TextView weatherWindText;//风
    private TextView aqiText;//AQI指数
    private TextView pm25Text;//pm2.5指数
    private LinearLayout weatherFutureLayout;
    private SwipeRefreshLayout swipeRefresh;
    private TextView sdText;//湿度
    private TextView qualityText; //天气质量
    private TextView titleCity;
    private TextView titleTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        titleCity =(TextView)  findViewById(R.id.weather_title_city);
        titleTime = (TextView) findViewById(R.id.weather_title_time);
        setSupportActionBar(toolbar);
        bgPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
//        titleCity = (TextView) findViewById(R.id.title_city);
//        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
         weatherPic= (ImageView) findViewById(R.id.weather_now_pic);
        degreeText = (TextView) findViewById(R.id.weather_now_temp);
        weatherStatText = (TextView) findViewById(R.id.weather_now_stat);
        weatherWindText = (TextView) findViewById(R.id.weather_now_wind);


        aqiText = (TextView) findViewById(R.id.weather_aqi_text);
        pm25Text = (TextView) findViewById(R.id.weather_pm25_text);
        sdText = (TextView) findViewById(R.id.weather_sd_text);
        qualityText = (TextView) findViewById(R.id.weather_quality_text);

//        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        weatherFutureLayout = (LinearLayout) findViewById(R.id.weather_future_layout);


        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
        if (Presenter.getInstance().getDataFromPerference("weatherUpdataTime","null").equals(Util.getCurrentTime())){

            String weatherInfor=Presenter.getInstance().getDataFromPerference("weather","null");
            if (weatherInfor.equals("null")){
                requestNetWork();
            }else {
                parseWeatherInfo(weatherInfor);
            }

        }else {

          requestNetWork();
        }


    }
    private void requestNetWork(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String city= prefs.getString("user_info_city","杭州");
        ApiWorkManager.getInstance().getWeather(city,this);
    }


    private void showWeather(WeatherRespondBody item){
        NowWeather nowWeather=item.getNow();
        titleCity.setText(nowWeather.getAqiDetail().getArea());
        titleTime.setText(nowWeather.getTemperature_time());
        ImageLoader.getInstance().displayImage(nowWeather.getWeather_pic(),weatherPic);
        degreeText.setText(nowWeather.getTemperature());
        weatherStatText.setText(nowWeather.getWeather());
        weatherWindText.setText(nowWeather.getWind_direction()+"    "+nowWeather.getWind_power());
        weatherFutureLayout.removeAllViews();
        showFutureWeather(item.getF1());
        showFutureWeather(item.getF2());
        showFutureWeather(item.getF3());
        showFutureWeather(item.getF4());
        showFutureWeather(item.getF5());
        showFutureWeather(item.getF6());
        showFutureWeather(item.getF7());

        aqiText.setText(nowWeather.getAqiDetail().getAqi());
        pm25Text.setText(nowWeather.getAqiDetail().getPm2_5());
        sdText.setText(nowWeather.getSd());
        qualityText.setText(nowWeather.getAqiDetail().getQuality());
    }

    private void showFutureWeather(FutureWeather futureWeather){
        if (futureWeather==null)
            return;
        View view = LayoutInflater.from(this).inflate(R.layout.weather_future_item, weatherFutureLayout, false);
        TextView dateText = (TextView) view.findViewById(R.id.future_weather_date_text);
        ImageView img = (ImageView) view.findViewById(R.id.future_weather_pic);
        TextView infoText = (TextView) view.findViewById(R.id.future_weather_info_text);
        TextView degreeText = (TextView) view.findViewById(R.id.future_weather_temperature_text);
        String date=futureWeather.getDay().substring(0,4)+"-"+futureWeather.getDay().substring(4,6)+"-"+futureWeather.getDay().substring(6,8);
        dateText.setText(date);
        ImageLoader.getInstance().displayImage(futureWeather.getDay_weather_pic(),img);
        infoText.setText(futureWeather.getDay_weather());
        degreeText.setText(futureWeather.getDay_air_temperature()+"℃ ～ "+futureWeather.getNight_air_temperature()+"℃");
        weatherFutureLayout.addView(view);

    }
    @Override
    public void onComplete(WeatherRespondBody item) {
        swipeRefresh.setRefreshing(false);
        showWeather(item);
    }
    @Override
    public void onError() {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this,"加载天气失败",Toast.LENGTH_LONG).show();
    }

    private void parseWeatherInfo(String info){
        ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(info, new TypeReference<ApiRespond<WeatherRespondBody>>() {});
        WeatherRespondBody weatherRespondBody=apiRespond.getShowapi_res_body();
        showWeather(weatherRespondBody);
    }

    @Override
    public void onRefresh() {
        requestNetWork();
    }
}
