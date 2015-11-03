package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dao.TableManager;

/**
 * Created by Administrator on 2015/9/24.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "safeApp.db";
    private static final int VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableManager.SMSTable.createTable());
        db.execSQL(TableManager.PhoneTable.createTable());
        db.execSQL(TableManager.BlackListTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
