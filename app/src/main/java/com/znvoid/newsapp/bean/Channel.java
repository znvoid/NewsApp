package com.znvoid.newsapp.bean;

/**
 * Created by zn on 2017/4/8.
 */

public class Channel  {
   private String channelId;
   private String name;

    public Channel() {
    }

    public Channel(String id) {
        channelId=id;
    }

    public Channel(String id, String name) {
        this.channelId=id;
        this.name=name;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
