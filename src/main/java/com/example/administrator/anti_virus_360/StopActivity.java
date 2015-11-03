package com.example.administrator.anti_virus_360;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import fragment.StopFragemt;

/**
 * Created by Administrator on 2015/9/25.
 */
public class StopActivity extends AppCompatActivity {
    private FragmentTabHost tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_stoplist1);
        tab = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tab.setup(this, getSupportFragmentManager(), R.id.stoplist1_container);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3EAE19));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("骚扰拦截");
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        addTabs("拦截短信", "sms", bundle);
        bundle = new Bundle();
        bundle.putInt("type",1);
        addTabs("拦截电话","black",bundle);
        bundle = new Bundle();
        bundle.putInt("type",2);
        addTabs("来电记录","phone",bundle);



    }
    private void addTabs(String tabName, String tag, Bundle bundle) {
        TextView tabItem = new TextView(this);
        tabItem.setText(tabName);
        tabItem.setGravity(Gravity.CENTER);
        tabItem.setBackgroundColor(0xff3EAE19);
        tabItem.setTextColor(getResources().getColor(R.color.contact_color));
        tabItem.setTextSize(16);
        FragmentTabHost.TabSpec tabSpec = tab.newTabSpec(tag);
        tabSpec.setIndicator(tabItem);
        tab.addTab(tabSpec, StopFragemt.class, bundle);
    }

}
