package com.mr.gank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mr.gank.R;
import com.mr.gank.core.BaseActivity;
import com.mr.gank.core.BaseFragment;
import com.mr.gank.utils.TabLayoutUtil;
import com.mr.gank.widget.colortrackview.ColorTrackTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_color)
    ColorTrackTabLayout tl;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<String> tabNames = new ArrayList<>();
    private List<BaseFragment> fragments = new ArrayList<>();
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreateAfter(Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initView() {
        tl.offsetLeftAndRight(10);
        TabLayoutUtil.setIndicatorEqualTitle(tl);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this, fragments, tabNames);
        vp.setAdapter(pagerAdapter);
        vp.setOffscreenPageLimit(tabNames.size());
        tl.setupWithViewPager(vp);

    }

    private void initData() {
        tabNames.add("关注");
        tabNames.add("Ios");
        tabNames.add("Android");
        tabNames.add("前端");
        tabNames.add("瞎推荐");
        tabNames.add("扩展资源");
        tabNames.add("福利");
        tabNames.add("休息视频");

        fragments.add(FollowFragment.newInstance(tabNames.get(0)));
        for (int i = 1; i < tabNames.size(); i++) {
            fragments.add(ContentTypeFragment.newInstance(tabNames.get(i)));
        }

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

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragmentList;
        private List<String> tabNamesList;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context, List<BaseFragment> fragments, List<String> tabNames) {
            super(fm);
            fragmentList = fragments;
            tabNamesList = tabNames;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNamesList.get(position);
        }

    }

}
