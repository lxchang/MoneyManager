package com.luxc.moneymanager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.activity.manager.AddFamilyActivity;
import com.luxc.moneymanager.activity.manager.FamilyManagerActivity;
import com.luxc.moneymanager.activity.manager.FamilyUserManagerActivity;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info_layout;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        String currentName = (String) SharedPreferenceUtils.get(UserInfoActivity.this,"currentUser","");
        List<UserBean> userBeans = DaoUtils.queryByName(currentName);
        if (userBeans!=null&& userBeans.size()>0){
            UserBean userBean = userBeans.get(0);
            tvName.setText(userBean.getName());
            tvSex.setText(userBean.getSex());
            tvAge.setText(userBean.getAge());
            tvBirthday.setText(userBean.getBirthday());
            tvFamilyName.setText(userBean.getFamilyName());
        }
    }

    @OnClick(R.id.tv_family_name)
    public void onViewClick(){
        if (TextUtils.isEmpty(tvFamilyName.getText().toString())) {
            startActivityForResult(new Intent(UserInfoActivity.this, AddFamilyActivity.class), 1002);
        }else{
            startActivity(new Intent(UserInfoActivity.this, FamilyUserManagerActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1002){
                String currentName = (String) SharedPreferenceUtils.get(UserInfoActivity.this,"currentUser","");
                List<UserBean> userBeans = DaoUtils.queryByName(currentName);
                if (userBeans!=null&& userBeans.size()>0){
                    UserBean userBean = userBeans.get(0);
                    tvFamilyName.setText(userBean.getFamilyName());
                }
            }
        }
    }
}
