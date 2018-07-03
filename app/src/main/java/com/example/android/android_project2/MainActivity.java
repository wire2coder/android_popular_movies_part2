/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Parcelable;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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



public class MainActivity extends AppCompatActivity
        implements
        MovieAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks< ArrayList<Movie> > {

    /*
    * fields
    * */

    String TAG = MainActivity.this.getClass().getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String QUERY_PARAM = "api_key";
    private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static String BASE_URL_POPULAR_HIGHEST_RATE = "https://api.themoviedb.org/3/movie/top_rated";

    private ProgressBar mProgressbar;

    static String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    static String BUNDLE_RECYCLER_MOVIEDATA = "recycler_moviedata";

    private static final int CURSOR_LOADER_ID = 13;
    private static final int MOVIE_LOADER_ID = 11;

    ArrayList<Movie> favMovieList = new ArrayList<>(); // for fav movies
    ArrayList<Movie> mMovies = new ArrayList<>(); // for most popular movies

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

            mRecyclerView = findViewById(R.id.rv_movies);
            StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(3, 1);

            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mMovieAdapter = new MovieAdapter(MainActivity.this, MainActivity.this);
            mRecyclerView.setAdapter(mMovieAdapter);

            if (savedInstanceState != null) {

                Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
                mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
//                mMovies = savedInstanceState.getParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA);

            } else {

                Bundle queryBundle = new Bundle();
//            queryBundle.putString();
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, queryBundle, this);

            }





            /* make a URL */
//            URL url = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);

            /* run the AsyncTask to get movies from the server https://stackoverflow.com/questions/3921816/can-i-pass-different-types-of-parameters-to-an-asynctask-in-android */
//            NetworkTask networkTask = new NetworkTask(mMovieAdapter);
//            networkTask.execute(url);

        } // else


    } // onCreate









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

//                URL url_most_popular = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);
//                new NetworkTask(mMovieAdapter).execute(url_most_popular);
                Bundle queryBundle = new Bundle();
                getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, queryBundle, this);
                Toast.makeText(this, "Menu Pop Movie", Toast.LENGTH_SHORT).show();

                return true; // clickEvent data is 'consumed'


            case R.id.mi_highest_rate:

//                URL url_toprated = NetworkUtil.makeUrl(BASE_URL_POPULAR_HIGHEST_RATE, -1, 0);
//                new NetworkTask(mMovieAdapter).execute(url_toprated);

                return true; // clickEvent data is 'consumed'



            case R.id.mi_favorite_movie:

                /*
                * start the FavoriteMovie activity, you need an INTENT
                * https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
                * */

//                LoaderManager loaderManager = getSupportLoaderManager();
//                loaderManager.restartLoader(CURSOR_LOADER_ID, null, fav_loader);

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
//        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onListItemClick(Movie clickedItemIndex) {
//        Toast.makeText(MainActivity.this, "I clicked on an item", Toast.LENGTH_SHORT).show();

        Intent detailActivityIntent = new Intent(MainActivity.this, DetailActivity.class);
        Parcelable parcelableMovie = clickedItemIndex;

        detailActivityIntent.putExtra("movie_object", parcelableMovie);
        startActivity(detailActivityIntent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putParcelableArrayList("list1",  mMovies);

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

                Toast.makeText(getApplicationContext(), "onStartLoading ArrayList<Movie>", Toast.LENGTH_SHORT).show();

                if (args == null) {
                    return;
                }
//                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public ArrayList<Movie> loadInBackground() {

                ArrayList<Movie> movie_list_out;

                URL url = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);
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

        } else {
            Toast.makeText(MainActivity.this, "Can't connect to the website", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {

    }


} // class MainActivity
