/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.android_project2.Adapter.ReviewsAdapter;
import com.example.android.android_project2.Adapter.TrailersAdapter;
import com.example.android.android_project2.AsyncTask.ReviewsTask;
import com.example.android.android_project2.AsyncTask.TrailersTask;
import com.example.android.android_project2.Database.Contract;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//    https://api.themoviedb.org/3/movie/383498/reviews?api_key=
//    https://api.themoviedb.org/3/movie/383498/movie?api_key=

public class DetailActivity extends AppCompatActivity
    implements TrailersAdapter.ListItemClickListener {

    private static String TAG = DetailActivity.class.getClass().getSimpleName();

    private List<TrailersThumbNails> mTrailersThumbNails = new ArrayList<>();
    private List<MovieReview> mMovieReviews = new ArrayList<>();

    private TrailersAdapter trailersAdapter1;
    private ReviewsAdapter mReviewsAdapter;

    private int movie_id;
    private String id_string;

    private String TRAILERS_URL = "https://api.themoviedb.org/3/movie/"+ id_string +"/trailers";
    private String REVIEWS_URL = "https://api.themoviedb.org/3/movie/"+ id_string +"/reviews";
    private String picUrl = "http://image.tmdb.org/t/p/w500//";

    private int id;
    private int vote_average;

    private String title;
    private String poster_path;
    private String original_title;
    private String backdrop_path;
    private String overview;
    private String release_date;


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


        /*
         * Making RecyclerView, Trailers
         * */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView_trailers = findViewById(R.id.rv_trailers);

        trailersAdapter1 = new TrailersAdapter(this, mTrailersThumbNails, this);
        recyclerView_trailers.setLayoutManager(linearLayoutManager);
        recyclerView_trailers.setAdapter(trailersAdapter1);



        /*
         * Making RecyclerView, Movie Reviews
         * */
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        RecyclerView rv_reviews = findViewById(R.id.rv_reviews);

        mReviewsAdapter = new ReviewsAdapter(this, mMovieReviews);
        rv_reviews.setLayoutManager(linearLayoutManager2);
        rv_reviews.setAdapter(mReviewsAdapter);



        Intent intentThatStartThisActivity = getIntent();
        if (intentThatStartThisActivity.hasExtra("movie_detail") ) {

            Movie movie = getIntent().getExtras().getParcelable("movie_detail");

            movie_id = movie.getId();
            id_string = Integer.toString(movie_id);

            vote_average = movie.getVote_average();

            title = movie.getTitle();
            poster_path = movie.getPoster_path();
            original_title = movie.getOriginal_title();
            backdrop_path = movie.getBackdrop_path();
            overview = movie.getOverview();
            release_date = movie.getRelease_date();

            tv_title.setText(title);
            tv_release_date.setText(release_date);
            tv_vote_average.setText( String.valueOf(vote_average) );
            tv_overview.setText(overview);

            Uri uri1 = Uri.parse(picUrl).buildUpon()
                    .appendEncodedPath( poster_path )
                    .build();

            Picasso.with(DetailActivity.this)
                    .load( uri1 )
                    .into( iv_poster );

        }


        URL trailer_url = NetworkUtil.makeUrl(TRAILERS_URL, 1, movie_id);
        URL reviews_url = NetworkUtil.makeUrl(REVIEWS_URL, 2, movie_id);


        if ( !NetworkUtil.isThereInternet(DetailActivity.this) ) { // >> no internet

            /* show 'no internet' dialogbox */
            internetDialog(DetailActivity.this).show();

        } else { // >> yes internet

            // COMPLETED: fix the crash right here, before calling AsyncTask to get data from the Internet
            TrailersTask trailersTask = new TrailersTask(trailersAdapter1);
            trailersTask.execute(trailer_url);

            ReviewsTask reviewsTask = new ReviewsTask(mReviewsAdapter);
            reviewsTask.execute(reviews_url);

        } // else


        // content://com.example.android.android_project2/favorite/1
        if( queryOne(id_string) == 1 ) {
            cb_favorite.setChecked(true);
        }


    } // onCreate()


    /* show a dialog box if the 'device' is not connected to the internet */
    public AlertDialog.Builder internetDialog(Context context) {

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


    /*
    * helper, onClickAddFavorite
    * */

    public void onClickAddFavorite(View view) {

        if ( cb_favorite.isChecked() ) {
           insertOne(id_string, title);
        } else {
            deleteOne(id_string);
//            deleteAll();
        }

    }


    public int queryOne(String idToQuery) {

        // content://com.example.android.android_project2/favorite/299536
        Uri uriToQuery = Contract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(idToQuery).build();

        Cursor result_cursor =
                getContentResolver().query(uriToQuery,
                        null,
                        null,
                        null,
                        null
                );


        int cursor_count = result_cursor.getCount();

//        Toast.makeText(getBaseContext(), String.valueOf(cursor_count), Toast.LENGTH_SHORT).show();

        return cursor_count;
    }


    public void queryAll() {

        Cursor result_cursor = getContentResolver().query(Contract.FavoriteEntry.CONTENT_URI
                , null
                ,null
                ,null
                ,null);

        int cursor_count = result_cursor.getCount();

//        Toast.makeText(getBaseContext(), String.valueOf(cursor_count), Toast.LENGTH_SHORT).show();

    }


    public void insertOne(String idToInsert, String title) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.FavoriteEntry.COLUMN_MOVIE_ID, idToInsert);
        contentValues.put(Contract.FavoriteEntry.COLUMN_MOVIE_TITLE, title);

        Uri uri = getContentResolver().insert(Contract.FavoriteEntry.CONTENT_URI, contentValues);

        if(uri != null) {
//            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_SHORT).show();
        }

        // Finish activity (this returns back to MainActivity)
//            finish();
    }


    public void deleteAll() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();

        // com.example.android.android_project2/favorite
        Uri uriToDelete = Contract.FavoriteEntry.CONTENT_URI.buildUpon().build();
        int tasksDeleted = contentResolver.delete(uriToDelete, null, null);

//        Toast.makeText(getBaseContext(), uriToDelete.toString(), Toast.LENGTH_SHORT).show();

    }


    public void deleteOne(String idToDelete) {

        ContentResolver contentResolver = getBaseContext().getContentResolver();

        // content://com.example.android.android_project2/favorite/299536
        Uri uriToDelete = Contract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(idToDelete).build();

        int tasksDeleted = contentResolver.delete(uriToDelete, null, null);

        if (tasksDeleted < 1) {
//            Toast.makeText(getBaseContext(), uriToDelete.toString(), Toast.LENGTH_SHORT).show();
        }
    }


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


} // class DetailActivity
