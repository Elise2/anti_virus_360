package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import entity.SMS;

/**
 * Created by Administrator on 2015/9/24.
 */
public class SMSDao {
    private DBHelper dbHelper;
    private Context context;

    public SMSDao(Context context) {
        this.dbHelper = new DBHelper(context);
        this.context = context;
    }

    public void insertValue(SMS sms) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String SqlString = " insert into " + TableManager.SMSTable.TABLR_NAME + " values(?,?,?)";
        try {
            database.execSQL(SqlString, new Object[]{sms.getPhoneNumber(), sms.getMessage(), sms.getDate()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();

    }

    public List<SMS> findAll() {
        return findValues(null, null);
    }

    public List<SMS> findValues(String[] selects, String[] values) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<SMS> smslists = new ArrayList<>();
        String sqlStr = " select * from " + TableManager.SMSTable.TABLR_NAME;
        if (!(selects == null || selects.length == 0)) {
            sqlStr += " where ";
            for (int i = 0; i < selects.length - 1; i++) {
                sqlStr += selects[i] + " = ? ";
            }
            sqlStr += selects[selects.length - 1] + "=?";
        }
        Cursor cursor = database.rawQuery(sqlStr, values);
        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_PHONE_NUMBER));
            String msg = cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_MESSAGE));
            String date = cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_DATE));
            SMS sms = new SMS(phoneNumber, msg, date);
            smslists.add(sms);
        }
        cursor.close();
        database.close();
        return smslists;

    }
}
