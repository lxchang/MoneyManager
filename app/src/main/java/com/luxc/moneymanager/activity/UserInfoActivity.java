package com.luxc.moneymanager.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddFamilyActivity;
import com.luxc.moneymanager.activity.manager.FamilyManagerActivity;
import com.luxc.moneymanager.activity.manager.FamilyUserManagerActivity;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.ApplyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_family_name)
    TextView tvFamilyName;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.sure)
    TextView applyManager;

    private Long userId;
    private String userName;
    private int userType = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("个人信息");
        applyManager.setText("申请家庭管理员");
    }

    @Override
    protected void initData() {
        String currentName = (String) SharedPreferenceUtils.get(UserInfoActivity.this, "currentUser", "");
        List<UserBean> userBeans = DaoUtils.queryByPhone(currentName);
        if (userBeans != null && userBeans.size() > 0) {
            UserBean userBean = userBeans.get(0);
            userId = userBean.getId();
            userName = userBean.getName();
            userType = userBean.getUserType();

            tvName.setText(userName);
            tvSex.setText(userBean.getSex());
            tvAge.setText(userBean.getAge());
            tvBirthday.setText(userBean.getBirthday());
            tvFamilyName.setText(userBean.getFamilyName());
            if (userType == 1) {
                tvFamilyName.setHint("您还没有创建家庭，点击去创建～");
            }
            applyManager.setVisibility(userBean.getUserType() == 2 ? View.VISIBLE : View.GONE);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.ll_back, R.id.tv_family_name, R.id.sure})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_family_name:
                if (TextUtils.isEmpty(tvFamilyName.getText().toString())) {
                    if (userType == 1)
                        startActivityForResult(new Intent(UserInfoActivity.this, AddFamilyActivity.class), 1002);
                } else {
                    startActivity(new Intent(UserInfoActivity.this, FamilyUserManagerActivity.class));
                }
                break;
            case R.id.sure:
                AbstractCommonDialog commonDialog = new AbstractCommonDialog(UserInfoActivity.this) {
                    @Override
                    public void sureClick() {
                        applyToManager(userId, userName);
                    }
                };
                commonDialog.setText("申请为管理员", "确认申请为家庭管理员身份？");
                commonDialog.showDialog();
                break;
        }
    }

    private void applyToManager(Long userId, String userName) {
        ApplyBean applyBean = new ApplyBean();
        applyBean.setApplyUserId(userId);
        applyBean.setApplyUserName(userName);
        MyApp.getInstance().getDaoSession().getApplyBeanDao().insert(applyBean);
        ToastUtils.showShort("申请成功，请等待系统管理员审核");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1002) {
                String currentName = (String) SharedPreferenceUtils.get(UserInfoActivity.this, "currentUser", "");
                List<UserBean> userBeans = DaoUtils.queryByPhone(currentName);
                if (userBeans != null && userBeans.size() > 0) {
                    UserBean userBean = userBeans.get(0);
                    tvFamilyName.setText(userBean.getFamilyName());
                }
            }
        }
    }
}
