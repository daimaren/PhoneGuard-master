package cn.ixuehu.phoneguard.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.ixuehu.phoneguard.R;
import cn.ixuehu.phoneguard.domain.ContactInfo;
import cn.ixuehu.phoneguard.utils.ContactInfoParser;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/2/28.
 */
public class FriendsActvitity extends ListActivity{
    private List<ContactInfo> datas;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        datas = ContactInfoParser.findAll(FriendsActvitity.this);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //bundle传递数据
                String phonenumber = datas.get(i).getPhone();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("phonenumber",phonenumber);
                intent.putExtras(bundle);
                setResult(0,intent);
                finish();
            }
        });
    }
    class MyAdapter extends BaseAdapter{
        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return datas.size();
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
            if (view != null)
            {
                viewHolder = new ViewHolder();
                view = View.inflate(FriendsActvitity.this, R.layout.item_contacts,null);
                viewHolder.tvName = (TextView) findViewById(R.id.tv_contacts_item_name);
                viewHolder.tvPhone = (TextView) findViewById(R.id.tv_contacts_item_phone);
                view.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //赋值
            ContactInfo contactInfo = datas.get(i);
            viewHolder.tvName.setText(contactInfo.getName());
            viewHolder.tvPhone.setText(contactInfo.getPhone());
            return view;
        }

    }
    class ViewHolder{
        TextView tvName;
        TextView tvPhone;
    }

}
