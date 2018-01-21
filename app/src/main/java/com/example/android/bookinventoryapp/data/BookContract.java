package com.example.android.bookinventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by abdulelah on 20/01/2018.
 */

public class BookContract {
    // To prevent someone from accidentally instantiating the contract class
    private BookContract() {
    }

    public static final class BookEntry implements BaseColumns {

        public final static String TABLE_NAME = "books";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product_name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_IMAGE = "image";
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public final static String COLUMN_SUPPLIER_EMAIL = "supplier_email";
        public final static String COLUMN_SUPPLIER_NUMBER = "supplier_phone_number";

    }
}
