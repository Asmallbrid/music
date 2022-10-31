package com.yt.yunxiaoweimusic.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AssetsUtil {
    public static String getDataFromJson(Context context,String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //读取assets目录下的json文件
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            //Qua qua = new Gson().fromJson(stringBuilder.toString(),Qua.class);
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
