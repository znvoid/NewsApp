package com.znvoid.newsapp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by zn on 2017/4/9.
 */

public class ApiRespond<T> {
    private int showapi_res_code;
    private String showapi_res_error;
   private T showapi_res_body;



    public ApiRespond() {
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public T getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(T showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }
    public static <T> ApiRespond<T> parserJsonString(String jsonString,TypeToken<ApiRespond<T>> typeToken ){
        Gson gson = new Gson();
//        Type jsonType = new TypeToken<ApiRespond<T> >() {
//        }.getType();
        Type jsonType= typeToken.getType();

        return   gson.fromJson(jsonString, jsonType);
    }

}
