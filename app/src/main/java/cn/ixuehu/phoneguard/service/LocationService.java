package cn.ixuehu.phoneguard.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;

import java.security.Permission;
import java.util.jar.Manifest;

import cn.ixuehu.phoneguard.utils.MyConstants;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.service
 * Created by daimaren on 2016/2/28.
 */
public class LocationService extends Service{
    private LocationManager lm;
    private LocationListener locationListener;
    private SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences(MyConstants.SP_NAME,MODE_PRIVATE);
        //获取手机位置，发送到安全号码
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        //创建监听器
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //位置改变
                float accuracy = location.getAccuracy();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                double altitude = location.getAltitude();
                SmsManager smsManager = SmsManager.getDefault();
                String res = "";
                res += "accuracy:" + accuracy + "\n";
                res += "latitude:" + latitude + "\n";
                res += "longitude:"+ longitude + "\n";
                res += "altitude:" + altitude + "\n";
                smsManager.sendTextMessage(sp.getString(MyConstants.SAFEPHONE,"110"),null,res,null,null);
                stopSelf();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        lm.requestLocationUpdates("gps", 0, 0, locationListener);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.removeUpdates(locationListener);
        locationListener = null;
    }
}
