package com.rt.sm.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {


    public static void compress(File file, int size, int quality)
            throws IOException {
        // 压缩大小
        FileInputStream stream = new FileInputStream(file);
        // 获取这个图片的宽和高
        Bitmap bitmap = ImageHelper.getImageThumbnail(file.getAbsolutePath(), 400, 600);
        stream.close();
        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        stream = new FileInputStream(file);
        stream.close();
        // 删除文件
        file.delete();
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream outstream = new FileOutputStream(file);
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outstream)) {
            outstream.flush();
            outstream.close();
        }
    }

    public static void compress2(Activity context, String imgPath)
            throws IOException {
        ImageHelper ih = new ImageHelper();
        Display display = context.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();// 720
        int height = display.getHeight();// 1280
        if (width > height)
            width = height;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        File jpeg = new File(imgPath);
        byte[] bytes = new byte[(int) jpeg.length()];
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(jpeg));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.readFully(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        float conversionFactor = 1.0f;
        if (opts.outWidth > opts.outHeight)
            conversionFactor = 1.0f;
        String orientation = "";
        orientation = ImageHelper.getExifOrientation1(imgPath, orientation);
        byte[] finalBytes = ih.createThumbnail(bytes,
                String.valueOf((int) (width * conversionFactor)), orientation,
                true);
        if (jpeg.delete()) {
            if (jpeg.createNewFile()) {
                FileOutputStream outstream = new FileOutputStream(jpeg);
                outstream.write(finalBytes, 0, finalBytes.length);
                outstream.flush();
                outstream.close();
            }
        }
    }




}
