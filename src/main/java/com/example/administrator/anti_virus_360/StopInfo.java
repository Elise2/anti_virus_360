package com.example.administrator.anti_virus_360;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.StopInfoAdapter;
import entity.StopBlackInfo;

/**
 * Created by Administrator on 2015/9/23.
 */
public class StopInfo extends Activity {
    private List<StopBlackInfo> mdata;
    private ListView myList;
    private StopInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_stop_info);
        myList = (ListView)findViewById(R.id.stopInfolistView);
        mdata = new ArrayList<>();
        mdata.add(new StopBlackInfo("18363803961","移动","sdhienf","9/23"));
        mdata.add(new StopBlackInfo("18363803961","联通","sdhienf","9/23"));
        mdata.add(new StopBlackInfo("18363803961","联通","sdhienf","9/23"));
        mdata.add(new StopBlackInfo("18363803961","移动","sdhienf","9/23"));
        adapter = new StopInfoAdapter(mdata,this);
        myList.setAdapter(adapter);
        findViewById(R.id.clearall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
