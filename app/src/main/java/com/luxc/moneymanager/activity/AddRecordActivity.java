package com.luxc.moneymanager.activity;

import android.view.View;
import android.widget.EditText;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.IncomePayRecordBean;

import butterknife.BindView;
import butterknife.OnClick;

public class AddRecordActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_date)
    EditText etDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_record_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_add)
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_add:
                IncomePayRecordBean incomePayRecordBean = new IncomePayRecordBean();
                incomePayRecordBean.setUser("小明爸");
                incomePayRecordBean.setUserId(1L);
                incomePayRecordBean.setType(0);
                incomePayRecordBean.setMoney("5000");
                incomePayRecordBean.setTime("2022-2-21");
                MyApp.getInstance().getDaoSession().getIncomePayRecordBeanDao().insert(incomePayRecordBean);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
