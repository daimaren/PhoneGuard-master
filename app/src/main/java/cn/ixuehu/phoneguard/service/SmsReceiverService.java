package cn.ixuehu.phoneguard.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import cn.ixuehu.phoneguard.receiver.SmsReceiver;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.service
 * Created by daimaren on 2016/2/29.
 */
public class SmsReceiverService extends Service{
    private SmsReceiver smsReceiver;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册IntentFilter
        smsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(smsReceiver);
        smsReceiver = null;
        super.onDestroy();
    }
}
