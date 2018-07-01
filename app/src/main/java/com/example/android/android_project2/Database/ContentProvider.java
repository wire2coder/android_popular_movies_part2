package com.example.android.android_project2.Database;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.sql.SQLDataException;
import java.sql.SQLInput;

import static com.example.android.android_project2.Database.Contract.TableEntry.TABLE_NAME;

public class ContentProvider extends android.content.ContentProvider {


    /*
    * fileds
    * */

    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;
    private DbHelper mDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    /*
    * helpers
    * */

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_FAVORITES, FAVORITES);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_FAVORITES + "/#", FAVORITE_WITH_ID);

        return  uriMatcher;

    }


    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return true;

    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Uri returnUri;

        long id = db.insert(TABLE_NAME, null, contentValues);

        if ( id > 0 ) {
            returnUri = ContentUris.withAppendedId(Contract.TableEntry.CONTENT_URI, id);
        } else {
            throw new SQLException("Failed to insert row into " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;


        switch (match) {

            case FAVORITES:
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "moviedbid=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        } // switch

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch(match) {

            case FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "moviedbid=?";
                String[] mSelectionArgs = new String[]{id};

                return  db.delete(TABLE_NAME,
                        mSelection,
                        mSelectionArgs);

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("method not implemented");
    }
} // class
