package com.example.android.android_project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.android_project2.Adapter.FavoriteMovieAdapter;

public class FavoriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        // TODO: HERE, make a new RecyclerView

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FavoriteMovieAdapter fAdapter = new FavoriteMovieAdapter(this, 10);

        RecyclerView mRecyclerView = findViewById(R.id.rv_favorite_movies);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(fAdapter);

        // TODO: 6/10 show data from database inside FavoriteMovie.activity
        // https://youtu.be/ju2Bv0XKSKI?t=4m39s
        // T07.07-Solutions-AddGuests, GuestListAdapter.java

    }
}
