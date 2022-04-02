package com.luxc.moneymanager.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luxc.moneymanager.R;

import androidx.core.content.ContextCompat;


/**
 * 通用dialog 弹窗
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/1/3.
 */

public abstract class AbstractCommonDialog {
    protected Dialog dialog;
    private TextView title, content;
    private TextView sure;
    private TextView cancle;
    private Activity activity;

    public AbstractCommonDialog(Activity activity) {
        this.activity = activity;
        if (dialog == null) {
            getDialog(activity);
        }
    }


    /**
     * 初始化  dialog
     *
     * @param activity 上下文
     * @return dialog
     */
    private Dialog getDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.common_dialog, null);
        dialog = new Dialog(activity, R.style.prompt_progress_dailog_dimenabled);
        dialog.setContentView(view, new LinearLayout.LayoutParams
                (dip2px(activity, 280), ViewGroup.LayoutParams.WRAP_CONTENT));
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        sure = view.findViewById(R.id.sure);
        cancle = view.findViewById(R.id.cancle);

        Window dialogWindow = dialog.getWindow();
        android.view.WindowManager.LayoutParams lp = dialogWindow
                .getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sureClick();
                dialog.dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelClick();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    //                    dialog.dismiss();
                    //                    activity.finish();
                    return true;
                }
                return false;
            }
        });

        return dialog;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 确认
     */
    public abstract void sureClick();

    public abstract void cancelClick();

    /**
     * 设置文本
     */
    public void setText(String strTitle,String strContent) {
        title.setText(strTitle);
        if (TextUtils.isEmpty(strContent))
            content.setVisibility(View.GONE);
        content.setText(strContent);
    }

    /**
     * 设置文本
     *
     * @param strTitle   dialog 标题
     * @param strContent dialog 提示内容  为 null 时 不显示提示内容  显示输入框
     * @param strSure    确定按钮文字
     * @param strCancel  取消按钮文字
     */
    public void setText(String strTitle, String strContent, String strSure, String strCancel) {
        title.setText(strTitle);
        sure.setText(strSure);
        cancle.setText(strCancel);

        content.setText(strContent);
    }


    /**
     * 设置 dialog 弹窗 确定按钮 和 取消按钮   颜色
     *
     * @param sureColor   确定按钮颜色
     * @param cancelColor 取消按钮颜色
     */
    public void setButtonColor(int sureColor, int cancelColor) {
        sure.setTextColor(sureColor);
        cancle.setTextColor(cancelColor);
    }

    /**
     * 显示dialog 弹窗
     */
    public void showDialog() {
        dialog.show();
    }

    /**
     * 隐藏dialog 弹窗
     */
    public void dismissDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}