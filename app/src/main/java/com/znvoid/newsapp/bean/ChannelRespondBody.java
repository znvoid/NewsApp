package com.znvoid.newsapp.bean;

import java.util.List;

/**
 * 新闻频道请求返回Json 数据对应的showapi_res_body
 * Created by zn on 2017/4/9.
 */

public class ChannelRespondBody {

    private  int totalNum;
    private int ret_code;
    private List<Channel> channelList;

    public ChannelRespondBody() {
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}
