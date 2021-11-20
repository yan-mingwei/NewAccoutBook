package com.yan.newaccountbook.utils;

import android.content.Context;
import android.widget.Toast;
import android.widget.Toolbar;

public class ToastUtil {

    public static void show(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
