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

//    public Cursor getSession()
//    {
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        String[] columns = {Constants.COLUMN_DAY, Constants.COLUMN_HOUR, Constants.COLUMN_LOCATION, Constants.COLUMN_SESID, Constants.COLUMN_MONTH};
//        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNUP, columns, null, null, null, null, null);
//        return cursor;
//    }

    public ArrayList<String> getRecords()
    {
        ArrayList<String> mArrayList = new ArrayList<String>();
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {"recordId","user","calories","steps","date"};
        Cursor cursor = db.query("records", columns, null, null, null, null, null);
        cursor.moveToFirst();

        int index1 = cursor.getColumnIndex("user");
        int index2 = cursor.getColumnIndex("calories");
        int index3 = cursor.getColumnIndex("steps");
        int index4 = cursor.getColumnIndex("date");

        while (!cursor.isAfterLast()) {
            String user = cursor.getString(index1);
            String calories = cursor.getString(index2);
            String steps = cursor.getString(index3);
            String date = cursor.getString(index4);

            String s = user +";" + calories +";" + steps + ";" + date;
            mArrayList.add(s);
            cursor.moveToNext();

        }
        return mArrayList;
    }

    public long insertRecord (String user, String calories, String steps, String date)
    {
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user", user);
        contentValues.put("calories", calories);
        contentValues.put("steps", steps);
        contentValues.put("date", date);
        long id = db.insert("records", null, contentValues);
        return id;
    }

//    public Cursor getSelectedData(String type)
//    {
//        //select plants from database of type 'herb'
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String[] columns = {Constants.COLUMN_DAY, Constants.COLUMN_HOUR, Constants.COLUMN_LOCATION};
//        ArrayList<Integer> cursorIndex = new ArrayList<>();
//
//        String selection = Constants.COLUMN_HOUR + "='" +type+ "'";  //Constants.TYPE = 'type'
//        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNUP, columns, selection, null, null, null, null);
//
//
//        return cursor;
//    }

//    public Cursor getAccount(String username)
//    {
//        //select plants from database of type 'herb'
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String[] columns = {Constants.COLUMN_USERNAME, Constants.COLUMN_PASSWORD};
//        ArrayList<Integer> cursorIndex = new ArrayList<>();
//
//        String selection = Constants.COLUMN_USERNAME + "='" +username+ "'";  //Constants.TYPE = 'type'
//        Cursor cursor = db.query(Constants.TABLE_NAME_SIGNIN, columns, selection, null, null, null, null);
//
//
//        return cursor;
//    }

    public void deleteRecords(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "records");
    }


}
