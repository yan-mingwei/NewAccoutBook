package com.yan.newaccountbook.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
