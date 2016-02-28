package cn.ixuehu.phoneguard.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.MyConstants;
import cn.ixuehu.phoneguard.utils.ShowToast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public class Setup2Activity extends BaseSetupActivity{
    private SharedPreferences sp;
    private ImageView ivLock;
    private TelephonyManager telephonyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(MyConstants.SP_NAME, MODE_PRIVATE);
        initView();
        initData();
    }

    private void initData() {
        //首先判断是否已绑定
        if (isBindSim()){
            //已绑定
            ivLock.setImageResource(R.drawable.lock);
        }
        else {
            //未绑定
            ivLock.setImageResource(R.drawable.unlock);
        }
    }

    private void initView() {
        setContentView(R.layout.activy_setup2);
        ivLock = (ImageView) findViewById(R.id.iv_setup2_lock);
        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    @Override
    public void next() {
        if (!isBindSim()){
            ShowToast.show(Setup2Activity.this,"请绑定sim卡");
            return;
        }
        startActivityAndFinish(Setup3Activity.class);

    }

    @Override
    public void pre() {
        startActivityAndFinish(Setup1Activity.class);
    }

    public void bindSim(View view)
    {
        //首先判断是否已绑定
        if (isBindSim()){
            //已绑定,解绑
            ivLock.setImageResource(R.drawable.unlock);
            sp.edit().putString(MyConstants.SIM,"").commit();
        }
        else {
            //未绑定,绑定
            //获取手机号码
            String simSerialNumber = telephonyManager.getSimSerialNumber();
            ivLock.setImageResource(R.drawable.lock);
            sp.edit().putString(MyConstants.SIM, simSerialNumber);
        }
    }
    private boolean isBindSim()
    {
        boolean res = false;
        String sim = sp.getString(MyConstants.SIM, "");
        if (!TextUtils.isEmpty(sim)){
            res = true;
        }
        return res;
    }
}
