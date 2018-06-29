package com.example.shinikhee.content_sql_sensor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context contex, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(contex, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table testTable(_id integer primary key autoincrement, " +
                "x_axis float, " +
                "y_axis float, " +
                "z_axis float)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MySQLiteOpenHelper", + oldVersion + " -> " + newVersion + " " + db);
        db.execSQL("drop table testTable");
        onCreate(db);

    }
}
