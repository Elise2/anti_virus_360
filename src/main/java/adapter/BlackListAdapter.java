package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.anti_virus_360.R;

import java.util.List;

import entity.BlockList;

/**黑名单Adapter
 * Created by Administrator on 2015/9/22.
 */
public class BlackListAdapter extends BaseAdapter {
    private List<BlockList> mData;
    private Context context;
    private onSlectedListener listener;

    public BlackListAdapter(List<BlockList> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setListener(onSlectedListener listener) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        StopHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.anti_virus_blacklist, null);
            holder = new StopHolder();
            holder.phoneNum = (TextView) convertView.findViewById(R.id.blackphoneName1);
            holder.phoneInfo = (TextView) convertView.findViewById(R.id.blackPhoneNum1);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.isSelected);
            convertView.setTag(holder);
        } else {
            holder = (StopHolder) convertView.getTag();
        }
        final BlockList blockman = this.mData.get(position);
        holder.phoneNum.setText(blockman.getPhoneNumber());
        holder.phoneInfo.setText(blockman.getName());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    listener.getCurrentItem(blockman);
                } else {
                    listener.removeCurrentItem(blockman);
                }
            }
        });
        return convertView;
    }

    public class StopHolder {
        public TextView phoneNum;
        public TextView phoneInfo;
        public CheckBox checkBox;
    }

    public interface onSlectedListener {
        public void getCurrentItem(BlockList blockUser);

        public void removeCurrentItem(BlockList blockUser);
    }
}
