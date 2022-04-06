package com.luxc.moneymanager.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.adapter.ApplyListAdapter;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.dialog.AbstractCommonDialog;
import com.luxc.moneymanager.entity.ApplyBean;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.greendao.db.ApplyBeanDao;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.SharedPreferenceUtils;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ApplyBean applyBean = ((ApplyListAdapter) adapter).getData().get(position);
                AbstractCommonDialog commonDialog = new AbstractCommonDialog(ApplyListActivity.this) {
                    @Override
                    public void sureClick() {
                        Long applyId = applyBean.getApplyId();
                        Long applyUserId = applyBean.getApplyUserId();
                        UserBean userBean = DaoUtils.queryUserById(applyUserId);
                        List<ApplyBean> applyBeans = DaoUtils.queryApplyBean(applyId);
                        if (applyBeans != null && applyBeans.size() > 0) {
                            ApplyBean applyBean1 = applyBeans.get(0);
                            applyBean1.setStatus(1);
                            MyApp.getInstance().getDaoSession().getApplyBeanDao().update(applyBean1);
                            if (userBean != null) {
                                userBean.setUserType(1);
                                MyApp.getInstance().getDaoSession().getUserBeanDao().update(userBean);
                                SharedPreferenceUtils.put(ApplyListActivity.this, "currentUserType", 1);
                                ToastUtils.showShort("已通过申请");
                            }

                            initData();
                        }
                    }
                };
                commonDialog.setText("审核", "是否通过该申请？");
                commonDialog.showDialog();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        List<ApplyBean> list = MyApp.getInstance().getDaoSession().getApplyBeanDao().queryBuilder().where(ApplyBeanDao.Properties.Status.eq(0)).list();
        mAdapter.setNewInstance(list);
    }

    @OnClick(R.id.ll_back)
    public void onViewClick(){
        finish();
    }
}
