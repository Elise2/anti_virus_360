package adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.anti_virus_360.R;

import java.util.List;

import entity.RunningProcess;
import utils.StorageUtil;


/**
 * Created by Owner on 2015/9/17.
 */
public class SoftManagerAdapter extends BaseAdapter {
    private List<RunningProcess> mdata;
    private Context context;
    public SoftManagerAdapter(List<RunningProcess> mdata, Context context) {
        this.mdata = mdata;
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.list_item1, null);

            holder = new ViewHolder();
            holder.otherInfo = (TextView) view.findViewById(R.id.otherInfo1);
            holder.appName = (TextView) view.findViewById(R.id.appName1);
            holder.pckName = (TextView) view.findViewById(R.id.pckName1);
            holder.icon = (ImageView) view.findViewById(R.id.icon1);
            holder.softManageBtn = (Button) view.findViewById(R.id.softmanageBtn);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.softManageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_DELETE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse(Uri.parse("package:")+mdata.get(i).getProcessName()));
                context.startActivity(intent);
            }
        });
        holder.appName.setText(mdata.get(i).getAppName());
        holder.pckName.setText(mdata.get(i).getProcessName());
        holder.icon.setImageDrawable(mdata.get(i).getIcon());
        holder.otherInfo.setText("内存：" +
                StorageUtil.convertStorage(mdata.get(i).getMemorySize())
                + "系统应用");
        return view;
    }

    private class ViewHolder {
        private TextView appName;
        private TextView pckName;
        private ImageView icon;
        private TextView otherInfo;
        private Button softManageBtn;
    }
}
