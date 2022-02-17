package com.luxc.moneymanager.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.entity.FamilyBean;
import com.luxc.moneymanager.entity.IncomePayRecordBean;

public class IncomePayAdapter extends BaseQuickAdapter<IncomePayRecordBean, BaseViewHolder> {
    public IncomePayAdapter() {
        super(R.layout.item_income_pay_record_list_layout);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, IncomePayRecordBean incomePayRecordBean) {
        baseViewHolder.setText(R.id.tv_user_name, incomePayRecordBean.getUser());
        baseViewHolder.setText(R.id.tv_type, (incomePayRecordBean.getType() == 1)?"收入":"支出");
        baseViewHolder.setText(R.id.tv_money, incomePayRecordBean.getMoney());
        baseViewHolder.setTextColor(R.id.tv_money, incomePayRecordBean.getType() == 1?Color.RED:Color.GREEN);
    }
}
