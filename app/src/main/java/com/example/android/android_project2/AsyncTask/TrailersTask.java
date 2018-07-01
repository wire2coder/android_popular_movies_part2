package com.example.android.android_project2.AsyncTask;

import android.os.AsyncTask;

import com.example.android.android_project2.Adapter.TrailersAdapter;
import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.Util.LogUtil;
import com.example.android.android_project2.Util.NetworkUtil;
import com.example.android.android_project2.Util.StringUtil;

import java.net.URL;
import java.util.List;

public class TrailersTask extends AsyncTask<URL, Void, String> {

    private String results = null;
    private TrailersAdapter mTrailsAdapter;


    public TrailersTask(TrailersAdapter adapter_in) {
        mTrailsAdapter = adapter_in;
    }


    @Override
    protected String doInBackground(URL... params) {

        URL url1 = (URL) params[0];
        results = NetworkUtil.goToWebsite(url1);

//        LogUtil.logStuff(results);

        return results;
    }

    @Override
    protected void onPostExecute(String results) {
        super.onPostExecute(results);

        List<TrailersThumbNails> trails1 = StringUtil.makeList1(results);
        mTrailsAdapter.swapData(trails1);

    }
}
