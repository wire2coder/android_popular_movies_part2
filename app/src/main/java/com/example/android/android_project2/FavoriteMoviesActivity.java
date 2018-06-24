package com.example.android.android_project2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.android_project2.Adapter.FavoriteMovieAdapter;
import com.example.android.android_project2.Database.MovieDatabaseContract;
import com.example.android.android_project2.Database.MovieDatabaseHelper;
import com.example.android.android_project2.Util.DatabaseUtil;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtils_U;
import com.example.android.android_project2.Util.ToastUtil;

import java.io.IOException;
import java.net.URL;

import static com.example.android.android_project2.Util.NetworkUtils_U.getResponseFromHttpUrl;

public class FavoriteMoviesActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    /* Member variables */


    private SQLiteDatabase database1;
    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    private static final int GITHUB_SEARCH_LOADER = 22;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);


        /* doing database stuff */
        MovieDatabaseHelper moviedatabasehelper = new MovieDatabaseHelper(this);
        database1 = moviedatabasehelper.getWritableDatabase();


        /* now insert fake data */
        DatabaseUtil.insertFakeData(database1);



        // getAllDataFromTable() give OUTPUT to 'cursor' variable
        // https://www.youtube.com/watch?time_continue=169&v=ju2Bv0XKSKI
        // https://youtu.be/ju2Bv0XKSKI?t=4m39s
        Cursor cursor = database1.query( MovieDatabaseContract.MovieDatabaseTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID );



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FavoriteMovieAdapter fAdapter = new FavoriteMovieAdapter(this, cursor);

        RecyclerView mRecyclerView = findViewById(R.id.rv_favorite_movies);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(fAdapter);



        /*
        * Bundle and Loader calling
        * */



        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, "test");
        getSupportLoaderManager().restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);



        /*
        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, "test");

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);

        if (githubSearchLoader == null) {
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }
        */



    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id,  final Bundle args) {


        return new AsyncTaskLoader<String> (this) {

            @Override
            protected void onStartLoading() {
                if (args == null) {
                    return;
                }

                forceLoad(); // run loadInBackground() below
            }


            @Override
            public String loadInBackground() {

                // this comes from the 'BUNDLE object'
//                 String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                try {

                   URL githubUrl = new URL("https://api.github.com/search/repositories?q=%22hi%22");
                   String githubSearchResults = NetworkUtils_U.getResponseFromHttpUrl(githubUrl);
                   return githubSearchResults; // >> data go to onLoadFinished() below

                } catch (IOException e) {

                    e.printStackTrace();
                    return null;

                }


            }

        };


    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        ToastUtil.makeMeAToast(this, "Loader done");
        TextView textView = findViewById(R.id.tv_favorite_github);
        textView.setText(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        ToastUtil.makeMeAToast(this, "Loader was reset");
    }
}
