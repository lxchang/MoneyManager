package com.luxc.moneymanager.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddNewUserAccountActivity;
import com.luxc.moneymanager.adapter.IncomePayAdapter;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyIncomeActivity extends BaseActivity {
    public static final int ADD_NEW_USER_FLAG = 1000;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private IncomePayAdapter incomePayAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_income_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("收支记录");
        llRight.setVisibility(View.VISIBLE);
        incomePayAdapter = new IncomePayAdapter();
        rvUser.setAdapter(incomePayAdapter);
    }

    @Override
    protected void initData() {
        Long userId = (Long) SharedPreferenceUtils.get(MyIncomeActivity.this,"currentUserId",0L);
        incomePayAdapter.setNewInstance(DaoUtils.queryRecordByUserId(userId));
    }

    @OnClick({R.id.ll_right,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                Intent intent = new Intent(this, AddRecordActivity.class);
                startActivityForResult(intent,ADD_NEW_USER_FLAG);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==ADD_NEW_USER_FLAG){
                initData();
            }
        }
    }
}
