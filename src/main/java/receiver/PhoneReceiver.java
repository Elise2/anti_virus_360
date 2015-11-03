package receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.example.administrator.anti_virus_360.R;
import com.example.administrator.anti_virus_360.StopActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dao.BlackListDao;
import dao.PhoneDao;
import dao.TableManager;
import entity.BlockList;
import entity.Phone;

/**
 * Created by Administrator on 2015/9/26.
 */
public class PhoneReceiver extends BroadcastReceiver {
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            //来电
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);//得到电话号码
                BlackListDao dao = new BlackListDao(context);
                //查看黑名单是方法有这个人
                List<BlockList> lists = dao.findValues(new String[]{TableManager.BlackListTable.COL_PHONE_NUMBER}, new String[]{phoneNumber});
                if (lists.size() != 0 && lists != null && phoneNumber != null) {
                    ITelephony i = getTelePhony(context);
                    try {
                        i.endCall();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //插入数据库
                    long time = System.currentTimeMillis();
                    String name = lists.get(0).getName();
                    String attribution = "归属地";
                    Phone p = new Phone(attribution, time + "", name, phoneNumber);
                    PhoneDao pDao = new PhoneDao(context);
                    pDao.insetPhoneValues(p);
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setContentTitle("骚扰电话");
                    builder.setContentText("来自" + attribution + "的" + phoneNumber);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    Intent in = new Intent(context, StopActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("type", 1);
                    in.putExtra("type", "bundle");
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);
                    manager.notify(0, builder.build());

                }
            }
        }
    }

    private static ITelephony getTelePhony(Context context) {
        ITelephony iTelephony = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;

        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telephonyManager, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return iTelephony;
    }
}
