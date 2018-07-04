/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.AsyncTask;

import android.os.AsyncTask;

import com.example.android.android_project2.Adapter.MovieAdapter;
import com.example.android.android_project2.MainActivity;
import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.StringUtil;

import java.net.URL;
import java.util.List;

/* AsyncTask class for making internet request */
public class NetworkTask extends AsyncTask<URL, Void, String> {


    /* class and member variables */
    private String results = null;
    private MovieAdapter mMovieAdapter;


    /* Constructor */
    public NetworkTask(MovieAdapter adapter_in) {
        mMovieAdapter = adapter_in;
    }

    @Override
    protected String doInBackground(URL... params) {

        URL url1 = (URL) params[0];

        /* go get data from server */
        results = NetworkUtil.goToWebsite(url1); // >> Strings[ {},{} ]

        /* this results go to onPostExecute() */
        return results;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);

        List<Movie> movies1 = StringUtil.stringToJson(results);
//        LogUtil.logStuff( String.valueOf( movies1.size() ) );

        mMovieAdapter.notifyDataSetChanged();

        /* SOURCE: http://androidadapternotifiydatasetchanged.blogspot.com/
        * .notifyDataSetChanged() only works IF YOU 'CRUD' YOUR DATA FIRST
        * */

    }

} // class NetworkTask
