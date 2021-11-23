package com.yan.newaccountbook;

import android.app.Application;

import com.yan.newaccountbook.db.DBManger;
import com.yan.newaccountbook.db.DBOpenHelper;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManger.initDB(getApplicationContext());
    }
}
