package com.divy.prakash.paathsala.bahikhata.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.divy.prakash.paathsala.bahikhata.bo.SignupBO;
import com.divy.prakash.paathsala.bahikhata.utils.DataBaseConstants;
import com.divy.prakash.paathsala.bahikhata.utils.UserConstants;

/* This Class Is Ued To Insert Data In Database */
public class SignupDAOImpl implements SignupDAO {

    /* Table Info */
    public static final String COL_ID = "_id";
    public static final String COL_SHOPNAME = "shopname";
    public static final String COL_EMAIL = "email";
    public static final String COL_CONTACT = "contact";
    public static final String COL_USERID = "userid";
    public static final String COL_PASSWORD = "password";
    /* Database Info */
    private static final String TABLE_NAME = "shopkeeper";
    /* Create Table Statement */
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_SHOPNAME + " TEXT NOT NULL," +
            COL_EMAIL + " TEXT NOT NULL," +
            COL_CONTACT + " LONG NOT NULL," +
            COL_USERID + " TEXT NOT NULL," +
            COL_PASSWORD + " TEXT NOT NULL)";
    Context context = null;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    public SignupDAOImpl(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    /* Method To Insert Data In Database */
    @Override
    public long insertSignup_Detail(SignupBO bo) {
        ContentValues values = new ContentValues();
        values.put(COL_SHOPNAME, bo.getShopname());
        values.put(COL_EMAIL, bo.getEmail());
        values.put(COL_CONTACT, bo.getContactno());
        values.put(COL_USERID, bo.getUserid());
        values.put(COL_PASSWORD, bo.getPassword());
        db = databaseHelper.getWritableDatabase();
        return db.insert(TABLE_NAME, null, values);

    }

    /* Method Is Used To Authenticate User */
    public UserConstants Authenticate(UserConstants userConstants) {
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_ID, COL_SHOPNAME, COL_EMAIL, COL_CONTACT, COL_USERID, COL_PASSWORD},//Selecting columns want to query
                COL_USERID + "=?",
                new String[]{userConstants.userID},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

            UserConstants userConstants1 = new UserConstants(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

            /* Match Passwords */
            if (userConstants1.password.equals(userConstants.password)) {
                return userConstants1;
            }
        }

        /* If User Password Does Not Matches Or There Is No Record  Then Return Null */
        return null;
    }

    /* Method To Check Is User Exists Or Not */
    public boolean isUserExists(String user) {
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_ID, COL_SHOPNAME, COL_EMAIL, COL_CONTACT, COL_USERID, COL_PASSWORD},//Selecting columns want to query
                COL_USERID + "=?",
                new String[]{user},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            /* If Cursor Has Value Then In Login Table There Is User Associated With This Given User So Return True */
            return true;
        }

        /* If User Does Not Exist Return False */
        return false;
    }

    /* This Function Is Used To Retrieve Password */
    @Override
    public UserConstants forgotPassword(UserConstants userConstants) {
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_ID, COL_SHOPNAME, COL_EMAIL, COL_CONTACT, COL_USERID, COL_PASSWORD},//Selecting columns want to query
                COL_USERID + "=?",
                new String[]{userConstants.userID},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

            UserConstants userConstants1 = new UserConstants(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                return userConstants1;

        }
        return null;
    }

    @Override
    public Cursor getUserData(String id) {
        db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(true, TABLE_NAME, new String[]{COL_ID, COL_SHOPNAME, COL_EMAIL, COL_CONTACT, COL_USERID, COL_PASSWORD}, COL_ID + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    @Override
    public boolean changeUserData(String id, String shopname, String usercontact, String useremail, String userid) {
        db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SHOPNAME, shopname);
        contentValues.put(COL_EMAIL, useremail);
        contentValues.put(COL_CONTACT, usercontact);
        contentValues.put(COL_USERID, userid);
        int up = db.update(TABLE_NAME, contentValues, COL_ID + "=" + id, null);
        return up > 0;


    }

    @Override
    public boolean changePassword(String id, String newpassword) {
        db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASSWORD, newpassword);
        int up = db.update(TABLE_NAME, contentValues, COL_ID + "=" + id, null);
        return up > 0;
    }

    /* Database Helper Is Used To Create Database */
    public static class DatabaseHelper extends SQLiteOpenHelper {
        Context context;

        public DatabaseHelper(Context context) {
            super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
