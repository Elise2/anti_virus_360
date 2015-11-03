package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import entity.Phone;

/**
 * Created by Administrator on 2015/9/24.
 */
public class PhoneDao {
    private DBHelper dbHelper;
    private Context context;

    public  PhoneDao(Context context){
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void  insetPhoneValues(Phone phone){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String insertSql = " insert into "+TableManager.PhoneTable.TABLE_NAME +" values(?,?,?,?)";
        try {
            database.execSQL(insertSql,new Object[]{phone.getPhoneNumber(),phone.getName(),phone.getDate(),phone.getAttribution()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
    }

    public  List<Phone> findAll(){
        return  findPhoneValues(null,null);
    }

    public List<Phone> findPhoneValues(String[] selects,String[] values){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Phone> phoneList = new ArrayList<>();
        String findSql = " select * from "+TableManager.PhoneTable.TABLE_NAME;
        if (!(selects==null||selects.length==0)){
            findSql += " where ";
            for (int i=0; i<selects.length-1;i++){
                findSql+= selects[i] +" = ?";
            }
            findSql+= selects[selects.length-1]+" =? ";
        }

        Cursor cursor = database.rawQuery(findSql,values);
        while (cursor.moveToNext()){
            String phoneNumber = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_PHONE_NUMBR));
            String name = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_FROM_NAME));
            String date = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_DATA));
            String attribute = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_ATTRIBUTE));
            Phone phone = new Phone(phoneNumber,name,date,attribute);
            phoneList.add(phone);
        }
        cursor.close();
        database.close();
        return  phoneList;
    }
}
