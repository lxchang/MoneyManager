package com.luxc.moneymanager.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luxc.moneymanager.utils.KeyboardUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            View view=this.getCurrentFocus();
            if (view!=null&&view instanceof EditText){
                int[] l = {0, 0};
                view.getLocationInWindow(l);
                int left = l[0], top = l[1], bottom = top + view.getHeight(), right = left
                        + view.getWidth();
                if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                    // 触摸的是EditText控件，忽略它
                } else {
                    KeyboardUtil.closeKeyboard(this);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 获取布局
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

}
