package com.luxc.moneymanager.activity;

import androidx.recyclerview.widget.RecyclerView;

import com.luxc.moneymanager.R;
import com.luxc.moneymanager.adapter.ApplyListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.ApplyBean;

import java.util.List;

import butterknife.BindView;

public class ApplyListActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ApplyListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_list;
    }

    @Override
    protected void initView() {
        mAdapter = new ApplyListAdapter();
        rvList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        List<ApplyBean> list = MyApp.getInstance().getDaoSession().getApplyBeanDao().queryBuilder().list();
        mAdapter.setNewInstance(list);
    }
}
