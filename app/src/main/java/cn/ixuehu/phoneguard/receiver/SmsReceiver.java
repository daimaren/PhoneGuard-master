package cn.ixuehu.phoneguard.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.service.LocationService;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.receiver
 * Created by daimaren on 2016/2/28.
 */
public class SmsReceiver extends BroadcastReceiver{
    private DevicePolicyManager mDPM;
    @Override
    public void onReceive(Context context, Intent intent) {
        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        //从intent里取出短信和内容
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            Object[] datas = (Object[]) bundle.get("pdus");
            //遍历
            for (Object d:datas){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) d);
                String messageBody = smsMessage.getDisplayMessageBody();
                if (messageBody.equals("#*music*#")){
                    // 播放音乐
                    System.out.println(messageBody);
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.qqqg);
                    mediaPlayer.setVolume(1,1);
                    mediaPlayer.start();
                    abortBroadcast();

                }else if (messageBody.equals("#*gps*#")){
                    //获取手机位置发短信，记得关闭
                    Intent location = new Intent(context, LocationService.class);
                    context.startService(location);
                    abortBroadcast();
                }
                else if (messageBody.equals("#*wipedata*#")){
                    //远程清除数据
                    mDPM.wipeData(mDPM.WIPE_EXTERNAL_STORAGE);
                    abortBroadcast();
                }
                else if (messageBody.equals("#*lockscreen*#")){
                    // 远程锁屏
                    mDPM.resetPassword("123",0);
                    mDPM.lockNow();
                    abortBroadcast();
                }
            }
        }

    }
}
