package com.znvoid.newsapp.model;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.znvoid.newsapp.presenter.Presenter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zn on 2017/4/17.
 */

public class MyImageDownloader extends BaseImageDownloader {
    public MyImageDownloader(Context context) {
        super(context);
    }

    public MyImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    public InputStream getStream(String imageUri, Object extra) throws IOException {
        if (!Presenter.getInstance().checkNetStat())
            return null;
        return super.getStream(imageUri, extra);
    }
}
