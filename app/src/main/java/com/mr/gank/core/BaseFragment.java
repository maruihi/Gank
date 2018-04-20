package com.mr.gank.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mr.gank.utils.helper.DebugHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseFragment 封装
 * Author： by MR on 2017/3/15.
 */
public abstract class BaseFragment extends Fragment {
//    public SparseArray arg = new SparseArray(); //根据id缓存一些值

    public View rootView;
    protected Context mContext;
    private Unbinder bind;

    protected final <T extends View> T findViewById(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        bind.unbind();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCreateBefore();
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            rootView = inflater.inflate(layoutId, container, false);
            bind = ButterKnife.bind(this, rootView);
        }else {
            //没有提供ViewId
            DebugHelper.throwIllegalState("没有提供正确的LayoutId");
            return null;
        }

        if (userEventBus()) {
            EventBus.getDefault().register(this);
        }

        return rootView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        //留给子类重写
        onCreateAfter(view,savedInstanceState);
    }
    protected void onCreateBefore() {

    }

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void onCreateAfter(View view, Bundle savedInstanceState);


    @Override
    public void onStart() {
        super.onStart();
    }

    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    public void clearOverridePendingTransition() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null)
            activity.clearOverridePendingTransition();
    }

    public void overridePendingTransitionEnter() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null)
            activity.overridePendingTransitionEnter();
    }

    public void overridePendingTransitionBack() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null)
            activity.overridePendingTransitionBack();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
    }


    protected boolean userEventBus() {
        return false;
    }

    protected void showLoadingDialog(String msg) {
        Activity activity = getActivity();
        if(activity!=null && activity instanceof BaseActivity){
            ((BaseActivity) activity).showLoadingDialog(msg);
        }
    }

    /**
     * 取消Loading动画
     */
    protected void dismissLoadingDialog() {
        Activity activity = getActivity();
        if(activity!=null && activity instanceof BaseActivity){
            ((BaseActivity) activity).dismissLoadingDialog();
        }
    }

}
