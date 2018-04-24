/*
 * 文件名：StringTools.java
 * 版权：Copyright by www.kimascend.com
 * 描述：
 * 修改人：Lenovo
 * 修改时间：2015年9月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.rt.sm.utils;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

import com.rt.sm.common.MyApp;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
    private static Typeface typeface2;

    public static String getDecimal(Object o) {
        // 保留二位小数
        return String.format("%.1f", o);
    }

    public static void main(String[] args) {
        System.out.println(StringTools.getDecimal(15.1));
        ;
    }

    /**
     * 字符串为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || str.length() == 0);
    }

    /**
     * Double变字符串，两位小数
     *
     * @param a
     * @return
     */
    public static String changes(Double a) {
        DecimalFormat fnum = new DecimalFormat("###########0.00");
        String dd = fnum.format(a);
        return dd;
    }

    /**
     * 检查手机号
     */
    public static boolean isMobileNumber(String mobiles) {
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 检查密码是否包含字母和数字
     */
    public static boolean isPassWord(String password) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 设置ttf字体
     */

    public static void setText(TextView textView, String code) {
        typeface2 = Typeface.createFromAsset(MyApp.APP_CONTEXT.getAssets(),
                "iconfont.ttf");
        textView.setTypeface(typeface2);
        textView.setText(code);
    }

    public static void setHint(EditText editText, String code) {
        typeface2 = Typeface.createFromAsset(MyApp.APP_CONTEXT.getAssets(),
                "iconfont.ttf");
        editText.setTypeface(typeface2);
        editText.setHint(code);
    }

    /**
     * 获取最新已完成的position
     *
     * @param list
     * @return
     */
    public static int getNowPosition(List<String> list) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int position = -1;
        String time = "1990-01-01 00:00:00";
        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i).equals("") || list.get(i) == null)) {
                try {
                    Date dt1 = df.parse(time);
                    Date dt2 = df.parse(list.get(i));
                    if (dt1.getTime() <= dt2.getTime()) {
                        time = list.get(i);
                        position = i;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return position;
    }

    /**
     * 打印提交的参数
     * @param object
     * @return
     */
    public static String gsonBean(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
