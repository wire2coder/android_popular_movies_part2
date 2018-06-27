/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;


import android.content.Context;
import android.content.Intent;
import android.os.DeadObjectException;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.android_project2.Adapter.TrailersAdapter;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtils_U;
import com.example.android.android_project2.Util.StringUtil;
import com.example.android.android_project2.Util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* class
* */


public class DetailActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<String> {


    /*
     * Fields
     * https://api.themoviedb.org/3/movie/343611/trailers?api_key=
     * https://img.youtube.com/vi/k3kzqVliF48/mqdefault.jpg
     * */


    private static String TAG = DetailActivity.class.getClass().getSimpleName();

    private static final int TRAILER_SEARCH_LOADER = 22;
    private static final int REVIEWS_SEARCH_LOADER = 23;

    private static final String NOT_AVAILABLE = "Not available";
    private static final String MOVIEDB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String TRAILER_THUMBNAIL_BASE_PATH = "https://img.youtube.com/vi/";
    private static final String YOUTUBE_BASE_PATH = "https://www.youtube.com/watch?v=";

    private static String BASE_URL_MOVIE_VIDEOS;
    private static String BASE_URL_MOVIE_REVIEWS;
//    https://api.themoviedb.org/3/movie/383498/reviews?api_key=
//    https://api.themoviedb.org/3/movie/383498/movie?api_key=

    private Context mContext;

    private Movie mMovieSelected;
    private boolean mIsMovieFavorite;

    private List<TrailersThumbNails> mTrailersThumbNails = new ArrayList<>();
    private TrailersAdapter trailersAdapter1;

    private ImageView iv_detail_activity_test;
    private String id_string;


    // using 'Butter Knife' library
    // http://jakewharton.github.io/butterknife/
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_release_date)
    TextView tv_release_date;
    @BindView(R.id.tv_overview)
    TextView tv_overview;
    @BindView(R.id.tv_vote_average)
    TextView tv_vote_average;
    @BindView(R.id.iv_poster)
    ImageView iv_poster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        // bind the view using butterknife
        ButterKnife.bind(DetailActivity.this);

        /*
         * shows the LEFT ARROW on the top left corner of the screen
         * and add this line in AndroidManifest
         *
         * <activity android:name=".DetailActivity"
         * android:parentActivityName=".MainActivity" />
         */
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException n) {
            n.printStackTrace();
        }


        /* intent always exist... */
        Intent intent = getIntent();


        int id = intent.getIntExtra("id", 0);
        id_string = Integer.toString(id);

        String title = intent.getStringExtra("title");
        String release_date = intent.getStringExtra("release_date");
        String overview = intent.getStringExtra("overview");
        String poster_path = intent.getStringExtra("poster_path");

        int vote_average_int = intent.getIntExtra("vote_average", 0);
        String vote_average = String.valueOf(vote_average_int);

        tv_title.setText(title);
        tv_release_date.setText(release_date);
        tv_vote_average.setText(vote_average);
        tv_overview.setText(overview);

        Picasso.with(DetailActivity.this)
                .load(poster_path)
                .into(iv_poster);



        /*
        * Making RecyclerView
        * */



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView_trailers = findViewById(R.id.rv_trailers);

        trailersAdapter1 = new TrailersAdapter(this, mTrailersThumbNails);

        recyclerView_trailers.setLayoutManager(linearLayoutManager);
        recyclerView_trailers.setAdapter(trailersAdapter1);



        /*
        * Making Bundle, running Loading
        * */


        Bundle trailerBundle = new Bundle();
        trailerBundle.putString("url_in_bundle", "https://api.themoviedb.org/3/movie/"+ id_string +"/trailers?api_key=55288907df50d7a713d92755304b6334");

        Bundle movie_reviews_bundle = new Bundle();
        movie_reviews_bundle.putString("url_in_bundle",
                "https://api.themoviedb.org/3/movie/383498/reviews?api_key=55288907df50d7a713d92755304b6334");

        getSupportLoaderManager().initLoader(TRAILER_SEARCH_LOADER, trailerBundle, this);




    } // onCreate()



    /*
    * Implementation of Loaders
    * */



    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {


        // use switch statement to
        switch(id) {

            case TRAILER_SEARCH_LOADER:
                return new AsyncTaskLoader<String>(this) {

                    @Override
                    protected void onStartLoading() {
                        if (args == null) {
                            return;
                        }

                        forceLoad(); // run loadInBackground() below
                    }


                    @Override
                    public String loadInBackground() {

                        String url_from_bundle = args.getString("url_in_bundle");
//                LogUtil.logStuff(url_trailers);

                        try {

                            // making GET request
                            URL url = new URL(url_from_bundle);
                            String reponse = NetworkUtils_U.getResponseFromHttpUrl(url);
                            return reponse; // data go to onLoadFinished() below

                        } catch (IOException i) {

                            i.printStackTrace();
                            return null;

                        }

                    }


                };


            default:
                throw new RuntimeException("Loader does not exist: " + id);

        } // switch


    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        switch (loader.getId() ) {

            case TRAILER_SEARCH_LOADER:

                    mTrailersThumbNails = StringUtil.makeList1(data);
                    trailersAdapter1.swapData(mTrailersThumbNails);
                    break;

            default:

                throw new RuntimeException("Loader not implemented: " + loader.getId() );

        }



        ToastUtil.makeMeAToast(this, TAG  + " Loader Done");

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

        ToastUtil.makeMeAToast(this, TAG  + " Loader Reset");

    }



} // class DetailActivity
