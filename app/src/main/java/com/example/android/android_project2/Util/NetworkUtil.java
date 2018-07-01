/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2.Util;

import android.net.Uri;

import com.example.android.android_project2.Util.MovieApi;

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


    public static String trailersUriBuilder(int id1) {

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(id1))
                .appendPath("trailers")
                .appendQueryParameter("api_key", API_KEY);

        String url_string = builder.build().toString();
        return url_string;
    }


    public static String reviewsUriBuilder(int id1) {

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(id1))
                .appendPath("reviews")
                .appendQueryParameter("api_key", API_KEY);

        String url_string = builder.build().toString();
        return url_string;
    }


    public static URL makeUrl(String inputString, int choice, int id) {

        URL url = null;
        Uri uri = null;


        switch (choice) {

            case -1:
                /* parse string and save it into URI */
                uri = Uri.parse(inputString)
                        .buildUpon()
                        .appendQueryParameter(PARAM_QUERY, API_KEY)
                        .build();
                break;

            case 1:
                uri = Uri.parse( trailersUriBuilder(id) );
                break;

            case 2:
                uri = Uri.parse( reviewsUriBuilder(id) );
                break;

            default:
                break;

        } // switch


        /* make URL from Uri */
        /* covert incoming URI to URL */
        try {

            url = new URL(uri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    } // makeUrl

} // class NetworkUtil
