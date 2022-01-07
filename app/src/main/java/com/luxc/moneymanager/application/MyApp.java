package com.luxc.moneymanager.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.luxc.moneymanager.greendao.db.DaoMaster;
import com.luxc.moneymanager.greendao.db.DaoSession;

public class MyApp extends Application {

    private static MyApp instance;
    private DaoSession daoSession;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDao();
    }


    /**
     * 数据库初始化
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "myApp.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
