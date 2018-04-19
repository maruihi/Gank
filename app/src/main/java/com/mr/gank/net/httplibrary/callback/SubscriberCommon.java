package com.mr.gank.net.httplibrary.callback;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mr.gank.AppManager;
import com.mr.gank.core.BaseActivity;
import com.mr.gank.net.httplibrary.exception.LoginException;
import com.mr.gank.net.httplibrary.exception.RequestErrorException;
import com.mr.gank.ui.me.LoginActivity;
import com.mr.gank.utils.DeviceUtil;
import com.mr.gank.utils.MLog;
import com.mr.gank.utils.ToastUtil;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import rx.Subscriber;

public abstract class SubscriberCommon<T> extends Subscriber<T> {
    public boolean showErrorMessage = true;
    private Context context;

    public SubscriberCommon(Context context) {
        this.context = context;
    }

    public SubscriberCommon(Context context, boolean showErrorMessage) {
        this.context = context;
        this.showErrorMessage = showErrorMessage;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //    网络请求成功
    @Override
    public void onCompleted() {
        unsubscribe();
        onEnd(this);
    }

    //    网络请求失败
    @Override
    public void onError(Throwable e) {
        if (showErrorMessage) {
            showErrorMessage(e);
        }
        e.printStackTrace();
        MLog.e("数据加载失败");
        unsubscribe();
        onEnd(this);
    }

    protected void onEnd(Subscriber subscriber) {

    }


    public void showErrorMessage(Throwable e) {
        if (!DeviceUtil.deviceConnect(context)) {
            Toast.makeText(context, "当前无网络连接，请先设置网络!", Toast.LENGTH_SHORT).show();
        }

       if (e instanceof LoginException) {
            //未登录跳转到登录页面
            toReLogin();

        } else if (e instanceof ConnectTimeoutException || e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时!", Toast.LENGTH_SHORT).show();
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(context, "无法请求到服务器!", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            Toast.makeText(context, "请求服务器出错!", Toast.LENGTH_SHORT).show();
        }else if(e instanceof RequestErrorException){
           MLog.e(((RequestErrorException) e).getErrorCode()+e.getMessage());
           ToastUtil.showMessage(context,"服务器异常或服务器未知错误");
       }else {
           MLog.e("网络请求错误未知！");
       }
    }

    /**
     * To login.
     */
    private void toReLogin() {
        BaseActivity cutTopActivity = (BaseActivity) AppManager.getInstance().getCurrentActivity();
        if (cutTopActivity == null || cutTopActivity instanceof LoginActivity) {
            MLog.i("toReLogin:%s", "已经在登录界面,不进行跳转了");
            return;
        }
        Intent intent = new Intent(context, LoginActivity.class);
        //intent.putExtra(BaseActivity.KEY_ERROR_MESSAGE, message); //后续扩展用
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).overridePendingTransitionEnter();
        }
    }
}
