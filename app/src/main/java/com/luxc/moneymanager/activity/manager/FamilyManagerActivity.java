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
import com.luxc.moneymanager.adapter.FamilyListAdapter;
import com.luxc.moneymanager.adapter.UserListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.FamilyBeanDao;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyManagerActivity extends BaseActivity {
    public static final int ADD_NEW_FAMILY_FLAG = 1001;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    private FamilyListAdapter familyListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_manager_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("家庭管理");

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
                startActivityForResult(intent,ADD_NEW_FAMILY_FLAG);

            }
        });
        familyListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                FamilyBean familyBean = ((FamilyListAdapter) adapter).getData().get(position);

                AbstractCommonDialog commonDialog = new AbstractCommonDialog(FamilyManagerActivity.this) {
                    @Override
                    public void sureClick() {
                        List<UserBean> userBeans = DaoUtils.queryByFamilyId(familyBean.getFamilyId());
                        for (UserBean userBean : userBeans) {
                            userBean.setFamilyID(-1L);
                            userBean.setFamilyName("");
                            MyApp.getInstance().getDaoSession().getUserBeanDao().update(userBean);
                        }

                        MyApp.getInstance().getDaoSession().getFamilyBeanDao().deleteByKey(familyBean.getFamilyId());
                        Toast.makeText(FamilyManagerActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        initData();
                    }

                };
                commonDialog.setText("确认删除该家庭？","");
                commonDialog.showDialog();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        List<FamilyBean> familyBeans = DaoUtils.queryAllFamily();
        if (familyBeans != null && familyBeans.size() > 0) {
            llRight.setVisibility(View.GONE);
        } else {
            llRight.setVisibility(View.VISIBLE);
        }
        familyListAdapter.setNewInstance(DaoUtils.queryAllFamily());
    }

    @OnClick({R.id.ll_right,R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_right:
                Intent intent = new Intent(this, AddFamilyActivity.class);
                startActivityForResult(intent, ADD_NEW_FAMILY_FLAG);
                break;
        }
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
