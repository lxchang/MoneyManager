package com.luxc.moneymanager.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddFamilyActivity;
import com.luxc.moneymanager.activity.manager.FamilyUserManagerActivity;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.ApplyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OtherUserInfoActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.main_title)
    TextView mainTitle;

    private UserBean userBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("个人信息");
    }

    @Override
    protected void initData() {
        userBean = (UserBean) getIntent().getSerializableExtra("data");

        if (userBean != null ) {
            tvName.setText(userBean.getName());
            tvSex.setText(userBean.getSex());
            tvAge.setText(userBean.getAge());
            tvBirthday.setText(userBean.getBirthday());
            tvFamilyName.setText(userBean.getFamilyName());
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ll_back, R.id.sure})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
