package com.divy.prakash.paathsala.bahikhata.utils;

import android.provider.BaseColumns;

/* This Class Is Used To Create A Constant Fields Of Customer */
public class CustomerConstants {
    public CustomerConstants() {
    }

    public static final class Customer_Data implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USERID = "custid";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_CONTACT = "contact";
        public static final String COLUMN_AMOUNT = "amount";

    }

}
