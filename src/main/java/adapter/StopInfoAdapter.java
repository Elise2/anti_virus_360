package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.anti_virus_360.R;

import java.util.List;

import entity.StopBlackInfo;

/**
 * Created by Administrator on 2015/9/22.
 */
public class StopInfoAdapter extends BaseAdapter {
    private List<StopBlackInfo> mData;
    private Context context;
    private onUserClickedListener listener;

    public StopInfoAdapter(List<StopBlackInfo> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setListener(onUserClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StopHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.anti_virus_stopinfo_item, null);
            holder = new StopHolder();
            holder.phoneNum = (TextView) convertView.findViewById(R.id.sms_phone);
            holder.phoneInfo = (TextView) convertView.findViewById(R.id.sms_time);
            holder.phoneType = (TextView) convertView.findViewById(R.id.sms_message);
//            holder.phoneTime = (TextView) convertView.findViewById(R.id.phoningTime);
//            holder.blockIsSelected = (CheckBox) convertView.findViewById(R.id.blockUserSelected);
            convertView.setTag(holder);
        } else {
            holder = (StopHolder) convertView.getTag();
        }
        StopBlackInfo sms = this.mData.get(position);
        holder.phoneNum.setText(sms.getPhoneNum());
        holder.phoneInfo.setText(sms.getTime());
//        holder.phoneType.setText(sms.getPhoneContent());
//        holder.phoneTime.setText(sms.getTime());
        return convertView;
    }

    public class StopHolder {
        public TextView phoneNum;
        public TextView phoneInfo;
        public TextView phoneType;
//        public TextView phoneTime;
//        public CheckBox blockIsSelected;
    }

    public interface onUserClickedListener {
        public void getCurrent(StopBlackInfo blackInfo);

        public void removeCurrent(StopBlackInfo blackInfo);
    }
}
