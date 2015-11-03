package com.example.administrator.anti_virus_360;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.PhoneAdapter;
import dao.PhoneDao;
import entity.Phone;

/**
 * Created by Administrator on 2015/9/24.
 */
public class StopPhoneActivity extends ActionBarActivity implements PhoneAdapter.onPhoneClickedListener{
    private ListView stopList;
    private List<Phone> mdata;
    private List<Phone> phones;
    private PhoneAdapter adapter;
    private  Phone stopBlackInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anti_virus_stop_history);
        stopList = (ListView) findViewById(R.id.stopHistorylistView);
        phones = new ArrayList<>();
        findViewById(R.id.stopPhoneClearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneDao phoneDao = new PhoneDao(getApplicationContext());
                for (Phone phone:phones){
                    phoneDao.insetPhoneValues(phone);
                }
            }
        });
        mdata = new ArrayList<>();
        //内容访问者
        ContentResolver contentResolver = getContentResolver();
        //Content的Uri
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            // ContactsContract.CommonDataKinds.Phone._ID //联系人的编号
            // ContactsContract.CommonDataKinds.Phone.NUMBER//联系人的号码
            //ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME联系人的姓名
            while (cursor.moveToNext()) {
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameIndex);
                int idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID);
                int id = cursor.getInt(idIndex);
                stopBlackInfo = new Phone(number, name, id + "", System.currentTimeMillis() + "");
                mdata.add(stopBlackInfo);
            }
            adapter = new PhoneAdapter(mdata, this);
            stopList.setAdapter(adapter);
            cursor.close();
        }
    }

    @Override
    public void getCurrent(Phone phone) {
        phones.add(phone);
    }

    @Override
    public void removeCurrent(Phone phone) {
        phones.remove(phone);
    }
}
