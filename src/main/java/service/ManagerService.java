package service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;

import com.example.administrator.anti_virus_360.R;

import java.util.ArrayList;
import java.util.List;

import entity.RunningProcess;

public class ManagerService extends Service {


    private ActivityManager activityManager;
    private PackageManager packageManager;
    private OnScanTaskListener listener;

    public ManagerService() {
    }

    public void setListener(OnScanTaskListener listener) {
        this.listener = listener;
    }

    public void scanRunningProcess() {
        new MyAsyncTask().execute();
    }

    public class LoadService extends Binder {
        public ManagerService getServce() {
            return ManagerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LoadService();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        packageManager = getPackageManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     * 检索异步任务类
     */
    private class MyAsyncTask extends AsyncTask<Void, Integer, List<RunningProcess>> {

        @Override
        protected List<RunningProcess> doInBackground(Void... voids) {
            //获取当前正在运行的APPProcess，所有正在运行的APP集合
            List<ActivityManager.RunningAppProcessInfo>
                    runningAppProcessInfos =
                    activityManager.getRunningAppProcesses();
            List<RunningProcess> list = new ArrayList<>();
            int count = 0;//当前处理的进度，处理第几个进程
            publishProgress(runningAppProcessInfos.size(), count);
            for (ActivityManager.RunningAppProcessInfo r : runningAppProcessInfos) {
                publishProgress(runningAppProcessInfos.size(), ++count);
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
                                packageManager.getInstalledApplications(
                                        PackageManager.GET_UNINSTALLED_PACKAGES);
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
            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener != null) {
                listener.onStartScan();
            }
        }

        @Override
        protected void onPostExecute(List<RunningProcess> runningProcesses) {
            super.onPostExecute(runningProcesses);
            //将结果展示并隐藏progressbar
            if (listener != null) {
                listener.onCompleted(runningProcesses);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int total = values[0];
            int current = values[1];
            int process = (int) ((float) (current / total) * 100);
            if (listener != null) {
                listener.onScanProgress(total, current, process);
            }
        }
    }

    public interface OnScanTaskListener {
        public void onStartScan();//扫描之前

        public void onScanProgress(Integer... values);//扫描过程中

        public void onCompleted(List<RunningProcess> runningProcesses);//扫描完成

        void getCurrentItem(RunningProcess r);

        void removeCurrentItem(RunningProcess r);
    }
}
