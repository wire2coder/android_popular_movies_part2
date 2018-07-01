/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_project2.Adapter.ReviewsAdapter;
import com.example.android.android_project2.Adapter.TrailersAdapter;
import com.example.android.android_project2.AsyncTask.ReviewsTask;
import com.example.android.android_project2.AsyncTask.TrailersTask;
import com.example.android.android_project2.Database.Contract;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity
    implements TrailersAdapter.ListItemClickListener {


    /*
     * Fields
     * */

    private static String TAG = DetailActivity.class.getClass().getSimpleName();

    private static String BASE_URL_MOVIE_VIDEOS;
    private static String BASE_URL_MOVIE_REVIEWS;
//    https://api.themoviedb.org/3/movie/383498/reviews?api_key=
//    https://api.themoviedb.org/3/movie/383498/movie?api_key=

    private static final int SQL_LOADER_ID = 22;

    private List<TrailersThumbNails> mTrailersThumbNails = new ArrayList<>();
    private List<MovieReview> mMovieReviews = new ArrayList<>();

    private TrailersAdapter trailersAdapter1;
    private ReviewsAdapter mReviewsAdapter;

    int movid_id;
    private String id_string;
    private Uri uri;
    private String TRAILERS_URL = "https://api.themoviedb.org/3/movie/"+ id_string +"/trailers";
    private String REVIEWS_URL = "https://api.themoviedb.org/3/movie/"+ id_string +"/reviews";


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
    @BindView(R.id.cb_favorite)
    CheckBox cb_favorite;


    /*
    * Loaders for DATABASE
    * */


    private LoaderManager.LoaderCallbacks<Boolean> sqlOperation
            = new LoaderManager.LoaderCallbacks<Boolean>() {

        @NonNull
        @Override
        public Loader<Boolean> onCreateLoader(int id, @Nullable final Bundle args) {

            return new AsyncTaskLoader<Boolean>(DetailActivity.this) {

                @Override
                protected void onStartLoading() {
                    forceLoad();
                }


                @Nullable
                @Override
                public Boolean loadInBackground() {

                    Uri uri1 = Uri.parse( args.getString("URI") );

                    if ( args.getString("SQL_Operation") == "select") {

                        // query
                        Cursor cursor1 = getContentResolver().query(uri1,
                                null,
                                null,
                                null,
                                null);

                        return cursor1.getCount() > 0 ? true : false;

                    } else if (args.getString("SQL_Operation") == "delete") {

                        int row_deleted = getContentResolver().delete(uri1,
                                null,
                                null);

                    } else if (args.getString("SQL_Operation") == "insert") {

                        int myId = args.getInt("MOVIE_ID");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(Contract.TableEntry.COLUMN_MOVIEDBID, myId);

                        Uri uri_inserted = getContentResolver().insert(uri1, contentValues);

                    }

                    return null;
                }

            };

        }

        @Override
        public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean result) {

            if (result != null) {
                cb_favorite.setChecked(result);
            }

        }

        @Override
        public void onLoaderReset(@NonNull Loader<Boolean> loader) {

        }
    };



    /*
    * onCreate
    * */

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


        movid_id = intent.getIntExtra("id", 0);
        id_string = Integer.toString(movid_id);

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

        trailersAdapter1 = new TrailersAdapter(this, mTrailersThumbNails, this);
        recyclerView_trailers.setLayoutManager(linearLayoutManager);
        recyclerView_trailers.setAdapter(trailersAdapter1);

        URL url1 = NetworkUtil.makeUrl(TRAILERS_URL, 1, movid_id);
        TrailersTask trailersTask = new TrailersTask(trailersAdapter1);
        trailersTask.execute(url1);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        RecyclerView rv_reviews = findViewById(R.id.rv_reviews);

        mReviewsAdapter = new ReviewsAdapter(this, mMovieReviews);
        rv_reviews.setLayoutManager(linearLayoutManager2);
        rv_reviews.setAdapter(mReviewsAdapter);

        URL reviews_url = NetworkUtil.makeUrl(REVIEWS_URL, 2, movid_id);
        ReviewsTask reviewsTask = new ReviewsTask(mReviewsAdapter);
        reviewsTask.execute(reviews_url);


        uri = Contract.TableEntry.CONTENT_URI;
        Uri uri1 = uri.buildUpon().appendPath(id_string).build();
        LogUtil.logStuff(uri1.toString());

        /*
        * Making Bundle, running Loading
        * */


        Bundle sqlBundle = new Bundle();
        sqlBundle.putString("SQL_Operation", "select");
        sqlBundle.putString("URI", uri1.toString() );
//        getSupportLoaderManager().restartLoader(SQL_LOADER_ID, sqlBundle, sqlOperation);




    } // onCreate()


    /*
    * Implementing onClick
    * */

    @Override
    public void onListItemClick(String youtube_source) {


        Uri uri1 = Uri.parse("http://www.youtube.com")
                .buildUpon()
                .appendPath("watch")
                .appendQueryParameter("v", youtube_source)
                .build();

        Intent youtube_intent = new Intent( Intent.ACTION_VIEW );

        youtube_intent.setData(uri1);

        if ( youtube_intent.resolveActivity( getPackageManager() ) != null ) {
            startActivity(youtube_intent);
        }

    }


    /*
    * helper, onClickAddFavorite
    * */

    public void onClickAddFavorite(View view) {

        if ( cb_favorite.isChecked() ) {

            Bundle bundle1 = new Bundle();
            bundle1.putString("SQL_Operation", "insert");
            bundle1.putString("URI", uri.toString() );
            bundle1.putInt("MOVIE_ID", movid_id);

            getSupportLoaderManager().restartLoader(SQL_LOADER_ID, bundle1, sqlOperation);

        } else {

            Bundle bundle2 = new Bundle();
            bundle2.putString("SQL_Operation", "delete");
            bundle2.putString("URI", uri.toString() );

            getSupportLoaderManager().restartLoader(SQL_LOADER_ID, bundle2, sqlOperation);

        }

    }





} // class DetailActivity
