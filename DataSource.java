package com.example.final_part2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DataSource {
    private SQLiteDatabase database;
    private final DBHelper dbHelper;

    public DataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public boolean insertShow(bt_devices s){
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            Long rowId = (long) getLastShowId()+1;
            Log.d("BLAH", "insert rowid: " + rowId);
            initialValues.put("_id", rowId);
            initialValues.put("showName", s.getShowName());
            initialValues.put("channel", s.getShowChannel());
            initialValues.put("genre", s.getShowGenre());
            initialValues.put("language", s.getShowLanguage());
            didSucceed = database.insert("shows", null, initialValues) > 0;
            Log.d("BLAH", "insert: "+rowId);
        }
        catch (Exception e) {
            Log.d("BLAH", "fail");
            //Do nothing - will return false if there is no exception
        }
        return didSucceed;
    }

    public int getLastShowId(){
        int lastId;
        try {
            String query = "select MAX(_id) from shows";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<String> getAllShowNames() {
        Log.d("BLAH", "getAllShowNames called:");

        ArrayList<String> showNames = new ArrayList<>();
        try {
            String query = "SELECT showName FROM shows";
            Cursor cursor = database.rawQuery(query, null);
            Log.d("BLAH", "getAllCustomerNames try:");

            while (cursor.moveToNext()) {
                String showName = cursor.getString(cursor.getColumnIndexOrThrow("showName"));
                showNames.add(showName);
                Log.d("BLAH", "firstName: " + showName);
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("BLAH", "Error in getAllCustomerNames: " + e.getMessage(), e);
        }
        return showNames;
    }
    public ArrayList<String> getLastShowData() {
        Log.d("BLAH", "getAllShowNames called:");

        ArrayList<String> lastShowData = new ArrayList<>();
        try {
            String query = "SELECT * FROM shows ORDER BY _id DESC LIMIT 1";
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    lastShowData.add(cursor.getString(i));
                }
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("BLAH", "Error in getLastShowData: " + e.getMessage(), e);
        }
        Log.d("BLAH", "getLastShowData called: " + lastShowData.toString());
        return lastShowData;
    }
}
