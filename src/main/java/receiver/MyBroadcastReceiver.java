package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/9/21.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //将数据拿到
            String data = intent.getStringExtra("data");
            Toast.makeText(context, data, Toast.LENGTH_LONG).show();

    }
}
