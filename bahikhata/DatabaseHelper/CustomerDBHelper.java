package com.divy.prakash.paathsala.bahikhata.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.divy.prakash.paathsala.bahikhata.utils.CustomerConstants.Customer_Data;

/* This Class Is Used To Create Customer Database */
public class CustomerDBHelper extends SQLiteOpenHelper {
    /* Database Info */
    public static final String DATABASE_NAME = "BAHIKHATACUSTOMER.db";
    public static final int DATABASE_VERSION = 1;
    CustomerDBHelper dbHelper;
    private SQLiteDatabase mDatabase;

    public CustomerDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                Customer_Data.TABLE_NAME + " (" +
                Customer_Data._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Customer_Data.COLUMN_NAME + " TEXT NOT NULL, " +
                Customer_Data.COLUMN_USERID + " INTEGER NOT NULL, " +
                Customer_Data.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                Customer_Data.COLUMN_CONTACT + " TEXT NOT NULL, " +
                Customer_Data.COLUMN_AMOUNT + " REAL NOT NULL " +
                ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS " + Customer_Data.TABLE_NAME);
        onCreate(db);

    }

}
