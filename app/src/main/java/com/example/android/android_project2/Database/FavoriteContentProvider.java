package com.example.android.android_project2.Database;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.android_project2.Util.LogUtil;

import static com.example.android.android_project2.Database.Contract.FavoriteEntry.TABLE_NAME;


// Verify that TaskContentProvider extends from ContentProvider and implements required methods
public class FavoriteContentProvider extends ContentProvider {

    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int FAVS = 100;
    public static final int FAV_WITH_ID = 101;


    // CDeclare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();



    // Define a static buildUriMatcher method that associates URI's with their int match
    /**
     Initialize a new matcher object without any matches,
     then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */

        // com.example.android.android_project2/favorite
        // com.example.android.android_project2/favorite/12

        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_FAVORITE, FAVS);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_FAVORITE + "/#", FAV_WITH_ID);


        return uriMatcher;
    }


    // Member variable for a TaskDbHelper that's initialized in the onCreate() method
    private DbHelper mDbHelper;


    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return false;
    }


    // Implement insert to handle requests to insert a single new row of data
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
//        LogUtil.logStuff( String.valueOf(match) );

        Uri returnUri; // URI to be returned

        switch (match) {

            case FAVS:

                // Inserting values into tasks table
                long id = db.insert(TABLE_NAME, null, values);

                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(Contract.FavoriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        } // switch

        // COMPLETED (5) Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;

    }




    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            // Query for the tasks directory
            case FAVS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            case FAV_WITH_ID:
                // using selection and selectionArgs
                // URI: content:// <authority> / tasks / #
                String id = uri.getPathSegments().get(1);

                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelection = "movie_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;


    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        switch (match) {

            // Handle the single item case, recognized by the ID included in the URI path
            case FAV_WITH_ID:
                // content://com.example.android.android_project2/favorite/299536

                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                String mSelection = "movie_id=?";
                String[] mSelectionArgs = new String[]{id};


                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = db.delete(TABLE_NAME,
                        mSelection,
                        mSelectionArgs
                        );

                break;


            case FAVS:
                // com.example.android.android_project2/favorite

                tasksDeleted = db.delete(TABLE_NAME, null, null);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        } // switch


        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;

    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

} // class