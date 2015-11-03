package com.example.administrator.anti_virus_360;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import utils.StorageUtil;

/**
 * Created by Administrator on 2015/9/25.
 */
public class FlowMangerActivity extends ActionBarActivity{
    private TextView flowShow;
    private ListView flowlistView;
    private TextView detailShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_manager);
        flowShow = (TextView)findViewById(R.id.flowShow);
        detailShow = (TextView)findViewById(R.id.detailShow);
//        setContentView(R.layout.anti_virus_flow_manager);
//        findViewById(R.id.flowBtn).setOnClickListener(this);
//        findViewById(R.id.appflowBtn).setOnClickListener(this);
//        flowShow = (TextView) findViewById(R.id.flowShow);
//        flowlistView = (ListView)findViewById(R.id.flowList);
        //上传的流量，mobile（无关WiFi）
        long updateData = TrafficStats.getMobileTxBytes();
        //下载的流量
        long downLoadData = TrafficStats.getMobileRxBytes();
        long totalDownData = TrafficStats.getTotalRxBytes();
        long totalUpdateData = TrafficStats.getTotalTxBytes();
        long total = totalDownData+totalUpdateData;
        flowShow.setText(StorageUtil.convertStorage(total));
        detailShow.setText("上传流量：" + StorageUtil.convertStorage(updateData) +
                " 下载流量：" + StorageUtil.convertStorage(downLoadData) +
                "        上传： " + StorageUtil.convertStorage(totalUpdateData) +
                "        下载： " + StorageUtil.convertStorage(totalDownData));

    }

//    public void getDataInfo() {
//        //上传的流量，mobile（无关WiFi）
//        long updateData = TrafficStats.getMobileTxBytes();
//        //下载的流量
//        long downLoadData = TrafficStats.getMobileRxBytes();
//        long totalDownData = TrafficStats.getTotalRxBytes();
//        long totalUpdateData = TrafficStats.getTotalTxBytes();
//        //具体的App流量
//        // TrafficStats.getUidRxBytes(uid);
//        // TrafficStats.getUidTxBytes(uid);
//        flowShow.setText(StorageUtil.convertStorage(totalUpdateData));
//        detailShow.setText("上传流量：" + StorageUtil.convertStorage(updateData) +
//                " 下载流量：" + StorageUtil.convertStorage(downLoadData) +
//                "上传： " + StorageUtil.convertStorage(totalUpdateData) +
//                "下载： " + StorageUtil.convertStorage(totalDownData));
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.flowBtn:
//                getDataInfo();
//                break;
//            case R.id.appflowBtn:
//                break;
//        }
//    }
//
//    public void getAppDataInfo() {
//        //见具有权限的已安装的APP获取到
//        PackageManager packageManager = getPackageManager();
//        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
//        List<APPDataItem> items = new ArrayList<>();
//        for (PackageInfo info : packageInfos) {
//            //判断此应用是否具有访问网络的权限
//            String[] permissions = info.requestedPermissions;
//            if(permissions==null)
//                continue;
//            for (String p : permissions) {
//                if ("android.permission.INTERNET".equals(p)) {
//                    //应用的名字
//                    String appName = info.applicationInfo.loadLabel(packageManager).toString();
//                    Drawable icon = info.applicationInfo.loadIcon(packageManager);
//                    String packageName = info.packageName;
//                    int uid = info.applicationInfo.uid;
//
//                    APPDataItem item = new APPDataItem();
//                    item.setAppName(appName);
//                    item.setIcon(icon);
//                    item.setUid(uid);
//                    item.setPackgeName(packageName);
//
//                    //流量信息
//                    //app接收数据的总量
//                    Long total = TrafficStats.getUidRxBytes(item.getUid()) +
//                            TrafficStats.getUidTxBytes(item.getUid());
//                    item.setMobileData(total);
//
//
//                    items.add(item);
//                    break;
//                }
//            }
//        }
////        //显示
////        StringBuilder sb = new StringBuilder();
////        for (APPDataItem item:items){
////            sb.append(item.getAppName());
////            sb.append(item.getIcon());
////        }
//    }
}
