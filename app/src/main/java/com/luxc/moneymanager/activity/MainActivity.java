package com.luxc.moneymanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.luxc.moneymanager.activity.manager.FamilyManagerActivity;
import com.luxc.moneymanager.activity.manager.UserManagerActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        MultPermission2();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_my_info, R.id.tv_in, R.id.tv_out, R.id.tv_family_user, R.id.tv_user_manager, R.id.tv_family_manager})
    public void onClickView(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_my_info:
                intent.setClass(this, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_in:
                intent.setClass(this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_out:
                intent.setClass(this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_family_user:
                intent.setClass(this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_user_manager:
                intent.setClass(this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_family_manager:
                intent.setClass(this, FamilyManagerActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 同时请求多个权限（分别获取结果）的情况
     */
    private void MultPermission2() {

        rxPermissions = new RxPermissions(MainActivity.this); // where this is an Activity instance
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE)//权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "{accept}permission.name=" + permission.name);
                        Log.e(TAG, "{accept}permission.granted=" + permission.granted);
                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
                            // 已经获取权限
                            Toast.makeText(MainActivity.this, "已经获取权限", Toast.LENGTH_SHORT).show();
//                            String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();//根据不同的手机设备返回IMEI，MEID或者ESN码
//                            Toast.makeText(MainActivity.this, "{accept}deviceId=" + deviceId, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}