package cn.ixuehu.phoneguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import cn.ixuehu.phoneguard.utils.MyConstants;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.receiver
 * Created by daimaren on 2016/2/28.
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    private SharedPreferences sp;
    private TelephonyManager telephonyManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        //判断是否更换sim卡
        sp = context.getSharedPreferences(MyConstants.SP_NAME, Context.MODE_PRIVATE);
        String oldSim = sp.getString(MyConstants.SIM, "") + "1";
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String newSim = telephonyManager.getSimSerialNumber();
        if (oldSim.equals(newSim)){

        }
        else {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(sp.getString(MyConstants.SAFEPHONE,"110"),null,"我是小偷,这是我的新号",null,null);
        }
    }
}
