package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.MyConstants;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public class LostFindActivity extends Activity{
    private TextView textView;
    private ImageView imageView;
    private SharedPreferences sp;
    private LinearLayout ll_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //判断是否设置过向导
        sp = getSharedPreferences(MyConstants.SP_NAME,MODE_PRIVATE);
        boolean isSet = sp.getBoolean(MyConstants.ISSET, false);
        if (isSet){
            //设置过，显示主界面
            initView();
            initData();
        }
        else{
            //显示向导界面第一步
            startSetup1Activity();
        }
    }

    private void startSetup1Activity() {
        Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
        startActivity(intent);
        finish();
    }

    private void initData() {
        // 显示安全号码
        textView.setText(sp.getString(MyConstants.SAFEPHONE, ""));
    }

    private void initView() {
        setContentView(R.layout.activity_lostfind);
        textView = (TextView) findViewById(R.id.tv_lostfind_safenumber);
        imageView = (ImageView) findViewById(R.id.iv_lostfind_lock);
        ll_menu = (LinearLayout) findViewById(R.id.ll_lostfind_menu);
    }
    /**
     * 重新进入设置向导
     */
    public void entersetup(View v) {
        startSetup1Activity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("");
        return super.onCreateOptionsMenu(menu);
    }
    private boolean isMenuUp = false;
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        isMenuUp = !isMenuUp;
        if (isMenuUp){
            ll_menu.setVisibility(View.VISIBLE);
        }else {
            ll_menu.setVisibility(View.GONE);
        }
        return false;
    }
}
