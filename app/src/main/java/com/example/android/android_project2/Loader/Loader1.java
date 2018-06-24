package com.example.android.android_project2.Loader;


import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.android_project2.R;
import com.example.android.android_project2.Util.NetworkUtils_U;
import com.example.android.android_project2.Util.ToastUtil;

import java.io.IOException;
import java.net.URL;

public class Loader1 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {


    /*
    * fileds
    * */


    private static final String SEARCH_QUERY_URL_EXTRA = "query";
    private static final int GITHUB_SEARCH_LOADER = 22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);
            mUrlDisplayTextView.setText(queryUrl);
        }
        */


        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);

    }





    private void makeGithubSearch() {

        URL githubSearchUrl = NetworkUtils_U.buildUrl("time");

        Bundle queryBundle = new Bundle();

        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubSearchUrl.toString() );

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);


        if (githubSearchLoader == null) {

            getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);

        } else {

            getSupportLoaderManager().restartLoader(GITHUB_SEARCH_LOADER, null, this);

        }


    }



    @Override
    public Loader<String> onCreateLoader(int i, final Bundle args) {

        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {

                /* If no arguments were passed, we don't have a query to perform. Simply return. */
                if (args == null) {
                    return;
                }

                forceLoad(); // run loadInBackground() below
            }



            @Override
            public String loadInBackground() {

                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);


                if (searchQueryUrlString == null) {
                    return null;
                }


                try {
                    URL githubUrl = new URL(searchQueryUrlString);
                    String githubSearchResults = NetworkUtils_U.getResponseFromHttpUrl(githubUrl);
                    return githubSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }

        };


    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        ToastUtil.makeMeAToast(this, "I AM DONE");
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

}
