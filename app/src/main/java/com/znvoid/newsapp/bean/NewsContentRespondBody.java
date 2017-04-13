package com.znvoid.newsapp.bean;

import java.util.List;

/**
 * 新闻正文抽取结果体
 * Created by zn on 2017/4/10.
 */

public class NewsContentRespondBody {
    private String content;
    private String html;
    private List<Img> img_list;
    private String  time;
    private String title;
    private String all_list;//title img_list content 集合

    public NewsContentRespondBody() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<Img> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<Img> img_list) {
        this.img_list = img_list;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAll_list() {
        return all_list;
    }

    public void setAll_list(String all_list) {
        this.all_list = all_list;
    }
}
