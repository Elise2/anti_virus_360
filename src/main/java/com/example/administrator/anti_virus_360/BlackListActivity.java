package com.example.administrator.anti_virus_360;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.PhoneAdapter;
import dao.PhoneDao;
import entity.Phone;

/**来电拦截
 * Created by Administrator on 2015/9/24.
 */
public class BlackListActivity extends ActionBarActivity{
    private ListView stopList;
    private List<Phone> phones;
    private PhoneAdapter adapter;
    private Button cleabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_blacklist_iterm);
        stopList = (ListView) findViewById(R.id.blacklistView);
        cleabtn = (Button)findViewById(R.id.blackSureBtn);
        phones = new ArrayList<>();
        PhoneDao phoneDao = new PhoneDao(getApplicationContext());
        phones = phoneDao.findAll();
        adapter = new PhoneAdapter(phones,getApplicationContext());
        stopList.setAdapter(adapter);
        if (cleabtn.isSelected()){
            phones.clear();
            adapter.notifyDataSetChanged();
        }
    }
}
