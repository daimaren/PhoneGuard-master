package cn.ixuehu.phoneguard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.service.SmsReceiverService;
import cn.ixuehu.phoneguard.utils.MyConstants;
import cn.ixuehu.phoneguard.utils.ShowToast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public class Setup4Activity extends BaseSetupActivity{
    private CheckBox checkBox;
    private SharedPreferences sp;
    private TextView textView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(MyConstants.SP_NAME, MODE_PRIVATE);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sp.edit().putBoolean(MyConstants.ISLOCK,b).commit();
                if (b){
                    textView.setText("防盗保护已经开启");
                    //启动拦截短信Service
                    intent = new Intent(Setup4Activity.this, SmsReceiverService.class);
                    startService(intent);
                }else {
                    textView.setText("防盗保护没有开启");
                    //关闭拦截短信Service
                    stopService(intent);
                }
            }
        });
    }

    private void initData() {
        boolean isLock = sp.getBoolean(MyConstants.ISLOCK, false);
        if (isLock){
            checkBox.setChecked(isLock);
            textView.setText("防盗保护已经开启");
        }
        else {
            checkBox.setChecked(isLock);
            textView.setText("防盗保护没有开启");
        }

    }

    private void initView() {
        setContentView(R.layout.activy_setup4);
        checkBox = (CheckBox) findViewById(R.id.cb_setup4_islock);
        textView = (TextView) findViewById(R.id.tv_setup4_desc);
    }

    @Override
    public void next() {
        //设置完成
        if (sp.getBoolean(MyConstants.ISLOCK, false)){
            ShowToast.show(Setup4Activity.this, "请先打钩");
            return;
        }
        else {
            //ShowToast.show(Setup4Activity.this, "设置完成");
            sp.edit().putBoolean(MyConstants.ISSET,true).commit();
            startActivityAndFinish(LostFindActivity.class);
        }
    }

    @Override
    public void pre() {
        startActivityAndFinish(Setup3Activity.class);
    }
}
