package cn.ixuehu.phoneguard.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.view
 * Created by daimaren on 2016/2/29.
 */
public class SettingView extends LinearLayout{
    private TextView tvTitle;
    private TextView tvContent;
    private CheckBox checkBox;
    private final String title;
    private final String content;
    private final String[] desc;
    private View rl_root;

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rl_root = inflate(context, R.layout.item_setting_view,this);
        tvTitle = (TextView) findViewById(R.id.tv_setting_item_title);
        tvContent = (TextView) findViewById(R.id.tv_setting_item_content);
        checkBox = (CheckBox) findViewById(R.id.cb_settting_item_check);

        title = attrs.getAttributeValue("http://schemas.android.com/apk/res/cn.ixuehu.phoneguard", "title");
        content = attrs.getAttributeValue("http://schemas.android.com/apk/res/cn.ixuehu.phoneguard","content");
        desc = content.split(",");
        initData();
    }
    private void initData(){
        tvTitle.setText(title);
        tvContent.setText(desc[0]);
        tvContent.setTextColor(Color.RED);
    }
    public void setOnClickListener(OnClickListener onClickListener){
        rl_root.setOnClickListener(onClickListener);
    }

    /**
     * 设置自定义view的状态
     * @param checked
     */
    public void setChecked(boolean checked){
        if (checked){
            //设置颜色和选中
            tvContent.setTextColor(Color.GREEN);
            tvContent.setText(desc[1]);
        }
        else {
            tvContent.setTextColor(Color.RED);
            tvContent.setText(desc[0]);
        }
        checkBox.setChecked(checked);
    }
    public boolean getChecked(){
        return checkBox.isChecked();
    }
}
