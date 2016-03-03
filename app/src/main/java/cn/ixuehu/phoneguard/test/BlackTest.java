package cn.ixuehu.phoneguard.test;

import android.test.AndroidTestCase;

import cn.ixuehu.phoneguard.db.dao.BlackNumberDao;
import cn.ixuehu.phoneguard.domain.BlackNameData;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.activity
 * Created by daimaren on 2016/3/1.
 */
public class BlackTest extends AndroidTestCase {
    public void testAdd(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        for (int i=0;i<200;i++)
        {
            BlackNameData data = new BlackNameData("110" + i,BlackNameData.PHONE);
            dao.add(data);
        }
    }
    public void testFindAll(){
        BlackNumberDao dao = new BlackNumberDao(getContext());
        System.out.println(dao.findAll());
    }
}
