package com.luxc.moneymanager.activity.manager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewUserAccountActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_birthday)
    EditText etBirthday;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_user_account_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_add)
    public void addNewUser() {
        UserBean userBean = new UserBean();
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String sex = etSex.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "请输入年龄", Toast.LENGTH_SHORT).show();
            return;
        }
        userBean.setName(name);
        userBean.setUserType(1);
        userBean.setAge(age);
        userBean.setSex(sex);
        userBean.setBirthday(birthday);
        int userType = (int) SharedPreferenceUtils.get(AddNewUserAccountActivity.this, "currentUserType", 0);
        if (userType == 0) {
            userBean.setUserType(1);
        } else if (userType == 1) {
            userBean.setUserType(2);
        }

        MyApp.getInstance().getDaoSession().insert(userBean);

        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);
        finish();
    }

}
