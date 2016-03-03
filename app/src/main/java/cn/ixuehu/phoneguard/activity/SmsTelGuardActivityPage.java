package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.db.dao.BlackNumberDao;
import cn.ixuehu.phoneguard.domain.BlackNameData;
import cn.ixuehu.phoneguard.utils.ShowToast;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/3/1.
 */
public class SmsTelGuardActivityPage extends Activity{
    protected static final int LOADING = 1;
    protected static final int LOADFINISH = 2;
    private LinearLayout ll_loading;
    private ListView lv_datas;
    private TextView tv_nodata;
    private BlackNumberDao dao;// 取数据
    private MyAdapter myAdapter;
    private List<BlackNameData> datas = new ArrayList<BlackNameData>();

    private EditText et_jumppage;
    private TextView tv_pagemess;
    private int currentPage = 1;
    private int totalPage;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取第currentPage页数据
                Message msg = handler.obtainMessage();
                msg.what = LOADING;
                handler.sendMessage(msg);
                totalPage = dao.getPages();
                datas = dao.getPageData(currentPage);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg = handler.obtainMessage();
                msg.what = LOADFINISH;
                handler.sendMessage(msg);
            }
        }).start();
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case LOADING:// 正在加载数据
                    ll_loading.setVisibility(View.VISIBLE);
                    break;
                case LOADFINISH:// 数据加载完成
                    //显示页数
                    tv_pagemess.setText(currentPage + "/" + totalPage);
                    ll_loading.setVisibility(View.GONE);
                    if (datas.size() == 0){
                        tv_nodata.setVisibility(View.VISIBLE);
                        lv_datas.setVisibility(View.GONE);
                    }else {
                        lv_datas.setVisibility(View.VISIBLE);
                        tv_nodata.setVisibility(View.GONE);
                        myAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    private void initView() {
        setContentView(R.layout.activity_smsguard_page);

        ll_loading = (LinearLayout) findViewById(R.id.ll_black_loading);
        lv_datas = (ListView) findViewById(R.id.lv_black_datas);
        tv_nodata = (TextView) findViewById(R.id.tv_black_nodata);
        myAdapter = new MyAdapter();
        lv_datas.setAdapter(myAdapter);

        et_jumppage = (EditText) findViewById(R.id.et_black_jumppage);
        tv_pagemess = (TextView) findViewById(R.id.tv_black_pagemess);
    }
    private class MyAdapter extends BaseAdapter{
        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getApplicationContext(),
                        R.layout.item_blackname_datas, null);
                holder = new ViewHolder();
                holder.tv_blackNumber = (TextView) view
                        .findViewById(R.id.tv_black_number);
                holder.tv_mode = (TextView) view
                        .findViewById(R.id.tv_black_mode);
                holder.iv_delete = (ImageView) view
                        .findViewById(R.id.iv_black_delete);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            BlackNameData data = datas.get(i);
            // 设置数据
            holder.tv_blackNumber.setText(data.getBlackNumber());
            switch (data.getMode()) {
                case BlackNameData.SMS:
                    holder.tv_mode.setText("短信拦截");
                    break;
                case BlackNameData.PHONE:
                    holder.tv_mode.setText("电话拦截");
                    break;
                case BlackNameData.ALL:
                    holder.tv_mode.setText("全部拦截");
                    break;

                default:
                    holder.tv_mode.setText("不拦截");
                    break;
            }

            return view;
        }
        private class ViewHolder{
            TextView tv_blackNumber;
            TextView tv_mode;
            ImageView iv_delete;
        }
        public void prev(View v){
            if (currentPage == 1 ){
                ShowToast.show(SmsTelGuardActivityPage.this, "已经是第一页");
            }
            else {
                currentPage--;
                initData();
            }
        }
        public void next(View v){
            if (currentPage == totalPage ){
                ShowToast.show(SmsTelGuardActivityPage.this, "已经是最后一页");
            }
            else {
                currentPage++;
                initData();
            }
        }
        public void jump(View v){
            String jumpPage = et_jumppage.getText().toString().trim();
            int i = Integer.parseInt(jumpPage);
            if (i <= 0 || i > totalPage){
                ShowToast.show(SmsTelGuardActivityPage.this, "请按套路出牌");
            }
            else {
                currentPage = i;
                initData();
            }
        }
    }
}
