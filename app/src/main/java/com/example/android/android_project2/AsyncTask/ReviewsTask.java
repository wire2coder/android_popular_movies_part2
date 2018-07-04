/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.AsyncTask;

import android.os.AsyncTask;

import com.example.android.android_project2.Adapter.ReviewsAdapter;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.StringUtil;

import java.net.URL;
import java.util.List;

public class ReviewsTask extends AsyncTask<URL, Void, String> {

    /*
    * Fields
    * */

    private String results = null;
    private ReviewsAdapter mReviewsAdapter;


    /*
    * Constructor
    * */

    public ReviewsTask(ReviewsAdapter adapter) {
        this.mReviewsAdapter = adapter;
    }


    @Override
    protected String doInBackground(URL... params) {

        URL url1 = (URL) params[0];
        results = NetworkUtil.goToWebsite(url1);

        return results;
    }


    @Override
    protected void onPostExecute(String reviews) {
        super.onPostExecute(reviews);

        List<MovieReview> list1 = StringUtil.makeReviewList(reviews);
        mReviewsAdapter.swapData(list1);
    }
}
