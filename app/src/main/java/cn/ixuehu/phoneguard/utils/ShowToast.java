package cn.ixuehu.phoneguard.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.utils
 * Created by daimaren on 2016/2/23.
 */
public class ShowToast {
    public static void show(final Activity activity,final String string)
    {
        //判断处于主线程还是子线程
        if (Thread.currentThread().getName().equals("main"))
        {
            //直接显示
            Toast.makeText(activity,string,1).show();
        }
        else
        {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,string,1).show();
                }
            });
        }
    }
}
