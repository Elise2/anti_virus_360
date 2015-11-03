package com.example.administrator.anti_virus_360;

import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import entity.AppCacheItem;
import utils.StorageUtil;

/**
 * Created by Administrator on 2015/9/29.
 */
public class Clean_rubbish extends Activity implements View.OnClickListener {
    private TextView casheSize;
    private TextView appCacheInfo;
    private Method getPackageSizeInfo;
    private Method freeStorageAndNotify;
    private long totalSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clean_rubbish);
        casheSize = (TextView) findViewById(R.id.cacheSize);
        appCacheInfo = (TextView) findViewById(R.id.AppCacheInfo);
        findViewById(R.id.cachebtn).setOnClickListener(this);
        findViewById(R.id.cleanCache).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cachebtn:
                PackageManager pm = getPackageManager();
                //使用反射机制得到安装包的大小
                try {
                    getPackageSizeInfo = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                    getPackageSizeInfo.setAccessible(true);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                final List<AppCacheItem> items = new ArrayList<>();
                final List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                final CountDownLatch countDownLatch = new CountDownLatch(packageInfos.size());
                    try {
                        //pm去调用getPackageSizeInfo的方法
                        for (final PackageInfo p : packageInfos) {
                        //异步过程，虽然很快，但有可能不能实现
                        getPackageSizeInfo.invoke(pm, p.packageName, new IPackageStatsObserver.Stub() {
                            @Override
                            public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                                if (succeeded && pStats != null) {
                                    AppCacheItem item = new AppCacheItem();
                                    item.setCacheSize(pStats.cacheSize);
                                    totalSize += item.getCacheSize();
                                    //  pStats.cacheSize;//缓存大小
                                    //pStats.dataSize;//数据大小
                                    //  pStats.codeSize;//代码大小
                                    String name = p.packageName;
                                    item.setAppName(name);
                                    items.add(item);
                                }
                                countDownLatch.countDown();
                            }
                        });
                    }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                //使用listView显示
                casheSize.setText("总缓存：" + StorageUtil.convertStorage(totalSize));
                StringBuilder sb = new StringBuilder();
                for(AppCacheItem item  :items){
                    sb.append(item.getAppName()).
                            append(StorageUtil.convertStorage(item.getCacheSize())).append(";");
                }

                appCacheInfo.setText(sb.toString());
        break;
            case R.id.cleanCache:
                //清理缓存信息
                try {
                    freeStorageAndNotify = PackageManager.class.getDeclaredMethod("freeStorageAndNotify", long.class, IPackageDataObserver.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                PackageManager packageManager = getPackageManager();
                File localData = Environment.getDataDirectory();//获取缓存数据库所在的data目录
                if (localData == null)
                    return;
                //获得指定目录的大小
                StatFs statFs = new StatFs(localData.getAbsolutePath());
                //块的个数*每块的大小
                long total = statFs.getBlockCount() * statFs.getBlockSize();
                try {
                    freeStorageAndNotify.invoke(packageManager, total, new IPackageDataObserver.Stub() {
                        @Override
                        public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {
                            casheSize.setText(packageName);
                        }
                    });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
