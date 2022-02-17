package com.luxc.moneymanager.activity.manager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyUserManagerActivity extends BaseActivity {
    public static final int ADD_NEW_USER_FLAG = 1000;

    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    @BindView(R.id.add)
    TextView tvAdd;

    private UserListAdapter userListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_user_manager_layout;
    }

    @Override
    protected void initView() {
        userListAdapter = new UserListAdapter();
        rvUser.setAdapter(userListAdapter);
        int userType = (int) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserType", 0);
        tvAdd.setVisibility(userType != 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initData() {
        Long familyId = (Long) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserFamilyId", 0L);
        userListAdapter.setNewInstance(DaoUtils.queryByFamilyId(familyId));
    }

    @OnClick(R.id.add)
    public void onClick() {
        Intent intent = new Intent(this, AddNewUserAccountActivity.class);
        startActivityForResult(intent, ADD_NEW_USER_FLAG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_NEW_USER_FLAG) {
                initData();
            }
        }
    }
}