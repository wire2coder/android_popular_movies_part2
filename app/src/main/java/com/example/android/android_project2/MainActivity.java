/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_project2.Adapter.MovieAdapter;
import com.example.android.android_project2.AsyncTask.NetworkTask;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.ToastUtil;
import com.facebook.stetho.Stetho;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.this.getClass().getSimpleName();



    private static String BASE_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    private static String BASE_URL_POPULAR_HIGHEST_RATE = "https://api.themoviedb.org/3/movie/top_rated";



    /* Don't forget to initialize with new ArrayList<data type>(); */
    private List<Movie> mMovies = new ArrayList<Movie>();
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    private int mFavoriteMovieCount;




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

            /* 'attach' click listener to the GridView */
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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

                    /* notifyDataset() make the gridView redraw itself
                     * and gridView call getView() again */
                    mMovieAdapter.notifyDataSetChanged();

                }

            }); // setOnItemClick


            /* make a new MovieAdapter */
            mMovieAdapter = new MovieAdapter(MainActivity.this, mMovies);

            /* set data source */
            mGridView.setAdapter(mMovieAdapter);

            /* make a URL */
            URL url = NetworkUtil.makeUrl(BASE_URL_POPULAR, -1, 0);

            /* run the AsyncTask to get movies from the server */
            /* https://stackoverflow.com/questions/3921816/can-i-pass-different-types-of-parameters-to-an-asynctask-in-android */

            NetworkTask networkTask = new NetworkTask(mMovieAdapter);
            networkTask.execute(url);

        }


    } // onCreate




    /* helper: checking for internet connection
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





    /* helper: show a dialog box if the 'device' is not connected to the internet */
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
                LogUtil.logStuff( url_most_popular.toString() );
                new NetworkTask(mMovieAdapter).execute(url_most_popular);

                return true; // clickEvent data is 'consumed'


            case R.id.mi_highest_rate:

                URL url_toprated = NetworkUtil.makeUrl(BASE_URL_POPULAR_HIGHEST_RATE, -1, 0);
                new NetworkTask(mMovieAdapter).execute(url_toprated);

                return true; // clickEvent data is 'consumed'


            case R.id.mi_favorite_movie:

                /* start the FavoriteMovie activity, you need an INTENT THINGY
                * https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
                * */
                Intent fMovieIntent = new Intent(MainActivity.this, FavoriteMoviesActivity.class);
                startActivity(fMovieIntent);

                return true; // clickEvent data is 'consumed' here


            default:
                return super.onOptionsItemSelected(item);
        }

    }





} // class MainActivity
