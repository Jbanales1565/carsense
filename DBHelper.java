package com.example.final_part2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shows.db";
    private static final int DATABASE_VERSION = 1;

    /*Database creation SQL statement*/
    private static final String CREATE_TABLE_SHOW =
            "create table shows (_id integer primary key, "
                    + "showName text not null, "
                    + "channel text not null, "
                    + "genre text not null, "
                    + "language text not null); ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + i + " to " + i1 + " which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shows");
        onCreate(sqLiteDatabase);
    }
}
