package com.rt.sm.common;

import android.app.Application;
import android.content.Context;

import com.rt.sm.greendao.DBHelper;


/**
 * Created by 郝宏伟 on 2016/10/19.
 */

public class MyApp extends Application {


    public static Context APP_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = getApplicationContext();
        DBHelper.initDatabase(getApplicationContext());
    }



}
