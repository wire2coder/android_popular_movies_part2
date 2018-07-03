/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.android_project2.Adapter.MovieAdapter;
import com.example.android.android_project2.AsyncTask.NetworkTask;
import com.example.android.android_project2.Database.Contract;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.StringUtil;
import com.example.android.android_project2.Util.ToastUtil;
import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    /*
    * fields
    * */

    String TAG = MainActivity.this.getClass().getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String QUERY_PARAM = "api_key";
    private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static String BASE_URL_POPULAR_HIGHEST_RATE = "https://api.themoviedb.org/3/movie/top_rated";

    private ProgressBar mProgressbar;

    private static String SAVED_GRID_LAYOUT = "grid_layout";
    private static String SAVED_GRID_DATA = "grid_data";

    private static final int CURSOR_LOADER_ID = 13;

//    List<Movie> favMovieList_outside = new ArrayList<>(); // for fav movies
    List<Movie> favMovieList = new ArrayList<>(); // for fav movies

    private List<Movie> mMovies = new ArrayList<>(); // for most popular movies
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;





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

            mGridView = (GridView) findViewById(R.id.gridview1);
            mMovieAdapter = new MovieAdapter(MainActivity.this, mMovies);
            mGridView.setAdapter(mMovieAdapter);

            /* 'attach' click listener to the GridView */
            mGridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {

                @Override
                /* https://stackoverflow.com/questions/13927601/how-to-show-toast-in-a-class-extended-by-baseadapter-get-view-method */
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    Movie movie1 = mMovies.get(position);

                    /* starting another activity */
                    Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

                    detailIntent.putExtra("position_of_the_view", String.valueOf(position));

                    detailIntent.putExtra("id", movie1.getId() );
                    detailIntent.putExtra("title", movie1.getTitle() );
                    detailIntent.putExtra("release_date", movie1.getRelease_date() );
                    detailIntent.putExtra("poster_path", movie1.getPoster_path() );
                    detailIntent.putExtra("vote_average", movie1.getVote_average() );
                    detailIntent.putExtra("overview", movie1.getOverview() );

                    /* start DetailActivity */
                    MainActivity.this.startActivity(detailIntent);

                    /* notify Dataset() make the gridView redraw itself
                     * and gridView call getView() again */
//                    mMovieAdapter.notifyDataSetChanged();

                }

            }); // setOnItemClick


            /* make a URL */
            URL url = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);

            /* run the AsyncTask to get movies from the server https://stackoverflow.com/questions/3921816/can-i-pass-different-types-of-parameters-to-an-asynctask-in-android */
            NetworkTask networkTask = new NetworkTask(mMovieAdapter);
            networkTask.execute(url);

        }


    } // onCreate





    /*
    * Loader Callbacks
    * */

    private LoaderManager.LoaderCallbacks<Cursor> fav_loader = new LoaderManager.LoaderCallbacks<Cursor>() {


        @Override
        public Loader<Cursor> onCreateLoader(int id, final Bundle args) {

            return new AsyncTaskLoader<Cursor>( getApplicationContext() ) {

                // Initialize a Cursor, this will hold all the task data
                Cursor mCursorData = null;
                String result = null;

                List<String> movieIdList = new ArrayList<>();


                // onStartLoading() is called when a loader first starts loading data
                @Override
                protected void onStartLoading() {
                        // Force a new load
                        forceLoad();
                }


                // loadInBackground() performs asynchronous loading of data
                @Override
                public Cursor loadInBackground() {
                    // Will implement to load data

                    // Query and load all task data in the background; sort by priority

                    try {

                        mCursorData = getContentResolver().query(Contract.FavoriteEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                Contract.FavoriteEntry.COLUMN_MOVIE_ID);


                        int column_index_movie_id =
                                mCursorData.getColumnIndex(Contract.FavoriteEntry.COLUMN_MOVIE_ID);
                        int column_index_movie_title =
                                mCursorData.getColumnIndex(Contract.FavoriteEntry.COLUMN_MOVIE_TITLE);
//                LogUtil.logStuff( "column index for movie_id " + String.valueOf(column_index_movie_id) );


                        while( mCursorData.moveToNext() ) {

                            int movie_id = mCursorData.getInt(column_index_movie_id);
                            String movie_title = mCursorData.getString(column_index_movie_title);
                            LogUtil.logStuff( String.valueOf( movie_id ) + " : " + movie_title);

                            movieIdList.add( String.valueOf(movie_id) );

                        }
                        LogUtil.logStuff( ">>>>>>>> movieIdList size: " + String.valueOf( movieIdList.size() ) );


                        favMovieList.clear();

                        for (int i=0; i < movieIdList.size(); i++ ) {
//                    LogUtil.logStuff( "current movie id: " + movieIdList.get(i).toString() );

                            Uri uri1 = Uri.parse(BASE_URL).buildUpon()
                                    .appendPath( movieIdList.get(i) )
                                    .appendQueryParameter(QUERY_PARAM, NetworkUtil.API_KEY)
                                    .build();
//                    LogUtil.logStuff("current uri: " + uri1.toString() );

                            try {

                                URL url1 = new URL( uri1.toString() );
                                LogUtil.logStuff("current url: " + url1.toString() );

                                result = NetworkUtil.goToWebsite(url1);
//                                LogUtil.logStuff("current result: " + result);

                            } catch (MalformedURLException m ) {
                                m.printStackTrace();
                            }


                            JSONObject root = new JSONObject(result);

                            int vote_count = root.getInt("vote_count");
                            int id = root.getInt("id");
                            int vote_average = root.getInt("vote_average");

                            double popularity = root.getDouble("popularity");

                            Boolean video = root.getBoolean("video");
                            Boolean adult = root.getBoolean("adult");

                            String title = root.optString("title");
                            String poster_path = root.optString("poster_path");
                            String original_language = root.optString("original_language");
                            String original_title = root.optString("original_title");
                            String backdrop_path = root.optString("backdrop_path");
                            String overview = root.optString("overview");
                            String release_date = root.optString("release_date");

                            Movie movie = new Movie(vote_count, id, vote_average, popularity, video, adult,
                                    title, poster_path, original_language, original_title, backdrop_path,
                                    overview, release_date);
//                        LogUtil.logStuff( "current movie title: " + movie.getTitle() );
//                        LogUtil.logStuff( "current movie id: " + movie.getPoster_path() );


                            favMovieList.add(movie);
//                            LogUtil.logStuff( "current movie title: " + favMovieList.get(i).getTitle() );

                        } // for



                    } catch (Exception e) {

                        Log.e(TAG, "Failed to asynchronously load data.");
                        e.printStackTrace();
                        return null;

                    }

                    LogUtil.logStuff( String.valueOf( "size of favMovieList : " + favMovieList.size() )  );

                    return mCursorData;

                } // loadInBackground

            }; //  return new AsyncTaskLoader<Cursor>



        } // onCreateLoader


        /**
         * Called when a previously created loader has finished its load.
         *
         * @param loader The Loader that has finished.
         * @param cursor_object The data generated by the Loader.
         */
        @Override
        public void onLoadFinished( Loader<Cursor> loader, Cursor cursor_object) {

//            mProgressbar.setVisibility(View.INVISIBLE);
            // Update the data that the adapter uses to create ViewHolders
//            mAdapter.swapCursor(data);



            if (favMovieList.size() == 0) {
                Toast.makeText(MainActivity.this, "You have not chosen any Favourite movies yet! ", Toast.LENGTH_LONG).show();
            } else {

                LogUtil.logStuff( String.valueOf( "favMovieList inside onLoadFinished: " + favMovieList.size() )  );

                mMovieAdapter = new MovieAdapter(MainActivity.this, favMovieList);
                mGridView.setAdapter(mMovieAdapter);

            } // if



        } // onLoadFinished


        /**
         * Called when a previously created loader is being reset, and thus
         * making its data unavailable.
         * onLoaderReset removes any references this activity had to the loader's data.
         *
         * @param loader The Loader that is being reset.
         */
        @Override
        public void onLoaderReset( Loader<Cursor> loader) {

        }


    }; // LoaderManager



    /*
    * helpers
    * */



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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mi_most_popular:

                URL url_most_popular = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);
                new NetworkTask(mMovieAdapter).execute(url_most_popular);

                return true; // clickEvent data is 'consumed'


            case R.id.mi_highest_rate:

                URL url_toprated = NetworkUtil.makeUrl(BASE_URL_POPULAR_HIGHEST_RATE, -1, 0);
                new NetworkTask(mMovieAdapter).execute(url_toprated);

                return true; // clickEvent data is 'consumed'



            case R.id.mi_favorite_movie:

                /*
                * start the FavoriteMovie activity, you need an INTENT
                * https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
                * */

                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(CURSOR_LOADER_ID, null, fav_loader);

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this restarts the loader to re-query the underlying data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putString(SAVED_GRID_LAYOUT, mGridView.onSaveInstanceState() );
//        outState.putParcelable(SAVED_GRID_DATA, mMovies);


    }
} // class MainActivity
