package com.mr.gank.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mr.gank.R;
import com.mr.gank.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 关注界面
 * Created by MR on 2018/4/19.
 */

public class FollowFragment extends BaseFragment {
    @BindView(R.id.tv_content)
    TextView tvContent;

    public static FollowFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        FollowFragment pageFragment = new FollowFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void onCreateAfter(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        tvContent.setText(bundle.getString("title"));
    }

}
