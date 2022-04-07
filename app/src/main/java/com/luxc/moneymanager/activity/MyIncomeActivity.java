package com.luxc.moneymanager.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddNewUserAccountActivity;
import com.luxc.moneymanager.adapter.IncomePayAdapter;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.IncomePayRecordBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

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

        incomePayAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AbstractCommonDialog commonDialog = new AbstractCommonDialog(MyIncomeActivity.this) {
                    @Override
                    public void sureClick() {
                        MyApp.getInstance().getDaoSession().getIncomePayRecordBeanDao().delete(incomePayAdapter.getData().get(position));
                        ToastUtils.showShort("删除成功");
                        initData();
                    }
                };
                commonDialog.setText("提示","确认删除该条记录");
                commonDialog.showDialog();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        Long userId = (Long) SharedPreferenceUtils.get(MyIncomeActivity.this,"currentUserId",0L);
        List<IncomePayRecordBean> list = DaoUtils.queryRecordByUserId(userId);
        if (list==null || list.size()==0){
            incomePayAdapter.setNewInstance(null);
            incomePayAdapter.setEmptyView(R.layout.empty_layout);
        }else{
            incomePayAdapter.setNewInstance(list);

        }
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
