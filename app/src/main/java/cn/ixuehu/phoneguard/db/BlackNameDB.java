package cn.ixuehu.phoneguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.db
 * Created by daimaren on 2016/3/1.
 */
public class BlackNameDB extends SQLiteOpenHelper{
    public BlackNameDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("delete  from blackname_tb");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table blackname_tb(_id integer primary key autoincrement,blacknumber text,mode integer)");
    }
}
