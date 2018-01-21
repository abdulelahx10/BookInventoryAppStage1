package com.example.android.bookinventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.android.bookinventoryapp.data.BookDbHelper;
import com.example.android.bookinventoryapp.data.BookContract.BookEntry;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private BookDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new BookDbHelper(this);
        // test two dummies data
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.harry_potter_img);
        byte[] byteArray = getBytes(bm);
        insertBook("Harry Potter", 20, 7, byteArray, "supplier1", "supplier1@abc.com", 123456789);
        insertBook("The hobbet", 15, 8, null, "supplier2", "supplier2@abc.com", 987654321);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_PRODUCT_QUANTITY,
                BookEntry.COLUMN_PRODUCT_IMAGE,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_EMAIL,
                BookEntry.COLUMN_SUPPLIER_NUMBER};

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            Log.d(LOG_TAG, "The table contains " + cursor.getCount() + " books.\n\n");
            Log.d(LOG_TAG, BookEntry._ID + " - "
                    + BookEntry.COLUMN_PRODUCT_NAME + " - "
                    + BookEntry.COLUMN_PRODUCT_PRICE + " - "
                    + BookEntry.COLUMN_PRODUCT_QUANTITY + " - "
                    + BookEntry.COLUMN_PRODUCT_IMAGE + " - "
                    + BookEntry.COLUMN_SUPPLIER_NAME + " - "
                    + BookEntry.COLUMN_SUPPLIER_EMAIL + " - "
                    + BookEntry.COLUMN_SUPPLIER_NUMBER + "\n");

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_QUANTITY);
            int imgColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_IMAGE);
            int sNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int sEmailColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_EMAIL);
            int sNumberColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NUMBER);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                byte[] currentImg = cursor.getBlob(imgColumnIndex);
                String currentSName = cursor.getString(sNameColumnIndex);
                String currentSEmail = cursor.getString(sEmailColumnIndex);
                int currentSNumber = cursor.getInt(sNumberColumnIndex);

                Log.d(LOG_TAG, currentID + " - "
                        + currentName + " - "
                        + currentPrice + " - "
                        + currentQuantity + " - "
                        + currentImg + " - "
                        + currentSName + " - "
                        + currentSEmail + " - "
                        + currentSNumber + "\n");
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBook(String name, int price, int quantity, byte[] img, String sName, String sEmail, int sNumber) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, name);
        values.put(BookEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(BookEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_PRODUCT_IMAGE, img);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, sName);
        values.put(BookEntry.COLUMN_SUPPLIER_EMAIL, sEmail);
        values.put(BookEntry.COLUMN_SUPPLIER_NUMBER, sNumber);


        db.insert(BookEntry.TABLE_NAME, null, values);
    }

    // convert from bitmap to byte array
    public byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
