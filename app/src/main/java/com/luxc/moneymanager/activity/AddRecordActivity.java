package com.luxc.moneymanager.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.JsonObject;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.IncomePayRecordBean;
import com.luxc.moneymanager.entity.TypeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AddRecordActivity extends BaseActivity {
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_date)
    EditText etDate;
    //收支类型选择器
    private OptionsPickerView mTypePickerView;
    private ArrayList<TypeBean> mTypeList;
    private ArrayList<String> mTypeNameList;
    private String typeData = "{\"data\":[{\"id\":\"1\",\"typeName\":\"收入\"},{\"id\":\"0\",\"typeName\":\"支出\"}]}";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_record_layout;
    }

    @Override
    protected void initView() {
        mainTitle.setText("添加记录");
    }

    @Override
    protected void initData() {
        mTypeList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(typeData);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonBean = data.getJSONObject(i);
                TypeBean typeBean = new TypeBean();
                typeBean.setId(jsonBean.getString("id"));
                typeBean.setTypeName(jsonBean.getString("typeName"));
                mTypeList.add(typeBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mTypeNameList = new ArrayList<>();
        for (TypeBean typeBean : mTypeList) {
            mTypeNameList.add(typeBean.getTypeName());
        }

        initTypeOptionPicker();
    }

    //初始化爱好选择器
    private void initTypeOptionPicker() {

        mTypePickerView = new OptionsPickerBuilder(AddRecordActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mTypeNameList.get(options1);
                tvType.setText(tx);
            }
        })
                .setDecorView((RelativeLayout) findViewById(R.id.activity_rootview))//必须是RelativeLayout，不设置setDecorView的话，底部虚拟导航栏会显示在弹出的选择器区域
                .setTitleText("选择收支类型")//标题文字
                .setTitleSize(20)//标题文字大小
                .setTitleColor(getResources().getColor(R.color.color_333))//标题文字颜色
                .setCancelText("取消")//取消按钮文字
                .setCancelColor(getResources().getColor(R.color.color_9c9c9c))//取消按钮文字颜色
                .setSubmitText("确定")//确认按钮文字
                .setSubmitColor(getResources().getColor(R.color.blue))//确定按钮文字颜色
                .setContentTextSize(20)//滚轮文字大小
                .setTextColorCenter(getResources().getColor(R.color.black))//设置选中文本的颜色值
                .setLineSpacingMultiplier(1.8f)//行间距
                .setDividerColor(getResources().getColor(R.color.color_DBDBDB))//设置分割线的颜色
                .setSelectOptions(0)//设置选择的值
                .build();

        mTypePickerView.setPicker(mTypeNameList);//添加数据
    }


    @OnClick({R.id.btn_add, R.id.ll_back,R.id.tv_type})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_type:
                mTypePickerView.show();
                break;
            case R.id.btn_add:
                IncomePayRecordBean incomePayRecordBean = new IncomePayRecordBean();
                incomePayRecordBean.setUser("小明爸");
                incomePayRecordBean.setUserId(1L);
                incomePayRecordBean.setType(0);
                incomePayRecordBean.setMoney("5000");
                incomePayRecordBean.setTime("2022-2-21");
                MyApp.getInstance().getDaoSession().getIncomePayRecordBeanDao().insert(incomePayRecordBean);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}
