package com.yan.newaccountbook.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalToDate {
    public static String trans(Calendar calendar){
        Date date=calendar.getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = sdf.format(date);

        return time;
    }
}
