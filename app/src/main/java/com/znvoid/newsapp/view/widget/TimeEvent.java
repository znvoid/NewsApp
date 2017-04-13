package com.znvoid.newsapp.view.widget;

/**
 * Created by zn on 2017/4/12.
 */

public  class TimeEvent {
    private   String time;
    private   String event;

    public TimeEvent() {
    }

    public TimeEvent(String time, String event) {
        this.time = time;
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
