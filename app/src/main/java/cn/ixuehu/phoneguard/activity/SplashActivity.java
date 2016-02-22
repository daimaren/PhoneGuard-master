package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;

public class SplashActivity extends Activity {
    private TextView textView;
    private RelativeLayout relativeLayout;
    private PackageManager packageManager;
    private int versionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initAnim();
    }
    /**
     * 初始化view
     */
    private void initView()
    {
        setContentView(R.layout.activity_splash);
        //初始化控件
        textView = (TextView) findViewById(R.id.tv_splash_versionname);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_splash_root);
        packageManager = getPackageManager();
    }
    /**
     * 初始化data
     */
    private void initData()
    {

        try {
            String packageName = getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            textView.setText(packageInfo.versionName);

            versionCode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化动画
     */
    private void initAnim()
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        relativeLayout.setAnimation(alphaAnimation);
    }

}
