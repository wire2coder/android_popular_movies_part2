/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.android_project2.Adapter.MovieAdapter;
import com.example.android.android_project2.Database.Contract;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.Util.JSONUtil;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.StringUtil;
import com.facebook.stetho.Stetho;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements
        MovieAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks< ArrayList<Movie> > {

    /*
    * fields
    * */

    String TAG = MainActivity.this.getClass().getSimpleName();

    private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static String BASE_URL_POPULAR_HIGHEST_RATE = "https://api.themoviedb.org/3/movie/top_rated";
    public  static String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String QUERY_PARAM = "api_key";

    private ProgressBar mProgressbar;

    static String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    static String BUNDLE_RECYCLER_MOVIEDATA = "recycler_moviedata";

    private static final int CURSOR_LOADER_ID = 13;
    private static final int MOVIE_LOADER_ID = 11;

    ArrayList<String> movieIdList = new ArrayList<>(); // for movie fav id
    ArrayList<Movie> favMovieList = new ArrayList<>(); // for fav movies
    ArrayList<Movie> mMovies = new ArrayList<>(); // for movies

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* https://www.youtube.com/watch?v=iyXpdkqBsG8 */
        Stetho.initializeWithDefaults(MainActivity.this);


        /* check if the device is connected to the internet */
        if ( !isThereInternet(MainActivity.this) ) { // >> no internet

            /* show 'no internet' dialogbox */
            internetDialog(MainActivity.this).show();

        } else { // >> yes internet

            setContentView(R.layout.activity_main);

            mProgressbar = findViewById(R.id.pb_loading);
            mRecyclerView = findViewById(R.id.rv_movies);
            StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(3, 1);

            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mMovieAdapter = new MovieAdapter(MainActivity.this, MainActivity.this);
            mRecyclerView.setAdapter(mMovieAdapter);


            if (savedInstanceState != null) {

                // extracting data from the 'state bundle object'
                Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);

                mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
                mMovies = savedInstanceState.getParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA);
                mMovieAdapter.swapData( mMovies );

            } else {

                // no saved data inside the 'state bundle object'
                Bundle queryBundle = new Bundle();
                queryBundle.putString("url_in", BASE_URL_POPULAR);
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, queryBundle, this);

            }


        } // else


    } // onCreate




    /*
        checking for internet connection
        https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        https://www.youtube.com/watch?v=DMhnJK38RlQ
    */
    private boolean isThereInternet(Context context) {

        boolean connected = false;

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting() ) {
            connected = true;
           return connected;
        } else {
            return connected;
        }

    }


    /* show a dialog box if the 'device' is not connected to the internet */
    private AlertDialog.Builder internetDialog(Context context) {

        AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
        dialog1.setTitle("No Internet");
        dialog1.setMessage("Please connect your device to the internet");

        dialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }

        });

        return dialog1; // >> 'builder'

    }


    /* MENU, 'inflate the MENU XML' */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);

        return true;
    }


    @Override
    public void onListItemClick(Movie clickedItemIndex) {
//        Toast.makeText(MainActivity.this, "I clicked on an item", Toast.LENGTH_SHORT).show();

        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        Parcelable parcelableMovie = clickedItemIndex;

        detailActivityIntent.putExtra("movie_detail", parcelableMovie);
        startActivity(detailActivityIntent);
    }


    /*
    * saved data before device gets rotate and Activity gets destroy
    * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState() );
        outState.putParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA, mMovies);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        TextView tv_sort_movie = findViewById(R.id.tv_sort_movie);
        LoaderManager loaderManager1 = getSupportLoaderManager();

        switch (item.getItemId()) {

            case R.id.mi_most_popular:
                tv_sort_movie.setText("Most Popular");

                Bundle mi_most_popular_bundle = new Bundle();
                mi_most_popular_bundle.putString("url_in", BASE_URL_POPULAR);

                loaderManager1.restartLoader(MOVIE_LOADER_ID, mi_most_popular_bundle, this);

                return true; // clickEvent data is 'consumed'


            case R.id.mi_highest_rate:
                tv_sort_movie.setText("Top Rated");

                Bundle mi_highest_rate_bundle = new Bundle();
                mi_highest_rate_bundle.putString("url_in", BASE_URL_POPULAR_HIGHEST_RATE);

                loaderManager1.restartLoader(MOVIE_LOADER_ID, mi_highest_rate_bundle, this);

                return true; // clickEvent data is 'consumed'


            case R.id.mi_favorite_movie:
//                tv_sort_movie.setText("Favorites");
                // you do this inside fav_loader onLoadFinished() instead

                loaderManager1.restartLoader(CURSOR_LOADER_ID, null, fav_loader);

                return true; // clickEvent data is 'consumed'

            default:
                return super.onOptionsItemSelected(item);
        }


    }



    /*
    * Loader, Class implementation
    * */

    @Override
    public Loader< ArrayList<Movie> > onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<ArrayList<Movie>>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
//                Toast.makeText(getApplicationContext(), "onStartLoading ArrayList<Movie>", Toast.LENGTH_SHORT).show();

                if (args == null) {
                    return;
                }
//                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList<Movie> loadInBackground() {

                ArrayList<Movie> movie_list_out;

                String url_in = args.getString("url_in");

                URL url = NetworkUtil.makeUrl(url_in, -1, 0);
