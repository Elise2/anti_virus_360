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

import entity.Phone;

/**
 * Created by Administrator on 2015/9/26.
 */
public class PhoneAdapter extends BaseAdapter {
    private List<Phone> phones;
    private Context mContext;
    private onPhoneClickedListener listener;

    public void setListener(onPhoneClickedListener listener) {
        this.listener = listener;
    }

    public PhoneAdapter(List<Phone> phones, Context mContext) {
        this.phones = phones;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return this.phones.size();
    }

    @Override
    public Object getItem(int position) {
        return this.phones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.anti_virus_stop_phone_item, null);
            holder = new ViewHolder();
            holder.phone = (TextView) view.findViewById(R.id.phone_number);
            holder.time = (TextView) view.findViewById(R.id.phone_time);
            holder.name = (TextView) view.findViewById(R.id.phone_name);
            holder.attribution = (TextView) view.findViewById(R.id.phone_attribution);
            holder.checkBox =(CheckBox)view.findViewById(R.id.contactCheckBox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Phone p = phones.get(position);
        holder.phone.setText(p.getPhoneNumber());
        holder.time.setText(p.getDate());
        holder.name.setText(p.getName());
        holder.attribution.setText(p.getAttribution());
        if (holder.checkBox.isChecked()){
            listener.getCurrent(p);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()){
                    listener.getCurrent(phones.get(position));
                }else {
                    listener.removeCurrent(phones.get(position));
                }
            }
        });
        return view;
    }

    private class ViewHolder {
        private TextView phone;
        private TextView name;
        private TextView time;
        private TextView attribution;
        private CheckBox checkBox;
    }

    public interface onPhoneClickedListener {
        public void getCurrent(Phone blackInfo);

        public void removeCurrent(Phone blackInfo);
    }
}
