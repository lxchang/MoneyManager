package com.luxc.moneymanager.activity.manager;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luxc.moneymanager.activity.OtherUserInfoActivity;
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
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private UserListAdapter userListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_manager_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("用户管理");
        llRight.setVisibility(View.GONE);
        userListAdapter = new UserListAdapter();
        rvUser.setAdapter(userListAdapter);

        userListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserBean userBean = userListAdapter.getData().get(position);
                Intent intent = new Intent(UserManagerActivity.this, OtherUserInfoActivity.class);
                intent.putExtra("data",userBean);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        Log.e("luxc",DaoUtils.queryAllUser().toString());
        userListAdapter.setNewInstance(DaoUtils.queryAllUser());
    }

    @OnClick({R.id.ll_right,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                Intent intent = new Intent(this,AddNewUserAccountActivity.class);
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
