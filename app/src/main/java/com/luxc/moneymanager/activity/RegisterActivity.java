package com.luxc.moneymanager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPassword;
    @BindView(R.id.et_sure_pwd)
    EditText etSurePassword;

    @BindView(R.id.main_title)
    TextView mainTitle;

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

    @OnClick(R.id.ll_back)
    public void OnViewClick(View view){
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
