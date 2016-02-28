package cn.ixuehu.phoneguard.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import cn.ixuehu.phoneguard.R;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public class Setup1Activity extends BaseSetupActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activy_setup1);
    }

    @Override
    public void pre() {

    }

    @Override
    public void next() {
        startActivityAndFinish(Setup2Activity.class);
    }
}
