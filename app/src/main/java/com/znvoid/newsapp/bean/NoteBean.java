package com.znvoid.newsapp.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by zn on 2017/5/4.
 */

public class NoteBean extends DataSupport {

    private long id;
    private  String content;
    private  long time;

    public NoteBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
