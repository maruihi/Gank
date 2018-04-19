package com.mr.gank.net.httplibrary.callback;

import android.content.Context;

import com.mr.gank.core.BaseActivity;


public abstract class SubscriberProgress<T> extends SubscriberCommon<T> {
    private String message;
    private Context context;

    public SubscriberProgress(Context context) {
        this(context, "请稍等...");
    }

    public SubscriberProgress(Context context, boolean showErrorMessage) {
        super(context, showErrorMessage);
        this.context = context;
    }

    public SubscriberProgress(Context context, String message) {
        super(context);
        this.context = context;
        this.message = message;
    }

    public SubscriberProgress(Context context, boolean showErrorMessage, String message) {
        super(context, showErrorMessage);
        this.context = context;
        this.message = message;
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * 显示Dialog
         */
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).showLoadingDialog(message);
        }
    }

    //网络请求成功
    @Override
    public void onCompleted() {
        super.onCompleted();
        onDismiss();
    }

    //网络请求失败
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        onDismiss();
    }

    private void onDismiss() {
        /**
         * 隐藏Dialog
         */
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).dismissLoadingDialog();
        }
    }
}
