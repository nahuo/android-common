package com.nahuo.library.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.nahuo.library.R;

/**
 * 正在加载等待对话框
 */
public class LoadingDialog extends Dialog {

    private Context mContext;
    private ImageView imageView;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        InitDialog(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, R.style.LoadingDialog);
        InitDialog(context);
    }

    public void InitDialog(Context context) {
        mContext = context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.loading_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.loading_dialog);
        animation.setInterpolator(new LinearInterpolator());// 匀速旋转
        imageView = (ImageView) findViewById(R.id.loadingdialog_loadingicon);
        imageView.setAnimation(animation);
        animation.start();
    }

    public void setMessage(String message) {
        TextView tvMsg = (TextView) findViewById(R.id.loadingdialog_message);
        if (tvMsg != null) {
            tvMsg.setText(message);
        }
    }

    public void start() {
        start("正在加载数据，请稍候...");
    }

    public void start(String message) {
        try {
            setMessage(message);
            show();
        } catch (Exception e) {
            //activity != null && !activity.isFinshing
            e.printStackTrace();
        }
    }

    public void stop() {
        if (imageView != null && imageView.getAnimation() != null) {
            imageView.clearAnimation();
        }
        try {
            dismiss();
        } catch (Exception e) {
            //activity != null && !activity.isFinshing
            e.printStackTrace();
        }

    }

}
