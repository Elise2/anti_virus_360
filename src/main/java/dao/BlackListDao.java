package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import database.DBHelper;
import entity.BlockList;

/**
 * Created by Administrator on 2015/9/24.
 */
public class BlackListDao {
    private DBHelper dbHelper;
    private Context context;

    public BlackListDao(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);

    }

    public void insertValues(BlockList blockList) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String sql = " insert into " + TableManager.BlackListTable.TABLE_NAME + " values(?,?)";
        try {
            database.execSQL(sql, new Object[]{blockList.getPhoneNumber(), blockList.getName()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
    }

    public List<BlockList> findAll() {
       return findValues(null, null);
    }

    public List<BlockList> findValues(String[] selects, String[] values) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<BlockList> blockLists = new ArrayList<>();
        String findSql = "select * from " + TableManager.BlackListTable.TABLE_NAME;
        if (!(selects == null || selects.length == 0)) {
            findSql += " where ";
            for (int i = 0; i < selects.length - 1; i++) {
                findSql += selects[i] + "=? and ";
            }
            findSql += selects[selects.length - 1] + " =? ";
        }
        Cursor cursor = database.rawQuery(findSql, values);
        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(TableManager.BlackListTable.COL_PHONE_NUMBER));
            String phoneName = cursor.getString(cursor.getColumnIndex(TableManager.BlackListTable.COL_FROME_NAME));
            BlockList blackUser = new BlockList(phoneNumber, phoneName);
            blockLists.add(blackUser);
        }
        cursor.close();
        database.close();
        return blockLists;
    }
}
