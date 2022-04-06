package com.luxc.moneymanager.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddNewUserAccountActivity;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserListActivity extends BaseActivity {
    public static final int ADD_NEW_USER_FLAG = 1000;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private UserListAdapter userListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list_layout;
    }

    @Override
    protected void initView() {
        Long familyId = getIntent().getLongExtra("familyId",-1L);
        String familyName = getIntent().getStringExtra("familyName");
        mainTitle.setText("邀请加入家庭");
        llRight.setVisibility(View.VISIBLE);
        userListAdapter = new UserListAdapter();
        rvUser.setAdapter(userListAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<UserBean> userBeans;
                if (!TextUtils.isEmpty(s)){
                    userBeans = DaoUtils.querySearchByName(s.toString());
                }else{
                    userBeans = DaoUtils.queryAllUser();
                }

                userListAdapter.setNewInstance(userBeans);
            }
        });

        userListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                UserBean userBean = userListAdapter.getData().get(position);
                AbstractCommonDialog commonDialog = new AbstractCommonDialog(UserListActivity.this) {
                    @Override
                    public void sureClick() {
                        userBean.setFamilyID(familyId);
                        userBean.setFamilyName(familyName);
                        MyApp.getInstance().getDaoSession().getUserBeanDao().update(userBean);
                        ToastUtils.showShort("邀请成功");
                    }
                };
                commonDialog.setText("提示","确认邀请加入");
                commonDialog.showDialog();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        userListAdapter.setNewInstance(DaoUtils.queryAllUser());
    }

    @OnClick({R.id.ll_right,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
//                Intent intent = new Intent(this, AddNewUserAccountActivity.class);
//                startActivityForResult(intent,ADD_NEW_USER_FLAG);
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
