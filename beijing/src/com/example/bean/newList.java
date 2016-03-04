package com.example.bean;

/**
 * Created by Administrator on 2016/3/1.
 */
public class newList {

    private String ctime;

    private String description;

    private String picUrl;

    private String title;

    private String url;

    public newList(String ctime, String description, String picUrl, String title, String url) {
        this.ctime = ctime;
        this.description = description;
        this.picUrl = picUrl;
        this.title = title;
        this.url = url;
    }


    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "newList{" +
                "ctime='" + ctime + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
