package com.znvoid.newsapp.view.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.Api;
import com.znvoid.newsapp.bean.WeatherRespondBody;
import com.znvoid.newsapp.model.ApiRequest;
import com.znvoid.newsapp.model.ApiRespond;

/**
 * Created by zn on 2017/4/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WeatherService extends JobService implements ApiRequest.Listener {
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           if (msg.obj instanceof JobParameters){
               params= (JobParameters) msg.obj;
               updataWeather();
           }

        }
    };
    private RequestQueue requestQueue;
    private JobParameters params;

    @Override
    public boolean onStartJob(JobParameters params) {
        Message message = mHander.obtainMessage(1,params);
        mHander.sendMessage(message);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


   private void updataWeather(){
       SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       boolean yesno_save__info = defaultSharedPreferences.getBoolean("yesno_save__info", false);
       requestQueue = Volley.newRequestQueue(getApplicationContext());
       ApiRequest apiRequest ;
       if (yesno_save__info) {
           String user_info_city = defaultSharedPreferences.getString("user_info_city", "");
           apiRequest = new ApiRequest(Api.GET_WEATHER_BY_EAR_API, this);
           apiRequest.addParemeter("area", user_info_city);

       }else {
           apiRequest = new ApiRequest(Api.GET_WEATHER_BY_IP_API, this);
       }
       apiRequest
               .addParemeter("needIndex", "1")
               .addParemeter("needMoreDay", "1")
               .post(requestQueue);
   }


    @Override
    public void onResponse(String response) {

        try {
            ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<WeatherRespondBody>>() {
            });
            if (apiRespond.getShowapi_res_code() == 0) {
                WeatherRespondBody weatherRespondBody = apiRespond.getShowapi_res_body();
                if (weatherRespondBody.getRet_code() == 0) {
                    SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    defaultSharedPreferences.edit().putString("weather",response).putString("weatherUpdataTime",Util.getCurrentTime()).commit();


                } else if (weatherRespondBody.getRet_code() == -1) {
                    requestQueue = Volley.newRequestQueue(getApplicationContext());
                    ApiRequest apiRequest = new ApiRequest(Api.GET_WEATHER_BY_IP_API, this);
                    apiRequest
                            .addParemeter("needIndex", "1")
                            .addParemeter("needMoreDay", "1")
                            .post(requestQueue);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        onErrorResponse(null);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        requestQueue.stop();
        requestQueue=null;
        jobFinished( params, true );
        params=null;
    }
}

