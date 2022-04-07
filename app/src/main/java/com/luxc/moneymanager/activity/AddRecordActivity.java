package com.luxc.moneymanager.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.JsonObject;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.IncomePayRecordBean;
import com.luxc.moneymanager.entity.TypeBean;
import com.luxc.moneymanager.utils.DateTimeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    @BindView(R.id.tv_date)
    TextView startdateTv;
    //收支类型选择器
    private OptionsPickerView mTypePickerView;
    private ArrayList<TypeBean> mTypeList;
    private ArrayList<String> mTypeNameList;
    private String typeData = "{\"data\":[{\"id\":\"1\",\"typeName\":\"收入\"},{\"id\":\"0\",\"typeName\":\"支出\"}]}";
    private TimePickerView mStartDatePickerView;

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
        initStartTimePicker();
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

    /**
     * 初始化开始日期选择器控件
     */
    private void initStartTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        //设置最小日期和最大日期
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(DateTimeHelper.parseStringToDate("1970-01-01"));//设置为2006年4月28日

        Calendar endDate = Calendar.getInstance();//最大日期是今天

        //时间选择器
        mStartDatePickerView = new TimePickerBuilder(AddRecordActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                startdateTv.setText(DateTimeHelper.formatToString(date, "yyyy-MM-dd"));
            }
        })
                .setDecorView((RelativeLayout) findViewById(R.id.activity_rootview))//必须是RelativeLayout，不设置setDecorView的话，底部虚拟导航栏会显示在弹出的选择器区域
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTitleText("收支日期")//标题文字
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
                .setRangDate(startDate, endDate)//设置最小和最大日期
                .setDate(selectedDate)//设置选中的日期
                .build();
    }


    @OnClick({R.id.btn_add, R.id.ll_back, R.id.tv_type,R.id.tv_date})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_type:
                mTypePickerView.show();
                break;
            case R.id.tv_date:
                mStartDatePickerView.show();
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
