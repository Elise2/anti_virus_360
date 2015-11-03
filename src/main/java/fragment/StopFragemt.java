package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.anti_virus_360.R;

import java.util.ArrayList;
import java.util.List;

import adapter.BlackListAdapter;
import adapter.PhoneAdapter;
import adapter.StopSMSAdapter;
import dao.BlackListDao;
import dao.PhoneDao;
import dao.SMSDao;
import entity.BlockList;
import entity.Phone;
import entity.SMS;

/**
 * Created by Administrator on 2015/9/25.
 */
public class StopFragemt extends Fragment {
    private int type;
    private List<SMS> smses;
    private List<Phone> phones;
    private ListView listView;
    private List<BlockList>blackList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        smses = new ArrayList<>();
        phones = new ArrayList<>();
        blackList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.anti_virus_list_view,null);
        listView =(ListView) v.findViewById(R.id.list_view);
        loadData();
        return v;
    }

    private  void loadData(){
        //得到SMS数据库
        if (type==0) {
            SMSDao dao = new SMSDao(getActivity());
            smses = dao.findAll();
            StopSMSAdapter adapter = new StopSMSAdapter(getActivity(), smses);
            listView.setAdapter(adapter);
        }else if (type==1){
            //得到数据库
            PhoneDao dao = new PhoneDao(getActivity());
            phones = dao.findAll();
            PhoneAdapter adapter = new PhoneAdapter(phones,getActivity());
            listView.setAdapter(adapter);

        }else if (type==2){
            BlackListDao dao = new BlackListDao(getActivity());
            blackList = dao.findAll();
            BlackListAdapter adapter = new BlackListAdapter(blackList,getActivity());
            listView.setAdapter(adapter);

        }
    }
}
