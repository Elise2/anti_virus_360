package service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.IBinder;

/**
 * Created by Administrator on 2015/9/25.
 */
public class DataService extends Service {
    private  static  int REF_MESSAGE = 1;
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case REF_MESSAGE:
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            recordData();
//                        }
//                    },1000*60);
//                    break;
//            }
//
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        recordData();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public  void  recordData(){//要写数据库，需要运行在子线程中
        //判断网络类型
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //判断当前的网络连接类型
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!networkInfo.isAvailable())
            return;
        //网络连接有效
        //获取当前网络的类型
        String type = "";
        if (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
            //Mobile流量
            type = "m";
        }else if (networkInfo.getType()==connectivityManager.TYPE_WIFI){
            //wifi
           type = "w";
        }
        //读取目前为止的数据总量
        long total = TrafficStats.getUidRxBytes(getApplicationInfo().uid)+TrafficStats.getUidTxBytes(getApplicationInfo().uid);

        //数据库中要有uid nettype dataSize
        //mHandler.sendEmptyMessage(REF_MESSAGE);

    }
}
