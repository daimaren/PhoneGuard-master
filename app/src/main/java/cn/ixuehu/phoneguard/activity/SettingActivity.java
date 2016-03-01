package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.MyConstants;
import cn.ixuehu.phoneguard.view.SettingView;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/28.
 */
public class SettingActivity extends Activity{
    private SharedPreferences sp;
    private SettingView sv_auto_update;
    private SettingView sv_black_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(MyConstants.SP_NAME,MODE_PRIVATE);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        if (sp.getBoolean(MyConstants.ISCHECKVERSION, false)) {
            sv_auto_update.setChecked(true);
        }
        else {
            sv_auto_update.setChecked(false);
        }
    }

    private void initEvent() {
        sv_auto_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv_auto_update.setChecked(!sv_auto_update.getChecked());
                if (sv_auto_update.getChecked()){
                    sp.edit().putBoolean(MyConstants.ISCHECKVERSION,true).commit();
                }else {
                    sp.edit().putBoolean(MyConstants.ISCHECKVERSION,false).commit();
                }
                //写入sp

            }
        });
        sv_black_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv_black_list.setChecked(!sv_black_list.getChecked());
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_settingview);
        sv_auto_update = (SettingView) findViewById(R.id.sv_setting_auto_update);
        sv_black_list = (SettingView) findViewById(R.id.sv_setting_black_list);
    }

}
