package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dao.PhoneDao;
import entity.Phone;

public class PhoneStartChangedReceiver extends BroadcastReceiver {
	private List<String> blackNumberlist;
	private List<Phone> phones;
	@Override
	public void onReceive(Context context, Intent intent) {
		PhoneDao phoneDao = new PhoneDao(context);
		phones = new ArrayList<>();
		blackNumberlist = new ArrayList<>();
		phones = phoneDao.findAll();
		if (phones!=null){
			for (Phone phone:phones){
				blackNumberlist.add(phone.getPhoneNumber());
			}
		}
		// TODO Auto-generated method stub
		//
		// Intent.ACTION_NEW_OUTGOING_CALL拨打电话时的系统广播
		// TelephonyManager.ACTION_PHONE_STATE_CHANGED//来电时的广播
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			// 去电
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		}else if (intent.getAction().equals(
				TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
			// 来电
			String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				// TelephonyManager.EXTRA_STATE_IDLE
				// TelephonyManager.EXTRA_STATE_OFFHOOK
				String phoneNumber = intent
						.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				if (null!=blackNumberlist&&null!=phoneNumber){
					for (String phoneName:blackNumberlist) {
						phoneName.endsWith(phoneNumber);
						//静音设置
						//挂断电话
						ITelephony iTelephony = getItelephony(context);
						try {
							iTelephony.endCall();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}

			} else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

			} else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				
			}
		}

	}
	private  static  ITelephony getItelephony(Context context){
		ITelephony iTelephony = null;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		Class<TelephonyManager> c = TelephonyManager.class;
		Method getITelephoneyMethod = null;
		try {
			getITelephoneyMethod = c.getDeclaredMethod("getITelephony",(Class[])null);
			getITelephoneyMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
		}

		try {
			iTelephony = (ITelephony)getITelephoneyMethod.invoke(telephonyManager,(Object[])null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
		}
		return  iTelephony;
	}

}
