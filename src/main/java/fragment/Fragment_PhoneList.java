package fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.anti_virus_360.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PhoneAdapter;
import entity.Phone;

/**
 * Created by Administrator on 2015/9/24.
 */
public class Fragment_PhoneList extends Fragment {
    private ListView stopList;
    private List<Phone> mdata;
    private PhoneAdapter adapter;
    private Phone stopBlackInfo;
    private ContentResolver contentResolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mdata = new ArrayList<>();
        adapter = new PhoneAdapter(mdata, getActivity());
        stopList.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.anti_virus_stop_history, null);
        stopList = (ListView) view.findViewById(R.id.stopHistorylistView);
        view.findViewById(R.id.stopPhoneClearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdata.clear();
                adapter.notifyDataSetChanged();
            }
        });
        //内容访问者
        contentResolver = getActivity().getContentResolver();
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
            cursor.close();
        }
        return view;
    }
}
