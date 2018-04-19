package com.mr.gank.ui.me;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.mr.gank.R;
import com.mr.gank.core.BaseActivity;

/**
 * 登录
 * Author： by MR on 2017/9/18.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        showStatusBar = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateAfter(Bundle savedInstanceState) {

    }
}
