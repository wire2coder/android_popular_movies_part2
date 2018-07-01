package com.example.android.android_project2.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    /*
    * fields
    * */

    private static final String DATABASE_NAME = "favoriteMoviesDb.db";
    private static final int VERSION = 2;

    /*
    * constructor
    * */

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE " + Contract.TableEntry.TABLE_NAME + " (" +
                Contract.TableEntry._ID + " INTEGER PRIMARY KEY, " +
                Contract.TableEntry.COLUMN_MOVIEDBID + " INTEGER UNIQUE NOT NULL " + ");";


        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.TableEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

} // class

