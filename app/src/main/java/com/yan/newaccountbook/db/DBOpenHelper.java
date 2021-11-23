package com.yan.newaccountbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.yan.newaccountbook.R;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="account.db";
    private static final String TYPE_TABLE_NAME="types";
    private static final String CREATE_TYPE_SQL="create table "+TYPE_TABLE_NAME+ "(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";


    public DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * 创建数据库，项目第一次运行时会被调用
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TYPE_SQL);
        insertType(sqLiteDatabase);
    }

    /**
     * 初始时向数据库中插入数据
     * @param db
     */
    private void insertType(SQLiteDatabase db) {
        String sql = "insert into types (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_fs,0});
        db.execSQL(sql,new Object[]{"餐饮", R.mipmap.ic_canyin,R.mipmap.ic_canyin_fs,0});
        db.execSQL(sql,new Object[]{"交通", R.mipmap.ic_jiaotong,R.mipmap.ic_jiaotong_fs,0});
        db.execSQL(sql,new Object[]{"购物", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
        db.execSQL(sql,new Object[]{"服饰", R.mipmap.ic_fushi,R.mipmap.ic_fushi_fs,0});
        db.execSQL(sql,new Object[]{"日用品", R.mipmap.ic_riyongpin,R.mipmap.ic_riyongpin_fs,0});
        db.execSQL(sql,new Object[]{"娱乐", R.mipmap.ic_yule,R.mipmap.ic_yule_fs,0});
        db.execSQL(sql,new Object[]{"零食", R.mipmap.ic_lingshi,R.mipmap.ic_lingshi_fs,0});
        db.execSQL(sql,new Object[]{"烟酒茶", R.mipmap.ic_yanjiu,R.mipmap.ic_yanjiu_fs,0});
        db.execSQL(sql,new Object[]{"学习", R.mipmap.ic_xuexi,R.mipmap.ic_xuexi_fs,0});
        db.execSQL(sql,new Object[]{"医疗", R.mipmap.ic_yiliao,R.mipmap.ic_yiliao_fs,0});
        db.execSQL(sql,new Object[]{"住宅", R.mipmap.ic_zhufang,R.mipmap.ic_zhufang_fs,0});
        db.execSQL(sql,new Object[]{"水电煤", R.mipmap.ic_shuidianfei,R.mipmap.ic_shuidianfei_fs,0});
        db.execSQL(sql,new Object[]{"通讯", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_fs,0});
        db.execSQL(sql,new Object[]{"人情往来", R.mipmap.ic_renqingwanglai,R.mipmap.ic_renqingwanglai_fs,0});

        db.execSQL(sql,new Object[]{"其他", R.mipmap.in_qt,R.mipmap.in_qt_fs,1});
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_xinzi,R.mipmap.in_xinzi_fs,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_fs,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.in_jieru,R.mipmap.in_jieru_fs,1});
        db.execSQL(sql,new Object[]{"收债", R.mipmap.in_shouzhai,R.mipmap.in_shouzhai_fs,1});
        db.execSQL(sql,new Object[]{"利息收入", R.mipmap.in_lixifuji,R.mipmap.in_lixifuji_fs,1});
        db.execSQL(sql,new Object[]{"投资回报", R.mipmap.in_touzi,R.mipmap.in_touzi_fs,1});
        db.execSQL(sql,new Object[]{"二手交易", R.mipmap.in_ershoushebei,R.mipmap.in_ershoushebei_fs,1});
        db.execSQL(sql,new Object[]{"意外所得", R.mipmap.in_yiwai,R.mipmap.in_yiwai_fs,1});

    }

    // 数据库版本在更新时发生改变，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