//                LogUtil.logStuff( url.toString() );

                String results = NetworkUtil.goToWebsite( url );
//                LogUtil.logStuff( results );

                movie_list_out = StringUtil.stringToJson(results);
//                LogUtil.logStuff( String.valueOf( movie_list_out.size() ) );

                return movie_list_out;

            } // loadInBackground


        }; // new AsyncTaskLoader


    } // onCreateLoader


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> list_in) {

        if (list_in != null && list_in.size() > 0) {

            mMovies = list_in;
            mMovieAdapter.swapData(mMovies);

            TextView tv_sort_movie = findViewById(R.id.tv_sort_movie);
//            LogUtil.logStuff( tv_sort_movie.getText().toString() );

            /*
             * this is a genius solution, so MainActivity will always load MOVIE_LOADER_ID.
             * so after MOVIE_LOADER_ID finished loading, you check the value of the TextView
             * if the TextView value == Favorites, you run CURSOR_LOADER_ID and fill the data
             * in MainActivity with CURSOR_LOADER_ID data
             * */

            if ( tv_sort_movie.getText() =="Favorites" ) {
                getSupportLoaderManager().restartLoader(CURSOR_LOADER_ID, null, fav_loader);
            }

        } else {
            Toast.makeText(MainActivity.this, "Can't connect to the website", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
    }



    /*
    * Loader, Callback, fav_loader
    * */

    private LoaderManager.LoaderCallbacks<Cursor> fav_loader
            = new LoaderManager.LoaderCallbacks<Cursor>() {


        @Override
        public Loader<Cursor> onCreateLoader(int id, final Bundle args) {

            return new AsyncTaskLoader<Cursor>( getApplicationContext() ) {

                // onStartLoading() is called when a loader first starts loading data
                @Override
                protected void onStartLoading() {
//                    Toast.makeText(getApplicationContext(), "onStartLoading()", Toast.LENGTH_SHORT).show();

//                    movieIdList.clear();
//                    favMovieList.clear();
//                    LogUtil.logStuff( String.valueOf( "favMovieList inside onStartLoading: " + favMovieList.size() )  );

                    // Force a new load
                    forceLoad();
                }


                @Override
                public Cursor loadInBackground() {

                    Cursor cursorData;
                    ArrayList<Movie> fav_movie_list = new ArrayList<>(); // for fav movies
                    mMovies = new ArrayList<>(); // another way of clearing data inside mMovies

                    // content resolver >> content provider >> database
                    cursorData = getContentResolver().query(Contract.FavoriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            Contract.FavoriteEntry.COLUMN_MOVIE_ID);


                    int column_index_movie_id =
                            cursorData.getColumnIndex(Contract.FavoriteEntry.COLUMN_MOVIE_ID);

                    int column_index_movie_title =
                            cursorData.getColumnIndex(Contract.FavoriteEntry.COLUMN_MOVIE_TITLE);
//                    LogUtil.logStuff( "column index for movie_id " + String.valueOf(column_index_movie_id) );

                    while( cursorData.moveToNext() ) {

                        try {

                            int movie_id = cursorData.getInt(column_index_movie_id);
                            String movie_title = cursorData.getString(column_index_movie_title);
    //                        LogUtil.logStuff( String.valueOf( movie_id ) + " : " + movie_title);

                            Uri uri1 = Uri.parse(BASE_URL).buildUpon()
                                        .appendPath( String.valueOf(movie_id) )
                                        .appendQueryParameter(QUERY_PARAM, NetworkUtil.API_KEY)
                                        .build();
    //                        LogUtil.logStuff( uri1.toString() );

                            URL url1 = NetworkUtil.uri_to_url(uri1);
                            String request_results = NetworkUtil.goToWebsite(url1);
    //                        LogUtil.logStuff( request_results );


                            Movie movie1 = new Movie();
                            ArrayList<Movie> fav_list_out =  (ArrayList<Movie>) JSONUtil.get_json_object(movie1, request_results);
//                            LogUtil.logStuff( String.valueOf(movie_id) + ": " + String.valueOf( fav_list_out.get(0).getId() ) );

//                            {m0, m1, m2} << {m0}
                            mMovies.add( fav_list_out.get(0) );
//                            LogUtil.logStuff(  String.valueOf( mMovies.size() ) + ": " + String.valueOf( mMovies.get( mMovies.size()-1 ).getId() ) );

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } // while
//                    LogUtil.logStuff( ">>>>>>>> movieIdList size: " + String.valueOf( movieIdList.size() ) );

                    return cursorData;
                } // loadInBackground()

            }; // AsyncTaskLoader()


        } // onCreateLoader()

        @Override
        public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
//            mProgressbar.setVisibility(View.INVISIBLE);

            if (data != null && data.getCount() > 0) {

                TextView tv_sort_movie = findViewById(R.id.tv_sort_movie);
                tv_sort_movie.setText("Favorites");

                mMovieAdapter.swapData(mMovies);
//                LogUtil.logStuff(  String.valueOf( mMovies.size() ) + ": inside onLoadFinished()" );

            } else {
                Toast toast1 = Toast.makeText(MainActivity.this,
                        "Your Favourite list is empty! ", Toast.LENGTH_SHORT);
                toast1.show();
            }

        } // onLoadFinished()

        @Override
        public void onLoaderReset( Loader<Cursor> loader) {
        }

    }; // new LoaderManager



} // class MainActivity
