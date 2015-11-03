package entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/9/25.
 */
public class APPDataItem {
    private String appName;
    private Drawable icon;
    private String packgeName;
    private  int uid;
    private   long  mobileData;
    private long wifiData;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackgeName() {
        return packgeName;
    }

    public void setPackgeName(String packgeName) {
        this.packgeName = packgeName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getMobileData() {
        return mobileData;
    }

    public void setMobileData(long mobileData) {
        this.mobileData = mobileData;
    }

    public long getWifiData() {
        return wifiData;
    }

    public void setWifiData(long wifiData) {
        this.wifiData = wifiData;
    }

    public APPDataItem(String appName, Drawable icon, String packgeName, int uid, long mobileData, long wifiData) {
        this.appName = appName;
        this.icon = icon;
        this.packgeName = packgeName;
        this.uid = uid;
        this.mobileData = mobileData;
        this.wifiData = wifiData;
    }

    public APPDataItem() {
    }
}
