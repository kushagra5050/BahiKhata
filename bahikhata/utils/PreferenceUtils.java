package com.divy.prakash.paathsala.bahikhata.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* This Class Is Used To Save And Retrieve User Details From Shared Preferences */
public class PreferenceUtils {
    public PreferenceUtils() {
    }

    public static boolean saveId(String id, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_ID, id);
        prefeditor.apply();
        return true;
    }

    public static boolean saveFlagSortBy(String flag, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString("Flag", flag);
        prefeditor.apply();
        return true;
    }

    public static boolean saveShopName(String shopname, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_SHOPNAME, shopname);
        prefeditor.apply();
        return true;
    }

    public static boolean saveEmail(String email, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_EMAIL, email);
        prefeditor.apply();
        return true;
    }

    public static boolean saveContact(String contact, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_CONTACT, contact);
        prefeditor.apply();
        return true;
    }

    public static boolean saveUserID(String userid, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_USERID, userid);
        prefeditor.apply();
        return true;
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(LoginConstants.COL_PASSWORD, password);
        prefeditor.apply();
        return true;
    }

    public static boolean saveProductName(String productname, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.PRODUCTNAME, productname);
        prefeditor.apply();
        return true;
    }

    public static boolean saveQuantity(String quantity, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.QUANTITY, quantity);
        prefeditor.apply();
        return true;
    }

    public static boolean saveMeasuringUnit(String measuringunit, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.MEASURINGUNIT,measuringunit);
        prefeditor.apply();
        return true;
    }

    public static boolean savePricePerUnit(String priceperunit, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.PRICEPERUNIT,priceperunit);
        prefeditor.apply();
        return true;
    }
    public static boolean saveTotalPrice(String totalprice, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.TOTALPRICE,totalprice);
        prefeditor.apply();
        return true;
    }
    public static boolean savePosition(String position, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefeditor = preferences.edit();
        prefeditor.putString(AddDataConstants.POSITION,position);
        prefeditor.apply();
        return true;
    }
    public static String getProductName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.PRODUCTNAME, null);
    }
    public static String getQuantity(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.QUANTITY, null);
    }
    public static String getMeasuringUnit(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.MEASURINGUNIT, null);
    }
    public static String getPricePerUnit(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.PRICEPERUNIT, null);
    }
    public static String getTotalPrice(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.TOTALPRICE, null);
    }
    public static String getPosition(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(AddDataConstants.POSITION, null);
    }

    public static String getFlagSortBy(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("Flag", null);
    }

    public static String getId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_ID, null);
    }

    public static String getShopName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_SHOPNAME, null);
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_EMAIL, null);
    }

    public static String getContact(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_CONTACT, null);
    }

    public static String getUserid(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_USERID, null);
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LoginConstants.COL_PASSWORD, null);
    }

}
