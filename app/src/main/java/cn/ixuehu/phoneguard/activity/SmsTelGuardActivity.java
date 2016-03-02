package cn.ixuehu.phoneguard.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
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
public class SmsTelGuardActivity extends Activity{
    protected static final int LOADING = 1;
    protected static final int LOADFINISH = 2;
    private LinearLayout ll_loading;
    private ListView lv_datas;
    private TextView tv_nodata;
    private BlackNumberDao blackNumberDao;
    private List<BlackNameData> datas;
    private MyAdapter myAdapter;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        lv_datas.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_FLING://惯性滑动
                        break;
                    case SCROLL_STATE_IDLE://静止
                        //判断是否到最后
                        int lastVisiblePosition = absListView.getLastVisiblePosition();
                        if (lastVisiblePosition == datas.size() - 1) {
                            ShowToast.show(SmsTelGuardActivity.this, "没有更多数据");
                            return;
                        } else {
                            initData();
                        }
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL://滑动
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case LOADING:
                    ll_loading.setVisibility(View.VISIBLE);
                    break;
                case LOADFINISH:
                    ll_loading.setVisibility(View.GONE);
                    if (datas.size() != 0){
                        tv_nodata.setVisibility(View.VISIBLE);
                        lv_datas.setVisibility(View.GONE);
                    }else {
                        lv_datas.setVisibility(View.VISIBLE);
                        tv_nodata.setVisibility(View.GONE);
                        lv_datas.deferNotifyDataSetChanged();
                    }
                    break;
            }
            return false;
        }
    });

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取数据
                Message msg = handler.obtainMessage();
                msg.what = LOADING;
                handler.sendMessage(msg);

                List<BlackNameData> blackNameDatas = blackNumberDao.getData(datas.size() + "",
                        20 + "");
                datas.addAll(blackNameDatas);
                //延时
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

    private void initView() {
        setContentView(R.layout.activity_smsguard);
        ll_loading = (LinearLayout) findViewById(R.id.ll_black_loading);
        lv_datas = (ListView) findViewById(R.id.lv_black_datas);
        tv_nodata = (TextView) findViewById(R.id.tv_black_nodata);

        datas = new ArrayList<BlackNameData>();
        blackNumberDao = new BlackNumberDao(this);
        myAdapter = new MyAdapter();
        lv_datas.setAdapter(myAdapter);
    }
    private class MyAdapter extends BaseAdapter{
        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return 0;
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
            //赋值
            final BlackNameData data= datas.get(i);

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
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(SmsTelGuardActivity.this);
                    ab.setTitle("提醒");
                    ab.setMessage("您是否真的删除 :" + data.getBlackNumber() + "?");
                    ab.setPositiveButton("真删", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            deleteBlackNumber(which);
                        }
                    });
                    ab.setNegativeButton("取消", null );
                    ab.show();
                }
            });
            return view;
        }
    }
    private class ViewHolder{
        TextView tv_blackNumber;
        TextView tv_mode;
        ImageView iv_delete;
    }
    private void deleteBlackNumber(int position){
        blackNumberDao.remove(datas.get(position));
        datas.remove(position);
        lv_datas.deferNotifyDataSetChanged();
    }
}
