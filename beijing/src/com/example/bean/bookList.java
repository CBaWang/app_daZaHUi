package com.example.bean;

/**
 * Created by Administrator on 2016/2/15.
 */
public class bookList {

    private String author;

    private String from;

    private int id;

    private String img;

    private String name;

    private String summary;

    public bookList(String author, String from, int id, String img, String name, String summary) {
        this.author = author;
        this.from = from;
        this.id = id;
        this.img = img;
        this.name = name;
        this.summary = summary;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "bookList{" +
                "author='" + author + '\'' +
                ", from='" + from + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
