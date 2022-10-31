package com.yt.yunxiaoweimusic.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureCalcUtil {
    public static final String accesstoken = "93340aceee9b4a3b9eace50777b1d875";

    /**
     * 生成签名
     *
     * @param requestBody 请求体
     * @param time        时间戳
     * @return 签名
     */
    public static String getSignature(String requestBody, String time) {
        String signatureContent = requestBody + time;
        String signature = "";
        try {
            // HmacSHA256加密
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(accesstoken.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] byteData = mac.doFinal(signatureContent.getBytes());
            return bytes2String(byteData);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字节数组转换成字符串
     *
     * @param byteData 需要被转换的字节数组
     * @return 转换后的字符串
     */
    private static String bytes2String(byte[] byteData) {
        StringBuilder result = new StringBuilder();
        String temp;
        for (int n = 0; byteData != null && n < byteData.length; n++) {
            temp = Integer.toHexString(byteData[n] & 0XFF);
            if (temp.length() == 1) {
                result.append("0");
            }
            result.append(temp);
        }
        return result.toString().toLowerCase();
    }

    public static byte[] toByteArray(File audioFile) throws IOException {
        if (!audioFile.exists()) {
            throw new FileNotFoundException(audioFile.getAbsolutePath() + "/" + audioFile.getName());
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(audioFile);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                System.out.println("reading...");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                assert channel != null;
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
