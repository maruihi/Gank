package com.mr.gank.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mr.gank.R;
import com.mr.gank.core.BaseActivity;
import com.mr.gank.core.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl)
    TabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<String> tabNames = new ArrayList<>();
    private List<BaseFragment> fragments=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateAfter(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        tabNames.add("关注");
        tabNames.add("Ios");
        tabNames.add("Android");
        tabNames.add("前端");
        tabNames.add("瞎推荐");
        tabNames.add("扩展资源");
        tabNames.add("福利");
        tabNames.add("App");
        tabNames.add("休息视频");

        fragments.add(new FollowFragment());


    }

    @OnClick({R.id.tv_title_about, R.id.iv_title_collection_folder, R.id.iv_title_calendar_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_about:
                Intent intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_title_collection_folder:
                Intent intent1 = new Intent(mContext, CollectionFolderActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_title_calendar_search:
                Intent intent2 = new Intent(mContext, CalendarSearchActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
