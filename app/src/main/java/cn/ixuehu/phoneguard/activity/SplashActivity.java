package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.domain.UrlData;
import cn.ixuehu.phoneguard.utils.ShowToast;
import cn.ixuehu.phoneguard.utils.Stream2String;

public class SplashActivity extends Activity {
    private static final int UPDATE_VERSION = 1;
    private static final int LOAD_MAIN = 2;
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
        checkVersion();
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what)
            {
                case UPDATE_VERSION:
                    final UrlData urlData = (UrlData) message.obj;
                    //弹出对话框，提示是否更新
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {

                        }
                    });
                    builder.setTitle("新版本").setMessage(urlData.getDesc()).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadMain();
                        }
                    }).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            downloadAndInstall(urlData.getDownUrl());
                        }
                    });
                    builder.show();
                    break;
                case LOAD_MAIN:
                    //加载主界面
                    loadMain();
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    private void loadMain()
    {

    }

    /**
     * 下载并安装apk
     * @param downloadUrl
     */
    private void downloadAndInstall(String downloadUrl)
    {

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
    /**
     * 检查版本
     */
    private void checkVersion()
    {
        Message message = handler.obtainMessage();
        long startTime = System.currentTimeMillis();
        //访问网络，检查版本
        try {
            URL url = new URL(getResources().getString(R.string.url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(3000);
            int responseCode = httpURLConnection.getResponseCode();
            //200成功  404找不到
            if (responseCode == 200)
            {
                //获取输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //流转换为string
                String res = Stream2String.process(inputStream);
                //Json解析
                UrlData urlData = parseJson(res);
                //判断版本号
                if (urlData.getVersionCode() != versionCode)
                {
                    //发送Msg
                    message.what = UPDATE_VERSION;
                    message.obj = urlData;
                }
            }
            else
            {
                //显示土司，提示连接失败
                ShowToast.show(SplashActivity.this,"2000 连接失败");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            ShowToast.show(SplashActivity.this, "2001 io错误,网络超时\"");
        } catch (JSONException e) {
            e.printStackTrace();
            ShowToast.show(SplashActivity.this, "2002 json数据格式错误");
        }finally {
            if (message.what == UPDATE_VERSION)
            {

            }
            else
            {
                message.what = LOAD_MAIN;
            }
            //判断是否需要延迟2秒
            long endTime = System.currentTimeMillis();
            if ((endTime - startTime) < 2000){
                SystemClock.sleep(2000 - (endTime - startTime));
            }
            message.sendToTarget();
        }

    }
    /**
     * 解析Json数据
     */
    private UrlData parseJson(String res) throws JSONException
    {
        UrlData urlData = new UrlData();
        JSONObject jsonObject = new JSONObject(res);
        urlData.setVersionCode(jsonObject.getInt("versionCode"));
        urlData.setDesc(jsonObject.getString("desc"));
        urlData.setDownUrl(jsonObject.getString("downloadUrl"));
        return urlData;
    }

}
