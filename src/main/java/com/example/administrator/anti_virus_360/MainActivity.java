package com.example.administrator.anti_virus_360;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
      private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_start_360menu);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        findViewById(R.id.stopMange).setOnClickListener(this);
        findViewById(R.id.clearMore).setOnClickListener(this);
        findViewById(R.id.flowManger).setOnClickListener(this);
        findViewById(R.id.softMag).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anti_virus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stopMange:
                startActivity(new Intent(getApplicationContext(),Telephony.class));
                break;
            case R.id.clearMore:
                startActivity(new Intent(getApplicationContext(),ClearMoreActivity.class));
                break;
            case R.id.flowManger:
                startActivity(new Intent(getApplicationContext(),FlowMangerActivity.class));
                break;
            case R.id.softMag:
                startActivity(new Intent(getApplicationContext(),SoftManagerActivity.class));
                break;
        }
    }
}
