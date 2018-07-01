package com.example.android.android_project2.Database;


import android.net.Uri;
import android.provider.BaseColumns;


public class Contract {


    /*
    * fields
    * */

    public static final String AUTHORITY = "com.example.android.android_project2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITES = "favorites";


    /*
    * inner class
    * */

    public static final class TableEntry implements BaseColumns {


        /*
        * fields
        * */

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favoritemovies";
        public static final String COLUMN_MOVIEDBID = "moviedbid";

        /*
         - - - - - - - - - - - - -
        | _id  |    moviedbid     |
         - - - - - - - - - - - - -
        |  1   |  245            |
         - - - - - - - - - - - - -
        |  2   |    332          |
         - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - -
        | 43   |   424           |
         - - - - - - - - - - - - -
         */


    } // class

} // class
