/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/*  static import declaration imports static members from classes,
    allowing them to be used without class qualification.
*/
import static com.example.android.android_project2.MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME;

/* this is where you write the log for the 'REST API' */
public class MovieDatabaseContentProvider extends android.content.ContentProvider {

/* 'member variables DECLARATION */
private MovieDatabaseHelper helper1;

public static final int FAVORITE = 100;
public static final int FAVORITE_WITH_ID = 101;


/* making a 'URI MATCHER' */
public static UriMatcher buildUriMatcher() {

    UriMatcher match1 = new UriMatcher(UriMatcher.NO_MATCH);

    // location 1, ALL the movies, FAVORITE = 100
    match1.addURI(MovieDatabaseContract.AUTHORITY, MovieDatabaseContract.PATH_FAV, FAVORITE);

    // location 2, ONE movie, FAVORITE_WITH_ID = 101
    match1.addURI(MovieDatabaseContract.AUTHORITY,
            MovieDatabaseContract.PATH_FAV + "/#", FAVORITE_WITH_ID);

    return match1;

}

@Override
public boolean onCreate() {

    Context context = getContext();
    helper1 = new MovieDatabaseHelper(context); // >> helper1 is 'class member'
    return true; // >> everything good!

}

@Override
public Uri insert(Uri uri, ContentValues values) {

    Uri insertUri;

    SQLiteDatabase db1 = helper1.getWritableDatabase(); // >> database object

    long id = db1.insert(TABLE_NAME, null, values);

    /* check if database insert successfully completed */
    if (id > 0) {

         insertUri = ContentUris.withAppendedId(MovieDatabaseContract.MovieDatabaseTable.CONTENT_URI, id);

    } else {

        throw new android.database.SQLException("row insert at " + uri + " did not happen");

    }

    // tell UI/Activity and database that I changed the data at this location/uri
    getContext().getContentResolver().notifyChange(uri, null);

    return insertUri;
}

@Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase readOnlyDb = helper1.getReadableDatabase();

        int matchedLocation = buildUriMatcher().match(uri);
        Cursor returnCursor;

        switch (matchedLocation) {

            case FAVORITE:

                returnCursor = readOnlyDb.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null, null,
                        sortOrder);
                break;

            case FAVORITE_WITH_ID:
                // URI: content://<authority>/tasks/#
                String id = uri.getPathSegments().get(1); // >> 1 is the # part of the URI

                String mSelection = "moviedbid=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = readOnlyDb.query(TABLE_NAME,
                            projection,
                            mSelection,
                            mSelectionArgs, null, null,
                            sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Don't this uri: " + uri);

        } // switch

        // tell the UI and database that I just changed the data at this location/uri
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;

    }

@Override
public int delete(Uri uri, String selection, String[] selectionArgs) {

    SQLiteDatabase db = helper1.getWritableDatabase();

    int matchedLocation = buildUriMatcher().match(uri);

    switch(matchedLocation) {

        case FAVORITE_WITH_ID: // >> 101
            String id = uri.getPathSegments().get(1); // >> a number
            String mSelection = "moviedbid=?";

            String[] mSelectionArgs = new String[]{id};

            // return the number of items that were deleted
            int numItemDeleted = db.delete(TABLE_NAME,
                    mSelection,
                    mSelectionArgs
            );

            return numItemDeleted;

        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);

    } // switch

}

@Override
public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Updating data is not allow");

}

@Override
public String getType(Uri uri) {
    throw new UnsupportedOperationException("Getting data type is now allow");
}


} // class
