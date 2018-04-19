package com.mr.gank.core;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mr.gank.R;
import com.mr.gank.utils.InputMethodUtils;
import com.mr.gank.utils.LoadingDialogUtils;
import com.mr.gank.utils.ToastUtil;
import com.mr.gank.utils.helper.DebugHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * BaseActivity 封装
 * Author： by MR on 2017/3/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;
    private boolean onDestroy;
    protected boolean showStatusBar = true;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDestroy = false;
        mContext = this;

        initStatusBar();

        if (showStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.theme_color);// 通知栏所需颜色
        }

        onCreateBefore(savedInstanceState);
        if (getLayoutId() != 0) {// 设置布局,如果子类有返回布局的话
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        } else {
            //没有提供ViewId
            DebugHelper.throwIllegalState("没有提供正确的LayoutId");
            return;
        }

        initBase(savedInstanceState);
        //留给子类重写
        onCreateAfter(savedInstanceState);
    }

    /**
     * setContentView之前调用
     *
     * @param savedInstanceState
     */
    protected void onCreateBefore(Bundle savedInstanceState) {
    }
    /**
     * onCreateBefore之前调用
     */
    protected void initStatusBar() {
    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * setContentView之后调用
     *
     * @param savedInstanceState
     */
    protected abstract void onCreateAfter(Bundle savedInstanceState);

    @Override
    protected void onResume() {
        super.onResume();
        //页面统计
//        Tracker.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面统计
//        Tracker.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroy = true;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        inputMethodHide(this);
    }

    /**
     * 关闭Activity
     */
    protected void finishActivity() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 重写返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
        Log.i(getClass().getCanonicalName(), "onBackPressed");
    }

    /**
     * 隐藏输入法
     */
    public void inputMethodHide(Context context) {
        InputMethodUtils.hide(context);
    }

    public void showSuccessTip(int id) {
        ToastUtil.showMySuccessMessage(getApplicationContext(), id);
    }

    public void showFailTip(int id) {

        ToastUtil.showMyFailMessage(getApplicationContext(), id);
    }

    public void showSuccessTip(String str) {
        ToastUtil.showMySuccessMessage(getApplicationContext(), str);
    }

    public void showFailTip(String str) {
        ToastUtil.showMyFailMessage(getApplicationContext(), str);
    }

    protected void initBase(Bundle savedInstanceState) {
        //初始化EventBus
        if (userEventBus()) {
            EventBus.getDefault().register(this);
        }

        //初始化其他,后续按需求来
    }

    //如果要使用Eventbus,则重写返回true
    protected boolean userEventBus() {
        return false;
    }


    //兼容低版本的 判断Activity是否已经被关闭了
    public boolean isDestroyed() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return super.isDestroyed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return onDestroy;
    }

    //界面动画
    public void clearOverridePendingTransition() {
        overridePendingTransition(-1, -1);
    }

    public void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void overridePendingTransitionBack() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void overridePendingAlphaEnter() {
        overridePendingTransition(R.anim.fade_in, -1);
    }

    public void overridePendingAlphaBack() {
        overridePendingTransition(-1, R.anim.fade_out);
    }

    public void overridePendingTransitionBottomEnter() {
        overridePendingTransition(R.anim.push_bottom_in, R.anim.push_static);
    }

    public void overridePendingTransitionBottomBack() {
        overridePendingTransition(R.anim.push_static, R.anim.push_bottom_out);
    }


    /**
     * 加载动画ImageView
     */
    private Dialog waitDialog;

    /**
     * 显示Loading动画
     *
     * @param msg
     */
    public void showLoadingDialog(String msg) {
        showLoadingDialog(msg, false);
    }

    public void showLoadingDialog(String msg, boolean b) {
        if (isFinishing() || isDestroyed())
            return;
        if (waitDialog == null) {
            if (TextUtils.isEmpty(msg))
                msg = "请稍等";
            waitDialog = LoadingDialogUtils.showDialogNew(this, msg, true);
        } else {
            if (waitDialog.isShowing()) {
                waitDialog.dismiss();
            }
            TextView testView = (TextView) waitDialog.findViewById(R.id.tipTextView);
            testView.setText(msg);

            waitDialog.setCancelable(!b);
            waitDialog.show();
        }
    }

    /**
     * 取消Loading动画
     */
    public void dismissLoadingDialog() {
        if (waitDialog != null && !isDestroyed() && waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = 0x04000000;//WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
