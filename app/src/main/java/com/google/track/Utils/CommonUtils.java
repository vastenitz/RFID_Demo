package com.google.track.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static String getDate(long timestamp) {
        Date mDate = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String date = format.format(mDate).toString();
        return date;
    }

    public static String getTime(long timestamp) {
        Date mDate = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(mDate).toString();
        return time;
    }
}
