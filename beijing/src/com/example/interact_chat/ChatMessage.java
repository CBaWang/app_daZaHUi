package com.example.interact_chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/26.
 */
public class ChatMessage {

    public enum Type{
        INPUT, OUTPUT
    }

    private Type type;

    private String msg;

    private Date data;

    private String dateStr;

    private String name;

    public ChatMessage() {
    }


    public ChatMessage(Type type, String msg) {
        super();
        this.type = type;
        this.msg = msg;
        setData(new Date());

    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
        DateFormat df =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = df.format(data);
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", dateStr='" + dateStr + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
