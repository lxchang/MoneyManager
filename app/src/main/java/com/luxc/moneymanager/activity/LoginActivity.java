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
import com.luxc.moneymanager.utils.LogUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import org.greenrobot.greendao.query.QueryBuilder;

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
    @BindView(R.id.main_title)
    TextView mainTitle;

    private String account;
    private String password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("登录");

    }

    @Override
    protected void initData() {
        List<UserBean> userBeans = DaoUtils.queryAllUser();
        LogUtils.e(userBeans.toString());
    }

    @OnClick({R.id.ll_back,R.id.tv_forget_pwd, R.id.btn_login,R.id.tv_to_register})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
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
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserBean> userBeans = DaoUtils.queryByPhone(account);
                if (userBeans != null && userBeans.size() > 0) {
                    int userType = userBeans.get(0).getUserType();
                    Long userId = userBeans.get(0).getId();
                    Long familyID = userBeans.get(0).getFamilyID();
                    String familyName = userBeans.get(0).getFamilyName();
                    String pwd = userBeans.get(0).getPassword();
                    if (!password.equals(pwd)){
                        ToastUtils.showShort("您输入的密码不正确,请重新输入");
                        etPassword.setText("");
                        return;
                    }
                    login(account,pwd,userId,userType,familyID,familyName);
                } else {
                    if (account.equals("admin")) {
                        UserBean userBean = new UserBean();
                        userBean.setName("admin");
                        userBean.setPhoneNum("admin");
                        userBean.setPassword("123");
                        userBean.setId(0L);
                        userBean.setFamilyID(0L);
                        userBean.setUserType(0);
                        MyApp.getInstance().getDaoSession().insert(userBean);
                        login("admin","123",0L,0,-1L,"");
                    } else {
                        Toast.makeText(LoginActivity.this, "没有该用户", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    private void login(String account,String pwd,Long userId,int userType,Long familyID,String familyName){
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
