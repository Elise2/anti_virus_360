package com.example.administrator.anti_virus_360;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.RadioGroup;
import android.widget.TabHost;

import adapter.TabsAdapter;
import fragment.Fragment_PhoneList;

/**
 * Created by Administrator on 2015/9/24.
 */
public class StopMainActivity extends FragmentActivity {
    private FragmentTabHost mTabHost;
    private RadioGroup mTabRg;
    private ViewPager mViewPage;
    private ActionBar actionBar;
    TabsAdapter mTabsAdapter;
    private final Class[] fragments = {Fragment_PhoneList.class,  Fragment_PhoneList.class,
            Fragment_PhoneList.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_tabs);
        initView();
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    private void initView() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager());
        mViewPage = (ViewPager) findViewById(R.id.pager);
        mTabRg = (RadioGroup) findViewById(R.id.tab_rg_menu);
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPage, mTabRg);
        // 得到fragment的个数
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragments[i], null);

            mTabsAdapter.addTab(mTabHost.newTabSpec(i + "")
                    .setIndicator(i + ""), fragments[i], null);
        }

        //定义滑动的监听
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_rb_1:
                        mTabHost.setCurrentTab(0);
                        break;
                    case R.id.tab_rb_2:
                        mTabHost.setCurrentTab(1);

                        break;
                    case R.id.tab_rb_3:
                        mTabHost.setCurrentTab(2);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

}
