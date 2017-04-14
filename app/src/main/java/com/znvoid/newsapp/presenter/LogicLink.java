package com.znvoid.newsapp.presenter;

import android.view.View;

/**
 * Created by zn on 2017/4/12.
 */

public interface LogicLink {

     void pageMark(int page);
     void saveDataToPreference(String key,String values);
     String  getDataFromPerference(String key,String defaul);

     void onClick(View view);
     void showBottomSheet();
}
