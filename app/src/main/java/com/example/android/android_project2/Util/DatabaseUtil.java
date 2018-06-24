package com.example.android.android_project2.Util;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.android_project2.Database.MovieDatabaseContract;

import java.util.ArrayList;

public class DatabaseUtil {

    public static void insertFakeData(SQLiteDatabase sqLiteDatabase1) {
        if (sqLiteDatabase1 == null) {
            return;
        }

        ArrayList<ContentValues> arrayList1 = new ArrayList<>();
        ContentValues cv = null;

        cv = new ContentValues();
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID, "333");
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_TITLE, "Love the Meat");

        arrayList1.add(cv);

        cv = new ContentValues();
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID, "555");
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_TITLE, "Stinky Clam");

        arrayList1.add(cv);

        cv = new ContentValues();
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID, "777");
        cv.put(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_TITLE, "Don't trust her");

        arrayList1.add(cv);

        try {

            sqLiteDatabase1.beginTransaction();
            // delete existing data in the table first
            sqLiteDatabase1.delete(MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME, null, null);

            /* now travel through array list and add data 1 row at a time */
            for (ContentValues asdf: arrayList1 ) {
                sqLiteDatabase1.insert(MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME, null, asdf);
            }
            sqLiteDatabase1.setTransactionSuccessful();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sqLiteDatabase1.endTransaction();
    }

} // DatabaseUtil
