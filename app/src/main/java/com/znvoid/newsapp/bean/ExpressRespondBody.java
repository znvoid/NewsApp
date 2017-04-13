package com.znvoid.newsapp.bean;

import java.util.List;

/**
 * Created by zn on 2017/4/11.
 */

public class ExpressRespondBody {
    private  String nu;
    private String ret_code;
    private   String ischeck;
    private  String com;
    private String com_zh;
    private   List<ExpressData> data;
    private String state;
    private String error_code;
    private String error_description;

    public ExpressRespondBody() {
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCom_zh() {
        return com_zh;
    }

    public void setCom_zh(String com_zh) {
        this.com_zh = com_zh;
    }

    public List<ExpressData> getData() {
        return data;
    }

    public void setData(List<ExpressData> data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
