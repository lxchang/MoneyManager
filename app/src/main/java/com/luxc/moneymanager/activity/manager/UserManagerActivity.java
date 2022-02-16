package com.luxc.moneymanager.activity.manager;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.UserBeanDao;

import java.util.List;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.utils.DaoUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UserManagerActivity extends BaseActivity {
    public static final int ADD_NEW_USER_FLAG = 1000;

    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private UserListAdapter userListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_manager_layout;
    }

    @Override
    protected void initView() {
        userListAdapter = new UserListAdapter();
        rvUser.setAdapter(userListAdapter);
    }

    @Override
    protected void initData() {
        Log.e("luxc",DaoUtils.queryAllUser().toString());
        userListAdapter.setNewInstance(DaoUtils.queryAllUser());
    }

    @OnClick(R.id.add)
    public void onClick() {
        Intent intent = new Intent(this,AddNewUserAccountActivity.class);
        startActivityForResult(intent,ADD_NEW_USER_FLAG);
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
