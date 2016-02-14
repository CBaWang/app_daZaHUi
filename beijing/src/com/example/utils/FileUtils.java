package com.example.utils;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileUtils {

//    private boolean isFirst;//用来判断是否是第一次把缓存保存到本地

    private SharedPreferences share;
    private File dir;//文件的总路径
    private String sdDir;//sd卡根目录

    public FileUtils() {
        share = MyContext.getContext().getSharedPreferences("Data", Activity.MODE_PRIVATE);

//        isFirst = share.getBoolean("isFirst", true);

    }

    public void SaveDataLocal(String Json, String FileName) { //将数据保存到本地
        CreateTheFile(FileName);//创建文件夹
        SaveLocal(Json);//保存到本地
    }

    private void SaveLocal(String Json) {  //保存到本地

        FileWriter write;
        BufferedWriter buffer;

        try {
            write = new FileWriter(dir, false);
            buffer = new BufferedWriter(write);
            buffer.write(Json);
            buffer.newLine();
            buffer.flush();
            buffer.close();                   //在这里json数据被写入进去
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void CreateTheFile(String FileName) {//创建文件夹
        boolean sdExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "biejing";
            File file = new File(sdDir);
            if (!file.exists()) {       //创建目录
                file.mkdirs();
            }
            dir = new File(sdDir + File.separator + FileName + ".json");
            if (!dir.exists()) {
                try {                           //创建文件
                    dir.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public String getLocalData(String FileName) {  //获取本地数据
        String readDir = sdDir + File.separator + FileName + ".json";
        FileInputStream reader;
        BufferedReader buffer;
        String string;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new FileInputStream(readDir);
            buffer = new BufferedReader(new InputStreamReader(reader));//拿到了本地数据
            while ((string = buffer.readLine()) != null) {
                builder.append(string);
            }
            buffer.close();
            reader.close();
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
