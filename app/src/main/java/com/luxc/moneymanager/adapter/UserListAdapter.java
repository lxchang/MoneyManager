package com.luxc.moneymanager.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.entity.UserBean;

public class UserListAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    public UserListAdapter() {
        super(R.layout.item_user_list_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UserBean userBean) {
        baseViewHolder.setText(R.id.tv_name,userBean.getName());
    }
}
