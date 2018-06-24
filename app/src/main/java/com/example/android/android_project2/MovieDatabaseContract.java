/*
 * Terry S Android Nano Degree project 2
 */

/* Project Rubric
* Data Persistence:
*
*   1. The titles and IDs of the user’s favorite movies are stored
*   in a native SQLite database and exposed via a ContentProvider
*
*   2. Data (int the database) is updated whenever the user favorites
*   or unfavorites a movie.
*   No other persistence libraries are used.
*
*   3. When the "favorites" setting option is selected,
*   the main view displays the entire favorites collection
*   based on movie ids stored in the database.*/

package com.example.android.android_project2;

import android.net.Uri;
import android.provider.BaseColumns;

/* this class DEFINES the database TABLE, but we call it a 'CONTRACT' -_- */
public class MovieDatabaseContract {

    public static final String AUTHORITY = "com.example.android.android_project2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAV = "fav";


    /* make a private constructor because this class will not been to be instantiated */
    private MovieDatabaseContract() {}

    /* make inner class to 'define' the database TABLE */
    public static class MovieDatabaseTable implements BaseColumns {


        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        /* need title and id */
        public static final String TABLE_NAME = "favorite_movie";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";



    }

    /*  Note: Because this implements BaseColumns, the _id column is generated automatically

        favorite_movie

         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    movie_id     |    movie_title   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |  245   |       Alive Poll          |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    332     |       Lion Monkey     |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   424     |       The Prince Joker |
         - - - - - - - - - - - - - - - - - - - - - -

       */

} // MovieDatabaseContract
