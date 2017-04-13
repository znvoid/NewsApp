package com.znvoid.newsapp.bean;

import java.util.List;

/**
 *
 * 图片请求返回Json 数据对应的showapi_res_body
 * Created by zn on 2017/4/10.
 */

public class PictureRespondBody {
    private String code;
    private String msg;
    private List<Picture> newslist;

    public PictureRespondBody() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Picture> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Picture> newslist) {
        this.newslist = newslist;
    }
}
