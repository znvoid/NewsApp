package com.znvoid.newsapp.bean;

/**
 * Created by zn on 2017/4/8.
 */

public class Img {

    private String url;
    private  String width;
    private String height;

    public Img() {
    }

    public Img(String url, String width, String height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
