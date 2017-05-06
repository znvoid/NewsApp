package com.znvoid.newsapp.view.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.znvoid.newsapp.R;
import com.znvoid.newsapp.bean.FutureWeather;
import com.znvoid.newsapp.bean.NowWeather;
import com.znvoid.newsapp.bean.WeatherIndexInfo;
import com.znvoid.newsapp.bean.WeatherRespondBody;
import com.znvoid.newsapp.model.ApiRespond;
import com.znvoid.newsapp.model.ApiWorkManager;
import com.znvoid.newsapp.model.LoadLisenter;
import com.znvoid.newsapp.presenter.Presenter;
import com.znvoid.newsapp.view.service.WeatherService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zn on 2017/4/13.
 */

public class WeatherActivity extends AppCompatActivity implements LoadLisenter<WeatherRespondBody>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "WeatherActivity";
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
    private ImageLoadListener imageLoadListener;
    private TextView zs_title1;
    private TextView zs_title2;
    private TextView zs_title3;
    private TextView zs_title4;
    private TextView zs_title5;
    private TextView zs_title6;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean updata = intent.getBooleanExtra("weather_updata", false);
            if (updata) {
                parseWeatherInfo(Presenter.getInstance().getDataFromPerference("weather", "null"));
            }
        }
    };
    private static long AWAKE_ITME=30 * 60 * 1000;

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
        imageLoadListener = new ImageLoadListener();
        if (Build.VERSION.SDK_INT >= 21) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();

//            for (int i = 0; i < allPendingJobs.size(); i++) {
//                Log.e("weather",allPendingJobs.get(i).getId()+"");
//
//            }
//            jobScheduler.cancelAll();
            JobInfo.Builder builder = new JobInfo.Builder(1,
                    new ComponentName(getPackageName(),
                            WeatherService.class.getName()));
            builder.setPeriodic(AWAKE_ITME)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            jobScheduler.schedule(builder.build());
        }else {
//            Intent _Intent = new Intent(getApplicationContext(), WeatherService2.class);
//            PendingIntent _PendingIntent = PendingIntent.getService(this, 0, _Intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            AlarmManager _AlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//            _AlarmManager.cancel(_PendingIntent);
//            _AlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AWAKE_ITME, AWAKE_ITME, _PendingIntent);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.znvoid.nwasapp.weather_updata");
        registerReceiver(mBroadcastReceiver,filter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        titleCity = (TextView) findViewById(R.id.weather_title_city);
        titleTime = (TextView) findViewById(R.id.weather_title_time);
        setSupportActionBar(toolbar);
        bgPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
//        titleCity = (TextView) findViewById(R.id.title_city);
//        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        weatherPic = (ImageView) findViewById(R.id.weather_now_pic);
        degreeText = (TextView) findViewById(R.id.weather_now_temp);
        weatherStatText = (TextView) findViewById(R.id.weather_now_stat);
        weatherWindText = (TextView) findViewById(R.id.weather_now_wind);


        aqiText = (TextView) findViewById(R.id.weather_aqi_text);
        pm25Text = (TextView) findViewById(R.id.weather_pm25_text);
        sdText = (TextView) findViewById(R.id.weather_sd_text);
        qualityText = (TextView) findViewById(R.id.weather_quality_text);

//        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        weatherFutureLayout = (LinearLayout) findViewById(R.id.weather_future_layout);

        zs_title1 = (TextView) findViewById(R.id.tq_xs_title_1);
        zs_title2 = (TextView) findViewById(R.id.tq_xs_title_2);
        zs_title3 = (TextView) findViewById(R.id.tq_xs_title_3);
        zs_title4 = (TextView) findViewById(R.id.tq_xs_title_4);
        zs_title5 = (TextView) findViewById(R.id.tq_xs_title_5);
        zs_title6 = (TextView) findViewById(R.id.tq_xs_title_6);


        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);

//        if (Presenter.getInstance().getDataFromPerference("weatherUpdataTime", "null").equals(Util.getCurrentTime())) {

            String weatherInfor = Presenter.getInstance().getDataFromPerference("weather", "null");
            if (weatherInfor.equals("null")) {
//                requestNetWork();
            } else {
                parseWeatherInfo(weatherInfor);
            }

//        } else {
//
//            requestNetWork();
//        }

    }

    private void requestNetWork() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("yesno_save__info", false)) {
            String city = prefs.getString("user_info_city", "");
            ApiWorkManager.getInstance().getWeather(city, this);
        } else {
            ApiWorkManager.getInstance().getWeather(this);
        }

    }


    private void showWeather(WeatherRespondBody item) {
        NowWeather nowWeather = item.getNow();
        titleCity.setText(nowWeather.getAqiDetail().getArea());
        titleTime.setText(nowWeather.getTemperature_time());
        ImageLoader.getInstance().displayImage(nowWeather.getWeather_pic(), weatherPic, imageLoadListener);
        degreeText.setText(nowWeather.getTemperature());
        weatherStatText.setText(nowWeather.getWeather());
        weatherWindText.setText(nowWeather.getWind_direction() + "    " + nowWeather.getWind_power());
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
        WeatherIndexInfo indexInfo = item.getF1().getIndex();
        zs_title1.setText(indexInfo.getUv().getTitle());
        zs_title2.setText(indexInfo.getClothes().getTitle());
        zs_title3.setText(indexInfo.getCold().getTitle());
        zs_title4.setText(indexInfo.getTravel().getTitle());
        zs_title5.setText(indexInfo.getSports().getTitle());
        zs_title6.setText(indexInfo.getWash_car().getTitle());

    }

    private void showFutureWeather(FutureWeather futureWeather) {
        if (futureWeather == null)
            return;
        View view = LayoutInflater.from(this).inflate(R.layout.weather_future_item, weatherFutureLayout, false);
        TextView dateText = (TextView) view.findViewById(R.id.future_weather_date_text);
        ImageView img = (ImageView) view.findViewById(R.id.future_weather_pic);
        TextView infoText = (TextView) view.findViewById(R.id.future_weather_info_text);
        TextView degreeText = (TextView) view.findViewById(R.id.future_weather_temperature_text);
        String date = futureWeather.getDay().substring(0, 4) + "-" + futureWeather.getDay().substring(4, 6) + "-" + futureWeather.getDay().substring(6, 8);
        dateText.setText(date);
        ImageLoader.getInstance().displayImage(futureWeather.getDay_weather_pic(), img, imageLoadListener);
        infoText.setText(futureWeather.getDay_weather());
        degreeText.setText(futureWeather.getDay_air_temperature() + "℃ ～ " + futureWeather.getNight_air_temperature() + "℃");
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
        Toast.makeText(this, "加载天气失败", Toast.LENGTH_LONG).show();
    }

    private void parseWeatherInfo(String info) {
        ApiRespond<WeatherRespondBody> apiRespond = ApiRespond.parserJsonString(info,new TypeToken<ApiRespond<WeatherRespondBody>>(){});
        WeatherRespondBody weatherRespondBody = apiRespond.getShowapi_res_body();
        showWeather(weatherRespondBody);
    }

    @Override
    public void onRefresh() {
        requestNetWork();
    }

    private class ImageLoadListener extends SimpleImageLoadingListener {
        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (view != null && view instanceof ImageView) {
                String path = imageUri.replace("http://app1.showapi.com/weather/", "");
                try {
                    InputStream inputStream = getResources().getAssets().open(path);
                    ((ImageView) view).setImageBitmap(BitmapFactory.decodeStream(inputStream));

                } catch (IOException e) {
                    Log.e(TAG, "onLoadingFailed: 加载图片失败");
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
