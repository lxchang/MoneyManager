package com.luxc.moneymanager.utils;

import android.widget.Toast;

import com.luxc.moneymanager.application.MyApp;

public class ToastUtils {
    private boolean showToast;

    public static void showShort(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
