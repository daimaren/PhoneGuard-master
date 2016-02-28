package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.ixuehu.phoneguard.R;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public abstract class BaseSetupActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void next(View view){
        next();
        //动画效果
        overridePendingTransition(R.drawable.next_in,R.drawable.next_out);
    }
    public abstract void next();
    public abstract void pre();
    public void pre(View view){
        pre();
        //动画效果
        overridePendingTransition(R.drawable.pre_in,R.drawable.pre_out);
    }

    /**
     * 启动Activity
     * @param type
     */
    public void startActivityAndFinish(Class type)
    {
        Intent intent = new Intent(BaseSetupActivity.this,type);
        startActivity(intent);
        finish();
    }

}
