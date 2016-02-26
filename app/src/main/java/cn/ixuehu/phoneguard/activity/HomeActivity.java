package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ixuehu.phoneguard.R;

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

    private void initEvent() {

    }

    private void initData() {
        gvAdapter = new GVAdapter();
        gv_jiugongge.setAdapter(gvAdapter);
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
