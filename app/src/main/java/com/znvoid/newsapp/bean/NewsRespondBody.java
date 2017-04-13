package com.znvoid.newsapp.bean;

/**
 * 新闻内容返回Json 数据对应的showapi_res_body
 * Created by zn on 2017/4/9.
 */

public class NewsRespondBody {

    private  int allNum;
    private  int currentPage;
    private  int maxResult;
    private int ret_code;
    private PageBean pagebean;

    public NewsRespondBody() {
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }
}
