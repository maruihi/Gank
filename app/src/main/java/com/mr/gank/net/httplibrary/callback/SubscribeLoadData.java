package com.mr.gank.net.httplibrary.callback;

import android.content.Context;


/**
 * 上传下载回调
 */
public abstract class SubscribeLoadData<T> extends SubscriberCommon<T> implements ProgressListener{
    private Context context;

    public SubscribeLoadData(Context context) {
        super(context,true);
        this.context = context;
    }

    public SubscribeLoadData(Context context, boolean showErrorMessage) {
        super(context,showErrorMessage);
        this.context = context;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
    }
}
