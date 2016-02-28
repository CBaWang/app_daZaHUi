package com.example.interact_chat;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/26.
 */
public class HongHttpUtils {

    private static String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static String URLPATH = "http://www.tuling123.com/openapi/api";

    public static ChatMessage sendMsg(String msg) {
        ChatMessage message = new ChatMessage();

        String url = setParam(msg);
        String res = doGet(url);
        Gson gson = new Gson();

        Result result = gson.fromJson(res, Result.class);

        if (result.getCode() > 400000 || result.getText() == null
                || result.getText().trim().equals("")) {
            message.setMsg("该功能等待开发...");
        } else {
            message.setMsg(result.getText());
        }

        message.setType(ChatMessage.Type.INPUT);
        message.setData(new Date());


        return message;

    }

    private static String doGet(String urlStr) {

        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new CommonException("服务器连接错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("服务器连接错误！");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();

        }//end

    }

    private static String setParam(String msg) {  //拼凑好的get请求

        try {
            msg = URLEncoder.encode(msg, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return URLPATH + "?key=" + API_KEY + "&info=" + msg;
    }


}