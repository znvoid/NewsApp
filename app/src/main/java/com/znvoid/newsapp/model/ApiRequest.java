package com.znvoid.newsapp.model;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.znvoid.newsapp.bean.Api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zn on 2017/4/9.
 */

public class ApiRequest {

    private  String url;
    Map<String, String> parames;
    private Listener listener;
    public ApiRequest(String url,Listener listener) {

        this.listener=listener;
        this.url = url;

        parames = new HashMap<>();
        parames.put("showapi_appid", Api.APPID);
        parames.put("showapi_sign", Api.SECRET);


    }
    public ApiRequest(Listener listener) {

        this.listener=listener;
        this.url = "http://192.168.199.189:8080/ExpSerch/Serch";

        parames = new HashMap<>();
        parames.put("showapi_appid", Api.APPID);
        parames.put("showapi_sign", Api.SECRET);


    }


    public ApiRequest addParemeter(String key, String values) {
        parames.put(key, values);
        return this;
    }

    public void post(RequestQueue queue) {
        StringRequest request=  new  StringRequest(Request.Method.POST,url,listener,listener){
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              return parames;
          }
      };

        queue.add(request);
    }
    public interface Listener extends Response.Listener<String>, Response.ErrorListener {


        @Override
        void onResponse(String response);

        @Override
        void onErrorResponse(VolleyError error);
    }



}
