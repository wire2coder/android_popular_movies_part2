package com.example.android.android_project2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.android_project2.Adapter.FavoriteMovieAdapter;
import com.example.android.android_project2.Util.DatabaseUtil;

public class FavoriteMoviesActivity extends AppCompatActivity {

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

        /* getAllDataFromTable() give OUTPUT to 'cursor' variable */
        Cursor cursor = database1.query( MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID );

        /* https://www.youtube.com/watch?time_continue=169&v=ju2Bv0XKSKI */
        // https://youtu.be/ju2Bv0XKSKI?t=4m39s

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FavoriteMovieAdapter fAdapter = new FavoriteMovieAdapter(this, cursor);

        RecyclerView mRecyclerView = findViewById(R.id.rv_favorite_movies);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(fAdapter);

    }
}
