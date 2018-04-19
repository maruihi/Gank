package com.mr.gank;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.mr.gank.model.event.ToForegroundEvent;
import com.mr.gank.utils.MLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



/**
 * Author： by MR on 2017/3/15.
 */

public class App extends Application {

    /**
     * 全局一个context(就一个,虽然是静态,但是不会导致内存泄露,因为如果这个context生命周期结束,说明应用也停止了)
     */
    public static Context appContext;

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //过滤非主进程的东西
        if (!mainApplication()) {
            return;
        }
        appContext = getApplicationContext();

        //==========初始化相关==========
        initAppManager();
        initLogController();
        instance = this;

    }

    /**
     * Init log controller.初始化日志控制器
     */
    private void initLogController() {
        //如果是调试模式,则输出日志,否则不输出(防止信息泄露)
        if (BuildConfig.DEBUG) {
            MLog.plant(new MLog.DebugTree());
        } else {
            MLog.plant(new MLog.ReleaseTree());
        }
    }

    /**
     * 初始化 App管理器(监听生命周期和软件的前后台切换)
     */
    private void initAppManager() {
        AppManager.getInstance().init(this);
        //前后台切换监听,切换的时候会发Eventbus事件,需要用到这个的地方直接接受Eventbus即可
        AppManager.getInstance().setOnAppForegroundChange(new AppManager.OnAppForegroundChange() {
            @Override
            public void onChange(boolean isForeground) {
                EventBus.getDefault().post(new ToForegroundEvent(isForeground));
            }
        });
    }

    /**
     * 判断主进程的Application
     *
     * @return the boolean
     */
    private boolean mainApplication() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断App是否在前台
     *
     * @return true 前台
     */
    public static boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (App.appContext.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static App getInstance() {
        return (App) appContext;
    }

}
