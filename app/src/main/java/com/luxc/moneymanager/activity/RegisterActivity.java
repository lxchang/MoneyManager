package com.luxc.moneymanager.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.luxc.moneymanager.R;
import com.luxc.moneymanager.application.MyApp;
import com.luxc.moneymanager.base.BaseActivity;
import com.luxc.moneymanager.entity.UserBean;
import com.luxc.moneymanager.utils.DaoUtils;
import com.luxc.moneymanager.utils.DateTimeHelper;
import com.luxc.moneymanager.utils.ToastUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPassword;
    @BindView(R.id.et_sure_pwd)
    EditText etSurePassword;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;

    @BindView(R.id.main_title)
    TextView mainTitle;

    private String account;
    private String pwd;
    private String surePwd;
    private String name;
    private String sex;
    private TimePickerView mDatePickerView;
    private String age;
    private String birthday;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mainTitle.setText("注册");
        initDateTimePicker();
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化开始日期选择器控件
     */
    private void initDateTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        //设置最小日期和最大日期
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(DateTimeHelper.parseStringToDate("1930-01-01"));//设置为2006年4月28日

        Calendar endDate = Calendar.getInstance();//最大日期是今天

        //时间选择器
        mDatePickerView = new TimePickerBuilder(RegisterActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvBirthday.setText(DateTimeHelper.formatToString(date, "yyyy-MM-dd"));
                birthday = DateTimeHelper.formatToString(date, "yyyy-MM-dd");
            }
        })
                .setDecorView((RelativeLayout) findViewById(R.id.activity_rootview))//必须是RelativeLayout，不设置setDecorView的话，底部虚拟导航栏会显示在弹出的选择器区域
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTitleText("收支日期")//标题文字
                .setTitleSize(16)//标题文字大小
                .setTitleColor(getResources().getColor(R.color.color_333))//标题文字颜色
                .setCancelText("取消")//取消按钮文字
                .setCancelColor(getResources().getColor(R.color.color_9c9c9c))//取消按钮文字颜色
                .setSubmitText("确定")//确认按钮文字
                .setSubmitColor(getResources().getColor(R.color.blue))//确定按钮文字颜色
                .setContentTextSize(16)//滚轮文字大小
                .setTextColorCenter(getResources().getColor(R.color.black))//设置选中文本的颜色值
                .setLineSpacingMultiplier(1.8f)//行间距
                .setDividerColor(getResources().getColor(R.color.color_DBDBDB))//设置分割线的颜色
                .setRangDate(startDate, endDate)//设置最小和最大日期
                .setDate(selectedDate)//设置选中的日期
                .build();
    }


    @OnClick({R.id.ll_back, R.id.btn_register,R.id.tv_birthday})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_register:
                if (checkInfo()) {
                    register();
                }
                break;
            case R.id.tv_birthday:
                mDatePickerView.show();
                break;
        }
    }


    private boolean checkInfo() {
        account = etAccount.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        surePwd = etSurePassword.getText().toString().trim();
        name = etName.getText().toString().trim();
        sex = etSex.getText().toString().trim();
        age = etAge.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(surePwd)) {
            ToastUtils.showShort("请再次输入密码");
            return false;
        }
        if (!pwd.equals(surePwd)) {
            ToastUtils.showShort("两次输入的密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.showShort("请输入性别");
            return false;
        }
        return true;
    }

    private void register() {
        if (account.equals("admin")) {
            ToastUtils.showShort("账号已存在");
            return;
        } else {
            List<UserBean> userBeans = DaoUtils.queryByPhone(account);
            if (userBeans != null && userBeans.size() > 0) {
                ToastUtils.showShort("账号已存在");
                return;
            }
            UserBean userBean = new UserBean();
            userBean.setPhoneNum(account);
            userBean.setPassword(pwd);
            userBean.setName(name);
            userBean.setSex(sex);
            userBean.setAge(age);
            userBean.setBirthday(birthday);
            MyApp.getInstance().getDaoSession().getUserBeanDao().insert(userBean);

            ToastUtils.showShort("注册成功");
            finish();
        }
    }

}
