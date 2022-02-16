package com.luxc.moneymanager.activity.manager;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.adapter.FamilyListAdapter;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyManagerActivity extends BaseActivity {
    public static final int ADD_NEW_FAMILY_FLAG = 1001;

    @BindView(R.id.add)
    TextView tvAdd;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private FamilyListAdapter familyListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_manager_layout;
    }

    @Override
    protected void initView() {
        familyListAdapter = new FamilyListAdapter();
        rvUser.setAdapter(familyListAdapter);
        familyListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int userType = (int) SharedPreferenceUtils.get(FamilyManagerActivity.this, "userType", 0);
                Long userId = (Long) SharedPreferenceUtils.get(FamilyManagerActivity.this, "currentUserId", 0L);
                FamilyBean familyBean = ((FamilyListAdapter) adapter).getData().get(position);
//                if (userType == 0 && !userId.equals(familyBean.getCreateUserId())) return;

                Intent intent = new Intent(FamilyManagerActivity.this, AddFamilyActivity.class);
                intent.putExtra("bean", familyBean);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        List<FamilyBean> familyBeans = DaoUtils.queryAllFamily();
        if (familyBeans != null && familyBeans.size() > 0) {
            tvAdd.setVisibility(View.GONE);
        } else {
            tvAdd.setVisibility(View.VISIBLE);
        }
        familyListAdapter.setNewInstance(DaoUtils.queryAllFamily());
    }

    @OnClick(R.id.add)
    public void onClick() {
        Intent intent = new Intent(this, AddFamilyActivity.class);
        startActivityForResult(intent, ADD_NEW_FAMILY_FLAG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_NEW_FAMILY_FLAG) {
                initData();
            }
        }
    }
}
