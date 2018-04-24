package com.rt.sm.utils;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListView;

import java.io.File;
import java.security.MessageDigest;

/**
 * Created by seriex.x on 16/12/7.
 */

public class CommonUtil {


    /**
     * 用于给listview动态设定高度，使其在scrollview中可以直接全部展示，解决滑动冲突
     *
     * @param adapters
     * @param view
     */
    public static void setHeight(Adapter adapters, ListView view) {
        int listViewHeight = 0;
        int adaptCount = adapters.getCount();
        for (int i = 0; i < adaptCount; i++) {
            View temp = adapters.getView(i, null, view);
            temp.measure(0, 0);
            listViewHeight += temp.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

//        layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
// 不强制设定宽度，一般在布局中已经设定了宽度为parent，方便有些不match——parent的界面使用
        layoutParams.height = listViewHeight;
        view.setLayoutParams(layoutParams);
    }

    public static void setHeight(Adapter adapters, GridView view) {
        int listViewHeight = 0;
        int adaptCount = adapters.getCount();

        for (int i = 0; i < adaptCount; i++) {
            View temp = adapters.getView(i, null, view);
            temp.measure(0, 0);
            listViewHeight += temp.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        //获取GridView columns
//        int num = view.getNumColumns();
//        Log.i("xsy+++++++++++++", num + "");
        layoutParams.height = listViewHeight / 2;
        view.setLayoutParams(layoutParams);
    }


    /**
     * 将本地图片压缩成字符串上传
     * @return
     */
//    public static String upLoadStrToJson(String path) {
//        String string;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            Bitmap bit = BitmapFactory.decodeFile(path);
//            bit.compress(Bitmap.CompressFormat.JPEG, 100, out);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//            int options = 100;//JPG,JPEG,GIF,PNG
//            String name = path.substring(path.lastIndexOf(".") + 1, path.length());//截取文件名
//
//            if (name.equalsIgnoreCase("PNG") || name.equalsIgnoreCase("JPG") || name.equalsIgnoreCase("JPEG")) {
//                //只有图片才压缩
//                while (out.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
//                    out.reset();//重置baos即清空baos
//                    options -= 10;//每次都减少10
//                    bit.compress(Bitmap.CompressFormat.JPEG, options, out);//这里压缩options%，把压缩后的数据存放到baos中
//                }
//            }
//            string = new String(Base64.encode(out.toByteArray(), Base64.DEFAULT));// encoder.encodeBuffer(oup.toByteArray());
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IllegalStateException("input file not found", e);
//        }
//        return string;
//    }

    /**
     * MD5 加密
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
