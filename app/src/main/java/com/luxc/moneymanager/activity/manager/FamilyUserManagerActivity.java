package com.luxc.moneymanager.activity.manager;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.OtherUserInfoActivity;
import com.luxc.moneymanager.activity.UserListActivity;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyUserManagerActivity extends BaseActivity {
    public static final int ADD_NEW_USER_FLAG = 1000;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    @BindView(R.id.ll_right)
    LinearLayout llRight;

    private UserListAdapter userListAdapter;
    private Long familyId;
    private String familyName;
    private Long userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_family_user_manager_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("家庭成员");
        userListAdapter = new UserListAdapter();
        rvUser.setAdapter(userListAdapter);
        int userType = (int) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserType", 2);
        llRight.setVisibility(userType == 1 ? View.VISIBLE : View.GONE);

        if (userType==1){
            userListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    UserBean userBean = userListAdapter.getData().get(position);
                    AbstractCommonDialog commonDialog = new AbstractCommonDialog(FamilyUserManagerActivity.this) {
                        @Override
                        public void sureClick() {
                            userBean.setFamilyID(null);
                            userBean.setFamilyName("");
                            MyApp.getInstance().getDaoSession().getUserBeanDao().update(userBean);
                            ToastUtils.showShort("移除成功");
                            initData();
                        }
                    };
                    commonDialog.setText("删除","确认移除该成员");

                    if (userBean.getId().equals(userId)){
                        ToastUtils.showShort("不能删除自己");
                    }else{
                        commonDialog.showDialog();
                    }

                    return false;
                }
            });
        }

        userListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserBean userBean = userListAdapter.getData().get(position);
                Intent intent = new Intent(FamilyUserManagerActivity.this, OtherUserInfoActivity.class);
                intent.putExtra("data",userBean);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {
        userId = (Long) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserId", 0L);
        familyId = (Long) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserFamilyId", 0L);
        familyName = (String) SharedPreferenceUtils.get(FamilyUserManagerActivity.this, "currentUserFamilyName", "");
        List<UserBean> list = DaoUtils.queryByFamilyId(familyId);
        if (list==null || list.size()==0){
            userListAdapter.setNewInstance(null);
            userListAdapter.setEmptyView(R.layout.empty_layout);
        }else {
            userListAdapter.setNewInstance(list);
        }
    }

    @OnClick({R.id.ll_right, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                Intent intent = new Intent(this, UserListActivity.class);
                intent.putExtra("familyId", familyId);
                intent.putExtra("familyName", familyName);
                startActivityForResult(intent, ADD_NEW_USER_FLAG);
                break;
        }
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
