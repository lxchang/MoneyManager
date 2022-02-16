package com.luxc.moneymanager.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.UserBean;

public class FamilyListAdapter extends BaseQuickAdapter<FamilyBean, BaseViewHolder> {
    public FamilyListAdapter() {
        super(R.layout.item_user_list_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, FamilyBean familyBean) {
        baseViewHolder.setText(R.id.tv_name,familyBean.getFamilyName());
    }
}
