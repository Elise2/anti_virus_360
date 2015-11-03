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

public class CureService extends Service {
    private  PackageManager packageManager;
    private  ActivityManager activityManager;
    private onScanTaskListener mOnScanTaskListener;

    public void setmOnScanTaskListener(onScanTaskListener mOnScanTaskListener) {
        this.mOnScanTaskListener = mOnScanTaskListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //最主要的两个对象都赋值
        activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        packageManager = getPackageManager();
    }
    public  class  localService extends Binder{
        public  CureService getService(){
            return  CureService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return  new localService();
    }

    /**
     * 查看当前运行的线程信息
     */
    public  void  scanRunningProgress(){
        new ScanRunningProgressTask().execute();
    }

    /**
     * 检索异步任务类
     */
    public class  ScanRunningProgressTask extends AsyncTask<Void,Integer,List<RunningProcess>> {
        @Override
        protected List<RunningProcess> doInBackground(Void... params) {//不停的发送当前进度
            //获取当前的正在运行的APPProcess
            List<ActivityManager.RunningAppProcessInfo>
                    runningAppProcessInfos=activityManager.getRunningAppProcesses();
            int count = 0;//当前处理的进度，处理的是第几个进程
            publishProgress(runningAppProcessInfos.size(),count);
            List<RunningProcess> list = new ArrayList<>();
            for(ActivityManager.RunningAppProcessInfo info: runningAppProcessInfos){
                //每执行一次，进度发生变化,并发布进度
                publishProgress(runningAppProcessInfos.size(),++count);

                RunningProcess rp = new RunningProcess();
                rp.setProcessName(info.processName);
                rp.setPid(info.pid);
                rp.setUid(info.uid);

                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(info.processName, 0);
                    //获取对应app的名字
                    String appName = applicationInfo.loadLabel(packageManager).toString();
                    //获取对应app的图标
                    Drawable icon = applicationInfo.loadIcon(packageManager);
                    rp.setIcon(icon);
                    rp.setProcessName(appName);


                    if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM)!=0){
                        rp.setIsSystem(true);
                    }else{
                        rp.setIsSystem(false);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                   // e.printStackTrace();
                    if (info.processName.indexOf(":")!=-1){
                        String packageName = info.processName.substring(0,info.processName.indexOf(":"));
                        ApplicationInfo appInfo = null;
                        //根据包名获取Application
                        //找到设备上所有安装的app
                        List<ApplicationInfo> apps = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                        for (ApplicationInfo i :apps){
                            //看包名是否一致
                            if (packageName.equals(i.processName)){
                                appInfo = i;
                                break;
                            }
                        }
                        if (appInfo!=null){
                            Drawable appIcon = appInfo.loadIcon(packageManager);
                            String label = appInfo.loadLabel(packageManager).toString();
                            rp.setIcon(appIcon);
                            rp.setAppName(label);
                        }else {
                            rp.setAppName(info.processName);
                            rp.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                        }
                    }else {
                        rp.setAppName(info.processName);
                        rp.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                    }
                }
                //获取app占用内存的大小
                Debug.MemoryInfo[] memory = activityManager.getProcessMemoryInfo(new int[]{info.pid});
                long memorySize = memory[0].getTotalPrivateDirty()*1024;
                rp.setMemorySize(memorySize);
                if (!rp.isSystem()) {
                    list.add(rp);
                }
            }
            return  list;
        }

        @Override
        protected void onPreExecute() {
             super.onPreExecute();
           if (mOnScanTaskListener!=null){
               mOnScanTaskListener.onStartScan();
           }
        }

        @Override
        protected void onPostExecute(List<RunningProcess> runningProcesses) {
            super.onPostExecute(runningProcesses);
            //将结果展示并隐藏progress
            if (mOnScanTaskListener!=null){
                mOnScanTaskListener.onScanCompleted(runningProcesses);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //该方法也是在主线程中进行
            int totalCount = values[0];
            int curCount = values[1];
            int progress = (int) (((curCount*1.0)/totalCount)*100);
            if (mOnScanTaskListener!=null){
                mOnScanTaskListener.onScanProgressUpdate(totalCount,curCount,progress);
            }
        }
    }

    /**
     * 该接口主要实现扫描过程的监听，谁进行扫描，就要实现该接口
     */
    public  interface onScanTaskListener{
        public void onStartScan();//扫描之前干什么
        public void  onScanProgressUpdate(Integer... values);//扫描过程中
        public void  onScanCompleted(List<RunningProcess> runningProcesses);//扫描完成
    }
}

