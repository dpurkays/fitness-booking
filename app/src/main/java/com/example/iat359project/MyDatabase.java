package com.example.iat359project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by helmine on 2015-02-04.
 */
public class MyDatabase {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase(Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertSession (String hour, String day, String month, String location)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_HOUR, hour);
        contentValues.put(Constants.COLUMN_DAY, day);
        contentValues.put(Constants.COLUMN_MONTH, month);
        contentValues.put(Constants.COLUMN_LOCATION, location);
        long id = db.insert(Constants.TABLE_NAME_SIGNUP, null, contentValues);
        return id;
    }

    public long insertAccount (String username, String password)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_USERNAME, username);
        contentValues.put(Constants.COLUMN_PASSWORD, password);
        long id = db.insert(Constants.TABLE_NAME_SIGNIN, null, contentValues);
        return id;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.COLUMN_DAY, Constants.COLUMN_HOUR, Constants.COLUMN_LOCATION};
        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNUP, columns, null, null, null, null, null);
        return cursor;
    }



    public Cursor getSelectedData(String type)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.COLUMN_DAY, Constants.COLUMN_HOUR, Constants.COLUMN_LOCATION};
        ArrayList<Integer> cursorIndex = new ArrayList<>();

        String selection = Constants.COLUMN_HOUR + "='" +type+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNUP, columns, selection, null, null, null, null);


        return cursor;
    }

    public Cursor getAccount(String username)
    {
        //select plants from database of type 'herb'
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {Constants.COLUMN_USERNAME, Constants.COLUMN_PASSWORD};
        ArrayList<Integer> cursorIndex = new ArrayList<>();

        String selection = Constants.COLUMN_USERNAME + "='" +username+ "'";  //Constants.TYPE = 'type'
        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNIN, columns, selection, null, null, null, null);


        return cursor;
    }

    public int deleteRow(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {"herb"};
        int count = db.delete(Constants.TABLE_NAME_SIGNUP, Constants.COLUMN_HOUR + "=?", whereArgs);
        return count;
    }


}
