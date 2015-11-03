package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.anti_virus_360.R;

import java.util.ArrayList;
import java.util.List;

import entity.RunningProcess;
import utils.StorageUtil;


/**
 * Created by Owner on 2015/9/17.
 */
public class RunningProcessAdapter extends BaseAdapter {
    private List<RunningProcess> mdata;
    private Context context;
    private List<RunningProcess> processes;
    private OnClickClearListener listener;

    public RunningProcessAdapter(List<RunningProcess> mdata, Context context) {
        this.mdata = mdata;
        this.context = context;
        processes = new ArrayList<>();
    }
    //自己定义的接口
    public void setListener(OnClickClearListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return this.mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.otherInfo = (TextView) view.findViewById(R.id.otherInfo);
            holder.appName = (TextView) view.findViewById(R.id.appName);
            holder.pckName = (TextView) view.findViewById(R.id.pckName);
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.box = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder.box.isChecked()) {
            listener.getCurrentItem(mdata.get(i));
        }
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox box = (CheckBox) view;
                if (box.isChecked()) {
                    listener.getCurrentItem(mdata.get(i));
                } else {
                    listener.removeCurrentItem(mdata.get(i));
                }
            }
        });
        holder.appName.setText(mdata.get(i).getAppName());
        holder.pckName.setText(mdata.get(i).getProcessName());
        holder.icon.setImageDrawable(mdata.get(i).getIcon());
        holder.otherInfo.setText("内存：" +
                StorageUtil.convertStorage(mdata.get(i).getMemorySize())
                + "系统应用");
        //Toast.makeText(context, processes.size() + "", Toast.LENGTH_SHORT).show();
        return view;
    }

    private class ViewHolder {
        private TextView appName;
        private TextView pckName;
        private ImageView icon;
        private TextView otherInfo;
        private CheckBox box;
    }

    public interface OnClickClearListener {
        public void getCurrentItem(RunningProcess r);

        public void removeCurrentItem(RunningProcess r);
    }
}
