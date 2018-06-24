package com.example.android.android_project2;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.android_project2.Adapter.FavoriteMovieAdapter;
import com.example.android.android_project2.Database.MovieDatabaseContract;
import com.example.android.android_project2.Database.MovieDatabaseHelper;
import com.example.android.android_project2.Util.DatabaseUtil;

public class FavoriteMoviesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /* Member variables */
    private SQLiteDatabase database1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        /* doing database stuff */
        MovieDatabaseHelper moviedatabasehelper = new MovieDatabaseHelper(this);
        database1 = moviedatabasehelper.getWritableDatabase();

        /* now insert fake data */
        DatabaseUtil.insertFakeData(database1);

        /* try using loading to get data from 'content-provider instead */


        // getAllDataFromTable() give OUTPUT to 'cursor' variable
        Cursor cursor = database1.query( MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID );

        // https://www.youtube.com/watch?time_continue=169&v=ju2Bv0XKSKI
        // https://youtu.be/ju2Bv0XKSKI?t=4m39s


//        getLoaderManager().initLoader(1, null, FavoriteMoviesActivity.this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FavoriteMovieAdapter fAdapter = new FavoriteMovieAdapter(this, cursor);

        RecyclerView mRecyclerView = findViewById(R.id.rv_favorite_movies);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(fAdapter);

    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        Uri uri = MovieDatabaseContract.MovieDatabaseTable.CONTENT_URI;

        // check the loader number
        if (i == 1) {
            return new CursorLoader(this, uri, null, null, null, null);
        }


        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
