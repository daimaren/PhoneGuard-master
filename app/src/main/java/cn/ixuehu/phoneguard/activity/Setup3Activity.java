package cn.ixuehu.phoneguard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.MyConstants;
import cn.ixuehu.phoneguard.utils.ShowToast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/27.
 */
public class Setup3Activity extends BaseSetupActivity{
    private SharedPreferences sp;
    private EditText etSafeNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences(MyConstants.SP_NAME, MODE_PRIVATE);
        initView();
        initData();
    }

    private void initData() {
        String phoneNum = sp.getString(MyConstants.SAFEPHONE, "");
        etSafeNum.setText(phoneNum);
    }

    private void initView() {
        setContentView(R.layout.activy_setup3);
        etSafeNum = (EditText) findViewById(R.id.et_setup3_safenumber);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            String phonenumber = data.getExtras().getString("phonenumber");
            etSafeNum.setText(phonenumber);
            //sp.edit().putString(MyConstants.SAFEPHONE,phonenumber).commit();
        }
    }

    @Override
    public void pre() {
        startActivityAndFinish(Setup4Activity.class);
    }

    @Override
    public void next() {
        //判断安全号码是否填写
        String strSafeNum = etSafeNum.getText().toString().trim();
        if (TextUtils.isEmpty(strSafeNum)){
            //没填写
            ShowToast.show(Setup3Activity.this,"安全号码不能为空");
        }
        else {
            sp.edit().putString(MyConstants.SAFEPHONE,strSafeNum).commit();
        }
        startActivityAndFinish(Setup2Activity.class);
    }

    /**
     * 选择安全号码点击事件
     * @param view
     */
    public void selectPhone(View view){
        //启动联系人界面
        Intent intent = new Intent(Setup3Activity.this,FriendsActvitity.class);
        startActivityForResult(intent,0);
    }

}
