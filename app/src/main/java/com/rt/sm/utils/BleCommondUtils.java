package com.rt.sm.utils;

/**
 * 作者:Created by haohw on 2018/2/8.
 * 邮箱:303729942@qq.com
 * Blog:http://blog.csdn.net/qq_31660173
 */

public class BleCommondUtils {

    public static byte[] stringToBytes(String text) {
        int len = text.length();
        byte[] bytes = new byte[(len + 1) / 2];
        for (int i = 0; i < len; i += 2) {
            int size = Math.min(2, len - i);
            String sub = text.substring(i, i + size);
            bytes[i / 2] = (byte) Integer.parseInt(sub, 16);
        }
        return bytes;
    }

    //使用1字节就可以表示b
    public static String numToHex8(int b) {
        return String.format("%02x", b);//2表示需要两个16进行数
    }
}
