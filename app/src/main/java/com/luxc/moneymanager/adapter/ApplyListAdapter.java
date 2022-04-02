package com.luxc.moneymanager.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.entity.ApplyBean;
import com.luxc.moneymanager.entity.FamilyBean;

public class ApplyListAdapter extends BaseQuickAdapter<ApplyBean, BaseViewHolder> {
    public ApplyListAdapter() {
        super(R.layout.item_apply_list_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ApplyBean familyBean) {
        baseViewHolder.setText(R.id.tv_title,familyBean.getApplyUserName()+"申请成为家庭管理员");
    }
}
