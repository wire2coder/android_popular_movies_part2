/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static String TAG = NetworkUtil.class.getClass().getSimpleName();

    /*
     * Uri uri1 = Uri.parse(GITHUB_BASE_URL)
     .buildUpon()
     .appendQueryParameter(PARAM_QUERY, PARAM_QUERY_VALUE)
     .build();

     https://api.github.com/search/repositories ? q = good1
     */


    private static String PARAM_QUERY = "api_key";
    // TODO: add API KEY HERE
    private static String API_KEY = MovieApi.getApi();


    public static String goToWebsite(URL url) {

        /* declaring variables */
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String stringStreamedData = null;

        try {
//            open http connection
            urlConnection = (HttpURLConnection) url.openConnection();

//            save stream into a variable
            inputStream = urlConnection.getInputStream();

//            use 'scanner' to read the data stream
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                stringStreamedData = scanner.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return stringStreamedData;

    } // goToWebsite


    public static URL makeUrl(String inputString) {

        URL url = null;

        /* parse string and save it into URI */
        Uri uri = Uri.parse(inputString)
                .buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();

        /* make URL from Uri */
        /* covert incoming URI to URL */
        try {

            url = new URL(uri.toString());
//            LogUtil.logStuff(url.toString() );

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    } // makeUrl

} // class NetworkUtil
