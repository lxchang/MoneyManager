package com.luxc.moneymanager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPassword;
    @BindView(R.id.et_sure_pwd)
    EditText etSurePassword;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    EditText etSex;

    @BindView(R.id.main_title)
    TextView mainTitle;

    private String account;
    private String pwd;
    private String surePwd;
    private String name;
    private String sex;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mainTitle.setText("注册");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back, R.id.btn_register})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_register:
                if (checkInfo()) {
                    register();
                }

                break;
        }
    }


    private boolean checkInfo() {
        account = etAccount.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        surePwd = etSurePassword.getText().toString().trim();
        name = etName.getText().toString().trim();
        sex = etSex.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(surePwd)) {
            ToastUtils.showShort("请再次输入密码");
            return false;
        }
        if (!pwd.equals(surePwd)) {
            ToastUtils.showShort("两次输入的密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.showShort("请输入性别");
            return false;
        }
        return true;
    }

    private void register() {
        if (account.equals("admin")) {
            ToastUtils.showShort("账号已存在");
            finish();
            return;
        } else {
            UserBean userBean = new UserBean();
            userBean.setPhoneNum(account);
            userBean.setPassword(pwd);
            userBean.setName(name);
            userBean.setSex(sex);
            MyApp.getInstance().getDaoSession().getUserBeanDao().insert(userBean);

            ToastUtils.showShort("注册成功");
            finish();
        }
    }

}
