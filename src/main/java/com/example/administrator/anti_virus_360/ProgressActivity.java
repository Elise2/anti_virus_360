package com.example.administrator.anti_virus_360;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.RunningProcessAdapter;
import entity.RunningProcess;
import service.ManagerService;
import utils.StorageUtil;

/**
 * Created by Administrator on 2015/9/21.
 */
public class ProgressActivity extends Activity implements ManagerService.OnScanTaskListener,
        RunningProcessAdapter.OnClickClearListener {

    private TextView memoryInfo;
    private ActivityManager activityManager;
    private ListView listView;
    private PackageManager packageManager;
    private Handler handler;
    private ProgressBar bar;
    private TextView len;
    private AlertDialog alert;
    private LinearLayout layout;
    private List<RunningProcess> ps;
    private RunningProcessAdapter adapter;
    List<RunningProcess> list = new ArrayList<>();
    private ManagerService managerService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            managerService = ((ManagerService.LoadService) iBinder).getServce();
            //绑定成功执行任务
            managerService.setListener(ProgressActivity.this);
            managerService.scanRunningProcess();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            managerService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memoryInfo = (TextView) findViewById(R.id.memoryInfo);
        listView = (ListView) findViewById(R.id.list);
        bar = (ProgressBar) findViewById(R.id.progressBars);
        len = (TextView) findViewById(R.id.textprogress);
        layout = (LinearLayout) findViewById(R.id.relative);
        //获取activitymanger对象
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //得到包信息
        packageManager = getPackageManager();
        //获取内存信息
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);//不知道什么用
        long availMemory = info.availMem;   //得到可用内存
        long totalMemory = info.totalMem;   //得到总内存
        memoryInfo.setText("总内存：" + StorageUtil.convertStorage(totalMemory) +
                "可用内存：" + StorageUtil.convertStorage(availMemory));
        //new MyAsyncTask().execute();
        //绑定service
        bindService(new Intent(this, ManagerService.class), connection, Service.BIND_AUTO_CREATE);
        ps = new ArrayList<>();
        adapter = new RunningProcessAdapter(list, this);
        listView.setAdapter(adapter);
        adapter.setListener(this);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), ps.size() + "", Toast.LENGTH_SHORT).show();
                list.removeAll(ps);
                long totalMemory = 0;
                for (RunningProcess run : ps) {
                    activityManager.killBackgroundProcesses(run.getProcessName());
                    totalMemory += run.getMemorySize();
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(ProgressActivity.this,
                        "为您节省了" + StorageUtil.convertStorage(totalMemory) + "内存",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRunningApp() {
        //获取当前正在运行的APPProcess，所有正在运行的APP集合
        List<ActivityManager.RunningAppProcessInfo>
                runningAppProcessInfos =
                activityManager.getRunningAppProcesses();
        List<RunningProcess> list = new ArrayList<>();
        bar.setMax(runningAppProcessInfos.size());
        int is = 0;
        for (ActivityManager.RunningAppProcessInfo r : runningAppProcessInfos) {
            is++;
            bar.setProgress(is);
            if (bar.getMax() == is) {
                alert.dismiss();
            }
            RunningProcess rp = new RunningProcess();
            rp.setPid(r.pid);//process id
            rp.setProcessName(r.processName);
            rp.setUid(r.uid);// user id
            try {
                ApplicationInfo applicationInfo =
                        packageManager.getApplicationInfo(r.processName, 0);
                //获取对应APP的名字
                String appName = applicationInfo.loadLabel(packageManager).toString();
                rp.setAppName(appName);
                //获取对应app的图标
                Drawable icon = applicationInfo.loadIcon(packageManager);
                rp.setIcon(icon);
                //获取是否系统APP
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    rp.setIsSystem(true);
                } else {
                    rp.setIsSystem(false);
                }
            } catch (PackageManager.NameNotFoundException e) {
                //e.printStackTrace();
                if (r.processName.indexOf(":") != -1) {
                    //取包名
                    String packageName = r.processName.substring(0, r.processName.indexOf(":"));
                    ApplicationInfo applicationInfo = null;
                    //根据包名获取applicationinfo
                    //首先找到设备上所有安装的APP
                    List<ApplicationInfo> apps =
                            packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                    //得到所有的安装和已经卸载的应用
                    for (ApplicationInfo i : apps) {
                        if (packageName.equals(i.processName)) {
                            applicationInfo = i;
                            break;
                        }
                    }
                    if (applicationInfo != null) {
                        Drawable icon = applicationInfo.loadIcon(packageManager);
                        String appName = applicationInfo.loadLabel(packageManager).toString();
                        rp.setIcon(icon);
                        rp.setAppName(appName);
                    } else {
                        rp.setAppName(r.processName);
                        rp.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    }
                } else {
                    rp.setAppName(r.processName);
                    rp.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                }
            }
            //获取对应app的大小
            Debug.MemoryInfo memory[] =
                    activityManager.getProcessMemoryInfo(new int[]{r.pid});
            long memorySize = memory[0].getTotalPrivateDirty() * 1024;
            rp.setMemorySize(memorySize);
            list.add(rp);
            Message msg = new Message();
            msg.obj = list;
            msg.what = 1;
            handler.sendMessage(msg);
            msg = new Message();
            msg.obj = is;
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onStartScan() {
        bar.setProgress(0);
        len.setText("正在扫描");
    }

    @Override
    public void onScanProgress(Integer... values) {
        int total = values[0];
        int current = values[1];
        int process = values[2];
        len.setText(current + "/" + total);
        bar.setProgress(process);
    }

    @Override
    public void onCompleted(List<RunningProcess> runningProcesses) {
        for (RunningProcess process : runningProcesses) {
            if (process.isSystem()) {
                list.add(process);
            }
        }
        adapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //managerService.unbindService(connection);
    }

    @Override
    public void getCurrentItem(RunningProcess r) {
        ps.add(r);
    }

    @Override
    public void removeCurrentItem(RunningProcess r) {
        ps.remove(r);
    }
}
