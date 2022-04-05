package com.luxc.moneymanager.activity;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.FamilyManagerActivity;
import com.luxc.moneymanager.activity.manager.FamilyUserManagerActivity;
import com.luxc.moneymanager.activity.manager.UserManagerActivity;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.tv_apply_list)
    TextView tvApplyList;
    @BindView(R.id.tv_my_info)
    TextView tvMyInfo;
    @BindView(R.id.tv_user_manager)
    TextView tvUserManager;
    @BindView(R.id.tv_family_manager)
    TextView tvFamilyManager;
    @BindView(R.id.tv_family_member_manager)
    TextView tvFamilyMemberManager;
    @BindView(R.id.tv_record)
    TextView tvRecord;

    private static final String TAG = "MainActivity";
    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        llBack.setVisibility(View.GONE);
        mainTitle.setText("首页");
        MultPermission2();
        int userType = (int) SharedPreferenceUtils.get(MainActivity.this, "currentUserType", 0);

        switch (userType) {
            case 0://系统管理员
                tvMyInfo.setVisibility(View.GONE);
                tvRecord.setVisibility(View.GONE);
                tvApplyList.setVisibility(View.VISIBLE);
                tvUserManager.setVisibility(View.VISIBLE);
                tvFamilyManager.setVisibility(View.GONE);
                tvFamilyMemberManager.setVisibility(View.GONE);
                break;
            case 1://家庭管理员
                tvMyInfo.setVisibility(View.VISIBLE);
                tvRecord.setVisibility(View.VISIBLE);
                tvApplyList.setVisibility(View.GONE);
                tvUserManager.setVisibility(View.GONE);
                tvFamilyManager.setVisibility(View.VISIBLE);
                tvFamilyMemberManager.setVisibility(View.VISIBLE);
                break;
            case 2://普通用户
                tvMyInfo.setVisibility(View.VISIBLE);
                tvRecord.setVisibility(View.VISIBLE);
                tvApplyList.setVisibility(View.GONE);
                tvUserManager.setVisibility(View.GONE);
                tvFamilyMemberManager.setVisibility(View.GONE);
                tvFamilyManager.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_my_info, R.id.tv_record, R.id.tv_family_member_manager, R.id.tv_user_manager, R.id.tv_family_manager, R.id.tv_apply_list})
    public void onClickView(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_my_info:
                intent.setClass(this, UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_record:
                intent.setClass(this, MyIncomeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_family_member_manager:
                intent.setClass(this, FamilyUserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_family_manager:
                intent.setClass(this, FamilyManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_user_manager:
                intent.setClass(this, UserManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_apply_list:
                intent.setClass(this, ApplyListActivity.class);
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