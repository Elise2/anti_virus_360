package com.example.administrator.anti_virus_360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/9/23.
 */
public class Telephony extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_stop_layout);
        findViewById(R.id.stopInfo1).setOnClickListener(this);
        findViewById(R.id.stopHistory1).setOnClickListener(this);
        findViewById(R.id.stopPhone1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopInfo1://短信拦截
                startActivity(new Intent(getApplicationContext(), StopInfo.class));
                break;
            case R.id.stopHistory1://来电记录
                startActivity(new Intent(getApplicationContext(),StopPhoneActivity.class));
                break;
            case R.id.stopPhone1://电话拦截
                startActivity(new Intent(getApplicationContext(),BlackListActivity.class));
        }
    }
}
