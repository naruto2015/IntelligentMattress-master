package com.rt.sm.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by gaodesong on 18/1/23.
 */

public class DBHelper {

    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    /**
     * 初始化greenDao，建议在Application初始化的时候添加；
     */
    public static void initDatabase(Context context) {
        mHelper = new DaoMaster.DevOpenHelper(context, "cache-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }
}
