package com.example.utils;


import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileUtils {

    private File dir;//文件的总路径
    private final static String sdDir = Environment.
            getExternalStorageDirectory().getAbsolutePath() + File.separator + "biejing";
    ;//sd卡根目录


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
//            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "biejing";
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
    }//end

    public static boolean deleteAllFiles() {  //删除app文件夹里的文件
        File dirFile = new File(sdDir);
        if (dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();

            }
        }

        if (dirFile.isFile()) {
            return false;
        }

        return true;
    }//end


}
