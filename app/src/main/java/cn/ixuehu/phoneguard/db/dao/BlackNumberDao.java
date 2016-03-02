package cn.ixuehu.phoneguard.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.ixuehu.phoneguard.db.BlackNameDB;
import cn.ixuehu.phoneguard.domain.BlackNameData;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.db.dao
 * Created by daimaren on 2016/3/1.
 */
public class BlackNumberDao {
    private BlackNameDB mydb;

    public BlackNumberDao(Context context) {
        mydb = new BlackNameDB(context, "blackname.db", null, 3);
    }
    public List<BlackNameData> getData(String startIndex,String numbers){
        SQLiteDatabase db = mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + BlackNameData.BLACKNUMBER + ","
                        + BlackNameData.MODE + "   from " + BlackNameData.TABLENAME + " order by _id desc  limit ?, ?" ,
                new String[]{startIndex,numbers});
        List<BlackNameData> datas = new ArrayList<BlackNameData>();
        while (cursor.moveToNext()){
            int mode = cursor.getInt(1);;
            String blackNumber = cursor.getString(0);
            BlackNameData data = new BlackNameData(blackNumber, mode );
            datas.add(data);
        }
        db.close();
        return datas;

    }
    /**
     * -1添加数据部成功
     * @param data
     * @return
     */
    public int add(BlackNameData data){
        int res = 0;
        SQLiteDatabase database = mydb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BlackNameData.BLACKNUMBER,data.getBlackNumber());
        contentValues.put(BlackNameData.MODE,data.getMode());
        res = (int) database.insert(BlackNameData.TABLENAME,null,contentValues);
        database.close();
        return res;
    }
    /**
     * 需求
     *    1， 记录删除掉
     *    2， 修改mode
     */
    public boolean  remove(String blackNumber){
        boolean res = false;
        SQLiteDatabase db = mydb.getWritableDatabase();
        int rowNumber = db.delete(BlackNameData.TABLENAME, BlackNameData.BLACKNUMBER + " = ? ", new String[]{blackNumber});
        if (rowNumber != 0){
            res = true;
        }
        db.close();
        return res;
    }


    /**
     *
     * @param pageIndex
     *     第几页
     * @return
     *    当前页的数据
     */
    public List<BlackNameData> getPageData(int pageIndex){
        SQLiteDatabase db = mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + BlackNameData.BLACKNUMBER + ","
                        + BlackNameData.MODE + "   from " + BlackNameData.TABLENAME + " limit ?, " + BlackNameData.PERPAGE,
                new String[]{(pageIndex - 1) * BlackNameData.PERPAGE + ""});
        List<BlackNameData> datas = new ArrayList<BlackNameData>();
        while (cursor.moveToNext()){
            int mode = cursor.getInt(1);;
            String blackNumber = cursor.getString(0);
            BlackNameData data = new BlackNameData(blackNumber, mode);
            datas.add(data);
        }
        db.close();
        return datas;

    }

    public int getPages(){
        return (int)Math.ceil(getTotal() * 1.0/ BlackNameData.PERPAGE);
    }

    /**
     * 数据的个数
     * @return
     */
    public int getTotal(){
        SQLiteDatabase db = mydb.getReadableDatabase();
        Cursor rawQuery = db.rawQuery("select count(1) from " + BlackNameData.TABLENAME , null);
        rawQuery.moveToNext();
        int rows = rawQuery.getInt(0);
        db.close();
        return rows;

    }

    /**
     * 查询所有数据
     * @return
     */
    public List<BlackNameData> findAll(){
        SQLiteDatabase db = mydb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + BlackNameData.BLACKNUMBER + ","
                + BlackNameData.MODE + "   from " + BlackNameData.TABLENAME, null);
        List<BlackNameData> datas = new ArrayList<BlackNameData>();
        while (cursor.moveToNext()){
            int mode = cursor.getInt(1);;
            String blackNumber = cursor.getString(0);
            BlackNameData data = new BlackNameData(blackNumber, mode );
            datas.add(data);
        }
        db.close();
        return datas;
    }

    /**
     *
     * @param blackNumber
     * @param mode
     * @return
     */
    public boolean update(String blackNumber,int mode){
        boolean res = false;
        SQLiteDatabase db = mydb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BlackNameData.MODE, mode);
        int rowNumber = db.update(BlackNameData.TABLENAME, values ,BlackNameData.BLACKNUMBER + " = ? ", new String[]{blackNumber});
        if (rowNumber != 0){
            res = true;
        }
        db.close();
        return res;
    }
    public boolean update(BlackNameData data){
        return update(data.getBlackNumber(),data.getMode());
    }

    /**
     * 需求
     *    1， 记录删除掉
     *    2， 修改mode
     */
    public boolean  remove(BlackNameData data){
        return remove(data.getBlackNumber());
    }
}
