package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.utils.Md5Utils;
import cn.ixuehu.phoneguard.utils.MyConstants;
import cn.ixuehu.phoneguard.utils.ShowToast;

public class HomeActivity extends Activity {
    private GridView gv_jiugongge;
    private String[] names = new String[]{"手机防盗","通讯卫士","软件管家"
            ,"进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
    private int[] icons = new int[]{R.drawable.safe,R.drawable.callmsgsafe,R.drawable.icon_selector
            ,R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
            R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};
    private GVAdapter gvAdapter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }
    private boolean isSetPass()
    {
        boolean res = false;

        String string = sp.getString(MyConstants.PASSWD, "");
        if (!TextUtils.isEmpty(string)){
            //不存在
            res = true;
        }
        return res;
    }
    private void loadLostFind(){

    }
    private Dialog dialog;
    /**
     * 显示设置密码对话框
     */
    private void showSetPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View view = View.inflate(HomeActivity.this,R.layout.dialog_setpasswd,null);
        final EditText et_passone = (EditText) view.findViewById(R.id.et_dialog_passone);
        final EditText et_passtwo = (EditText) view.findViewById(R.id.et_dialog_passtwo);
        Button bt_set = (Button) view.findViewById(R.id.bt_dialog_setpass);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_dialog_cancel);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passone = et_passone.getText().toString().trim();
                String passtwo = et_passtwo.getText().toString().trim();
                if (TextUtils.isEmpty(passone) || TextUtils.isEmpty(passtwo)){
                    ShowToast.show(HomeActivity.this, "密码不能为空");
                    return;
                }
                //判断是否一致
                if (passone.equals(passtwo)){
                    //写入sp
                    sp.edit().putString(MyConstants.PASSWD,Md5Utils.Md5Encode(passone)).commit();
                    ShowToast.show(HomeActivity.this, "密码设置成功");
                    dialog.dismiss();
                }
                else {
                    ShowToast.show(HomeActivity.this, "两次密码不一致");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    /**
     * 显示输入密码对话框
     */
    private void showInputPassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        View view = View.inflate(HomeActivity.this,R.layout.dialog_inputpasswd,null);
        final EditText et_passone = (EditText) view.findViewById(R.id.et_dialog_passone);
        Button bt_set = (Button) view.findViewById(R.id.bt_dialog_setpass);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_dialog_cancel);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passone = et_passone.getText().toString().trim();
                if (TextUtils.isEmpty(passone)){
                    ShowToast.show(HomeActivity.this, "密码不能为空");
                    return;
                }
                if (Md5Utils.Md5Encode(passone).equals(sp.getString(MyConstants.PASSWD, ""))){
                    loadLostFind();
                    dialog.dismiss();
                }
                else {
                    ShowToast.show(HomeActivity.this, "密码不正确");
                }

            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void initEvent() {
        gv_jiugongge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0://手机防盗
                        //判断是否设置过密码
                        if (isSetPass())
                        {
                            //输入密码，进入防盗页面
                            showInputPassDialog();
                        }
                        else
                        {
                            //对话框设置密码
                            showSetPassDialog();
                        }
                }
            }
        });
    }
    private void initData() {
        gvAdapter = new GVAdapter();
        gv_jiugongge.setAdapter(gvAdapter);
        sp = getSharedPreferences(MyConstants.SP_NAME, MODE_PRIVATE);
    }

    private void initView() {
        setContentView(R.layout.activity_home);
        gv_jiugongge = (GridView) findViewById(R.id.gv_home_jiugongge);
    }
    private class GVAdapter extends BaseAdapter{
        public GVAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null)
            {
                view = View.inflate(getApplicationContext(),R.layout.item_home_gradview,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) findViewById(R.id.iv_gradview_item_icon);
                viewHolder.textView = (TextView) findViewById(R.id.tv_gradview_item_name);
                view.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //赋值
            viewHolder.imageView.setBackgroundResource(icons[i]);
            viewHolder.textView.setText(names[i]);
            return view;
        }
    }
    private class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }

}
