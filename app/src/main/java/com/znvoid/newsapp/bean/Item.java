package com.znvoid.newsapp.bean;

/**
 * Created by zn on 2017/4/8.
 */

public class Item {
    private String title;//	新闻标题
    private String link;    //新闻详情链接
    private String pubDate;//	2015-06-06 09:09:42	发布时间
    private String source;//新华网	来源网站
    private String desc;//MERS”疫情致兰州游客赴韩游热情减退受韩国中东	新闻简要描述
    private String channelId;    //	5572a108b3cdc86cf39001cd	频道id
    private Img[] imageurls;    //		图片列表
    private String channelName;//频道名称
    private boolean havePic;//是否有图片


    public Item() {
    }

    public Item(String title, String desc, String source, String pubDate, Img[] imageurls) {
        this.title = title;
        this.desc = desc;
        this.source = source;
        this.pubDate = pubDate;
        this.imageurls = imageurls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public Img[] getImageurls() {
        return imageurls;
    }

    public void setImageurls(Img[] imageurls) {
        this.imageurls = imageurls;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return title.equals(item.getTitle()) && link.equals(item.getLink()) && pubDate.equals(item.getPubDate());

        }


        return false;
    }
}
