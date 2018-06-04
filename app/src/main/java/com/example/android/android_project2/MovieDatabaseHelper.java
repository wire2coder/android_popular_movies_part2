/*
 * Terry S Android Nano Degree project 2
 */

/* This class CREATES the DATABASE */

package com.example.android.android_project2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import  com.example.android.android_project2.MovieDatabaseContract.MovieDatabaseTable;
import com.example.android.android_project2.Util.LogUtil;

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    /* member variables */
    private static final String DATABASE_NAME = "favorite_movie.db";
    private static final int DATABASE_VERSION = 1;

    /* constructor */
    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* required override
    * this method CREATES the database */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /* make a SQL command STRING */
        String SQL_CREATE_FAVORITE_MOVIE_TABLE =
                "CREATE TABLE " + MovieDatabaseTable.TABLE_NAME
                        + "("
                        + MovieDatabaseTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MovieDatabaseTable.COLUMN_MOVIE_ID + " INTEGER NOT NULL, "
                        + MovieDatabaseTable.COLUMN_MOVIE_TITLE + " TEXT NOT NULL "
                        + "); ";

        LogUtil.logStuff(SQL_CREATE_FAVORITE_MOVIE_TABLE);

        /* execute the SQL command */
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    /* required override
    * what to do when are need to UPGRADE or CHANGE the database */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /* destroy the old table */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieDatabaseTable.TABLE_NAME);

        /* make a new table */
        onCreate(sqLiteDatabase);

    }


} // MovieDatabaseHelper
