package com.rt.sm.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.rt.sm.common.MyApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Toast提示工具类(避免无限制弹出Toast)
 */
public class ToastUtils {

    private static final String TAG = "ToastUtils";

    private static Toast mToast = null;

    public static void showLongMsg(String text) {
        // Toast.LENGTH_LONG（3.5秒）
        showMsg(text, Toast.LENGTH_LONG);
    }

    public static void showShortMsg(String text) {
        // Toast.LENGTH_SHORT（2秒）
        showMsg(text, Toast.LENGTH_SHORT);
    }

    private static void showMsg(String text, int duriation) {
        if (!StringTools.isEmpty(text)) {
            if (null == mToast) {
                mToast = Toast.makeText(MyApp.APP_CONTEXT, text, duriation);

            } else {
                mToast.setText(text);
                mToast.setDuration(duriation);
            }
            mToast.show();

        } else {
            Logger.i(TAG, "text is empty.");
        }

    }

    /**
     * toast测试用来玩玩
     *
     * @param string
     */
    public static void testMsg(String string) {
        showShortMsg(string);
    }


//    金钱正则
        public static boolean isMoney(String mobiles) {
            Pattern p = Pattern
                    .compile("^([1-9]\\d*|0)(\\.\\d+)?$");
            Matcher m = p.matcher(mobiles);
            return m.matches();
        }

    //手机正则

    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、177、178、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、7、8中
        // 的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 电话号码验证
     *
     * @param  str
     * @return 验证通过返回true
     */

    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m  = null;
        boolean b  = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
}

