package com.znvoid.newsapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zn on 2017/4/8.
 */

public class PageBean implements Serializable {
    private int allNum;
    private int allPages;
    private int currentPage;
    private int maxResult;
    private List<Item> contentlist;

    public PageBean() {
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
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

    public List<Item> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<Item> contentlist) {
        this.contentlist = contentlist;
    }
}
