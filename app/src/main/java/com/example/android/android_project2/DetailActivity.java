/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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


    /* BEFORE create */
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

    } // BEFORE CREATE

} // class DetailActivity
