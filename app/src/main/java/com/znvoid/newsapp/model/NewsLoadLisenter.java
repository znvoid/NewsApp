package com.znvoid.newsapp.model;

import java.util.List;

/**
 * Created by zn on 2017/4/9.
 */

public interface NewsLoadLisenter<T> {

     void onComplete(List<T> items);

     void onError();

}
