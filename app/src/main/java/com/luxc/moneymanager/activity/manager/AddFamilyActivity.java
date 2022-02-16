package com.luxc.moneymanager.activity.manager;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFamilyActivity extends BaseActivity {
    @BindView(R.id.et_family_name)
    EditText etFalimyName;

    private FamilyBean bean;
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_family_layout;
    }

    @Override
    protected void initView() {
        bean = (FamilyBean) getIntent().getSerializableExtra("bean");
        type = getIntent().getIntExtra("type", 0);

        if (type == 1)
            etFalimyName.setText(bean.getFamilyName());

    }

    @Override
    protected void initData() {
    }


    @OnClick(R.id.btn_add)
    public void addNewUser() {
        String familyName = etFalimyName.getText().toString().trim();

        if (TextUtils.isEmpty(familyName)) {
            Toast.makeText(this, "请输入家庭名称", Toast.LENGTH_SHORT).show();
            return;
        }

        if (type == 1) {
            bean.setFamilyName(familyName);
            MyApp.getInstance().getDaoSession().update(bean);

            String userName = (String) SharedPreferenceUtils.get(AddFamilyActivity.this, "currentUser", "");
            List<UserBean> userBeans = DaoUtils.queryByName(userName);
            if (userBeans != null && userBeans.size() > 0) {
                UserBean userBean = userBeans.get(0);
                userBean.setFamilyName(bean.getFamilyName());
                MyApp.getInstance().getDaoSession().update(userBean);
            }
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

        } else {
            String userName = (String) SharedPreferenceUtils.get(AddFamilyActivity.this, "currentUser", "");

            FamilyBean familyBean = new FamilyBean();

            familyBean.setFamilyName(familyName);
            familyBean.setCreater(userName);
            familyBean.setCreateUserId((Long) SharedPreferenceUtils.get(AddFamilyActivity.this, "currentUserId", 0L));

            MyApp.getInstance().getDaoSession().insert(familyBean);

            List<UserBean> userBeans = DaoUtils.queryByName(userName);
            if (userBeans != null && userBeans.size() > 0) {
                UserBean userBean = userBeans.get(0);
                userBean.setFamilyID(familyBean.getFamilyId());
                userBean.setFamilyName(familyBean.getFamilyName());
                MyApp.getInstance().getDaoSession().update(userBean);
            }
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }


        setResult(RESULT_OK);
        finish();
    }

}