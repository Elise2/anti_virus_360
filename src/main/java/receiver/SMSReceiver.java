package receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.example.administrator.anti_virus_360.R;
import com.example.administrator.anti_virus_360.StopActivity;

import java.util.List;

import dao.BlackListDao;
import dao.SMSDao;
import dao.TableManager;
import entity.BlockList;
import entity.SMS;

/**
 * Created by Administrator on 2015/9/21.
 */
public class SMSReceiver extends BroadcastReceiver {
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                //解析成我们所看的短信信息，
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage.createFromPdu((byte[]) pdus[i]);//生成SmsMessage用来存储短信
                }
                //针对每一条短信进行处理
                for (SmsMessage message : messages) {
                    //首先看一下发送人是谁，是否需要拦截
                    String senderNum = message.getOriginatingAddress();//如果是发送人的话就是发送人的号码
                    BlackListDao dao = new BlackListDao(context);
                    //检测此人是否在黑名单中
                    List<BlockList> lists = dao.findValues(new String[]{TableManager.BlackListTable.COL_PHONE_NUMBER}, new String[]{senderNum});

                    if (lists.size()!=0&&lists!=null&&senderNum!=null) {
                        String messageBody = message.getMessageBody();
                        long time = System.currentTimeMillis();
                        SMS sms = new SMS(senderNum,messageBody,time+"");
                        SMSDao smsDao = new SMSDao(context);
                        //如果有，将信息插入到数据库中
                        smsDao.insertValue(sms);
                        //广播信息
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setContentTitle("骚扰信息");
                        builder.setContentText("来自" + senderNum + " 的一条信息");
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                        Intent in = new Intent(context, StopActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("type", 0);
                        in.putExtra("type", "bundle");
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,in,PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);
                        builder.setAutoCancel(true);
                        manager.notify(0,builder.build());
                        abortBroadcast();//广播到此为止，不再向下传播
                    }
                }

            }
        }
    }
}
