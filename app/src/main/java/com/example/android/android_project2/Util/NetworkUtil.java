/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.Util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.example.android.android_project2.MainActivity;
import com.example.android.android_project2.Util.MovieApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static String TAG = NetworkUtil.class.getClass().getSimpleName();

    private static String PARAM_QUERY = "api_key";
    public static String API_KEY = MovieApi.getApi(); // TODO: add API KEY HERE


    public static URL uri_to_url (Uri uri_in) {

        URL url1;

        try {

            url1 = new URL( uri_in.toString() );

        } catch (MalformedURLException m) {
            m.printStackTrace();
            return null;
        }

        return url1;
    }



    public static String goToWebsite(URL url)  {

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



    /*
        checking for internet connection
        https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        https://www.youtube.com/watch?v=DMhnJK38RlQ
    */
    public static boolean isThereInternet(Context context) {

        boolean connected = false;

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting() ) {
            connected = true;
           return connected;
        } else {
            return connected;
        }

    }





    public static String goToWebsite3 (Uri uri_in) throws SocketTimeoutException, MalformedURLException, IOException {

        URL url1;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String stringStreamedData = null;

        try {

            url1 = new URL( uri_in.toString() );

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
        }


//            open http connection
        urlConnection = (HttpURLConnection) url1.openConnection();
        urlConnection.setConnectTimeout(3000);


        try {
//            save stream into a variable
            inputStream = urlConnection.getInputStream();

//            use 'scanner' to read the data stream
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return stringStreamedData = scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

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
