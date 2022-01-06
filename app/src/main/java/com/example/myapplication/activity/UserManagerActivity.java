package com.example.myapplication.activity;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserListAdapter;
import com.example.myapplication.application.MyApp;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.entity.UserBean;
import com.example.myapplication.entity.UserManager;
import com.example.myapplication.greendao.db.UserBeanDao;
import com.example.myapplication.greendao.db.UserManagerDao;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserManagerActivity extends BaseActivity {
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
        UserBeanDao userBeanDao = MyApp.getInstance().getDaoSession().getUserBeanDao();
        List<UserBean> list = userBeanDao.queryBuilder().list();
        Log.e("luxc ",list.toString());
        userListAdapter.setNewInstance(list);
    }
    @OnClick(R.id.add)
    public void onClick(){
        Toast.makeText(this,"跳转到新增页面",Toast.LENGTH_LONG).show();
        UserBean userBean = new UserBean();
        userBean.setName("小芳");
        userBean.setAge(17);
        userBean.setSex("女");
        userBean.setBirthday("19930101");
        userBean.setUserType(2);
        MyApp.getInstance().getDaoSession().insert(userBean);

        initData();
    }
}
