package com.luxc.moneymanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.btn_login)
    TextView btnLogin;

    private String account;
    private String password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_forget_pwd, R.id.btn_login,R.id.tv_to_register})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_pwd:
                break;
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                account = etAccount.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserBean> userBeans = DaoUtils.queryByName(account);
                if (userBeans != null && userBeans.size() > 0) {
                    int userType = userBeans.get(0).getUserType();
                    Long userId = userBeans.get(0).getId();
                    Long familyID = userBeans.get(0).getFamilyID();
                    String familyName = userBeans.get(0).getFamilyName();
                    login(account,userId,userType,familyID,familyName);
                } else {
                    if (account.equals("admin")) {
                        UserBean userBean = new UserBean();
                        userBean.setName("admin");
                        userBean.setPassword(password);
                        userBean.setId(0L);
                        userBean.setFamilyID(0L);
                        userBean.setUserType(0);
                        MyApp.getInstance().getDaoSession().insert(userBean);
                        login("admin",0L,0,0L,"");
                    } else {
                        Toast.makeText(LoginActivity.this, "没有该用户", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    private void login(String account,Long userId,int userType,Long familyID,String familyName){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                SharedPreferenceUtils.put(LoginActivity.this, "currentUser", account);
                SharedPreferenceUtils.put(LoginActivity.this, "currentUserId", userId);
                SharedPreferenceUtils.put(LoginActivity.this, "currentUserType", userType);
                if (familyID!=null) {
                    SharedPreferenceUtils.put(LoginActivity.this, "currentUserFamilyId", familyID);
                    SharedPreferenceUtils.put(LoginActivity.this, "currentUserFamilyName", familyName);
                }
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
