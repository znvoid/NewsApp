package com.znvoid.newsapp.model;

/**
 * Created by zn on 2017/4/9.
 */

public interface LoadLisenter<T> {

     void onComplete(T item);

     void onError();

}
