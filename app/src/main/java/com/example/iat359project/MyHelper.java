package com.example.iat359project;

/**
 * Created by helmine on 2017-02-08.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by helmine on 2015-02-02.
 */
public class MyHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE_SIGNUP =
            "CREATE TABLE "+
                    Constants.TABLE_NAME_SIGNUP + " (" +
                    Constants.COLUMN_SESID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.COLUMN_DAY + " TEXT, " +
                    Constants.COLUMN_MONTH + " TEXT, " +
                    Constants.COLUMN_HOUR + " TEXT, " +
                    Constants.COLUMN_LOCATION + " TEXT);" ;

    private static final String CREATE_TABLE_SIGNIN =
            "CREATE TABLE "+
                    Constants.TABLE_NAME_SIGNIN + " (" +
                    Constants.COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.COLUMN_USERNAME + " TEXT, " +
                    Constants.COLUMN_PASSWORD + " TEXT);" ;

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " ;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_SIGNUP);
            db.execSQL(CREATE_TABLE_SIGNIN);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}
