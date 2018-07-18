package com.dongzhic.o2o.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 微信请求校验工具类
 * @author dongzc
 * @date 2018/7/17 11:07
 */
public class SignUtil {

    /**
     * 与接口配置信息中的Token要一致
     */
    private static String token = "myo2o";

    public static boolean checkSignature (String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce };
        //将token,timestamp,nonce三个参数进行字典排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i=0; i<arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个字符串拼接成一个字符串进行sha1加密
            byte [] digest = md.digest(content.toString().getBytes());
            tmpStr = byte2Str(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        //将加密后的字符串，与signature进行对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;

    }

    /**
     * 将字节数组转化未十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byte2Str (byte [] byteArray) {
        String strDigest = "";
        for (int i = 0; i< byteArray.length; i++) {
            strDigest += byte2HexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转化为十六进制字符串
     *  0x代表十六进制
     *  0F(16进制) -> 15(10进制)
     * @param mByte
     * @return
     */
    private static String byte2HexStr (byte mByte) {
        char [] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char [] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
