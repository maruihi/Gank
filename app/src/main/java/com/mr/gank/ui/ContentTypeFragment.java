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
 * 内容分类fragment
 * Created by MR on 2018/4/20.
 */

public class ContentTypeFragment extends BaseFragment {


    @BindView(R.id.tv_content)
    TextView tvContent;

    public static BaseFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        ContentTypeFragment contentTypeFragment = new ContentTypeFragment();
        contentTypeFragment.setArguments(bundle);
        return contentTypeFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content_type;
    }

    @Override
    protected void onCreateAfter(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        tvContent.setText(bundle.getString("title"));
    }
}
