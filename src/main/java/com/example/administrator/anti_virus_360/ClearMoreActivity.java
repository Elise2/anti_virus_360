package com.example.administrator.anti_virus_360;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/9/23.
 */
public class ClearMoreActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_clear_morespeed);
        findViewById(R.id.clear2).setOnClickListener(this);
        findViewById(R.id.clearBtn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear2:
                startActivity(new Intent(this,Clean_rubbish.class));
                break;
            case  R.id.clearBtn2:
                startActivity(new Intent(this, ProgressActivity1.class));
                break;
        }
    }
}
