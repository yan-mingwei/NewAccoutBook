package com.yan.newaccountbook.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yan.newaccountbook.bean.AccountBean;
import com.yan.newaccountbook.bean.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class DBManger {
    private static SQLiteDatabase db;

    public static void initDB(Context context){
        DBOpenHelper helper=new DBOpenHelper(context);
        db=helper.getWritableDatabase();  //得到数据库
    }

    /**
     * 查询所有的类型
     * @param
     * @return
     */
    public static List<TypeBean> getTypeList(int kind) {
        List<TypeBean>list = new ArrayList<>();
        //读取typetb表当中的数据
        String sql = "select * from types where kind = "+kind;
        Cursor cursor = db.rawQuery(sql, null);
//        循环读取游标内容，存储到对象当中
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String typename = cursor.getString(cursor.getColumnIndex("typename"));
            @SuppressLint("Range") int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") TypeBean typeBean = new TypeBean(id, typename, imageId, sImageId, kind);
            list.add(typeBean);
        }
        return list;
    }

    /**
     * 给记录表添加数据
     */
    public static void insertItemToAccountTB(AccountBean accountBean){
        ContentValues contentValues=new ContentValues();
        contentValues.put("typeName",accountBean.getTypeName());
        contentValues.put("sImageId",accountBean.getsImageId());
        contentValues.put("remark",accountBean.getRemark());
        contentValues.put("money",accountBean.getMoney());
        contentValues.put("time",accountBean.getTime());
        contentValues.put("year",accountBean.getYear());
        contentValues.put("month",accountBean.getMonth());
        contentValues.put("day",accountBean.getDay());
        contentValues.put("kind",accountBean.getKind());
        db.insert("accounts",null,contentValues);
    }

    /**
     * 查询某一天的收入、支出 总金额
     * @param year
     * @param month
     * @param day
     * @param kind
     * @return
     */
    public static float getSumMoneyThisDay(int year, int month, int day, int kind) {
        float total =0.0f;
        String sql="select sum(money) from accounts where year=? and month=? and day=? and kind=?";
        Cursor cursor=db.rawQuery(sql,new String[]{year +"", month +"", day + "",kind + ""});
        if(cursor.moveToNext()){
            @SuppressLint("Range") float money=cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total=money;
        }
        return total;
    }
    /**
     * 获取某一月的支出或者收入的总金额   kind：支出==0    收入===1
     * */
    public static float getSumMoneyThisMonth(int year,int month,int kind){
        float total = 0.0f;
        String sql = "select sum(money) from accounts where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        // 遍历
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            total = money;
        }
        return total;
    }

    public static List<AccountBean> getDayAccount(int year, int month, int day) {
        List<AccountBean> list=new ArrayList<>();
        String sql="select * from accounts where year=? and month=? and day=? order by id desc";
        Cursor cursor=db.rawQuery(sql,new String[]{year + "", month + "", day + ""});
        while (cursor.moveToNext()){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String typeName = cursor.getString(cursor.getColumnIndex("typeName"));
            @SuppressLint("Range") String remark = cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") int sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"));
            @SuppressLint("Range") int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            @SuppressLint("Range") float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typeName, sImageId, remark, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }

}
