package com.luxc.moneymanager.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    public static Date parseStringToDate(String str) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatToString(Date date, String format) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(format);
        String time = dateFormat.format(date);
        return time;
    }
}
