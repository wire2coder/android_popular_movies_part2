/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.Util;

import android.util.Log;

import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.MovieData.TrailersThumbNails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final public class StringUtil {

    /*
    * Fields
    * */


    static private ArrayList<Movie> mMovieList = new ArrayList<>();
    static private ArrayList<TrailersThumbNails> mTrailersThumbNails = new ArrayList<>();
    static private ArrayList<MovieReview> mMovieReview = new ArrayList<>();


    /*
    * static methods
    * */
    static public ArrayList<Movie> stringToJson(String inputString1) {


         int id;
         int vote_average;

         String title;
         String poster_path;
         String original_title;
         String backdrop_path;
         String overview;
         String release_date;


        /* clear all data from the List, if exist */
        if (mMovieList.size() != 0) {
            mMovieList.clear();
        }

        try {

            // convert 'input string to JSON'
            JSONObject rootDocument = new JSONObject(inputString1);

            int page = rootDocument.getInt("page");
            int total_results = rootDocument.getInt("total_results");
            int total_pages = rootDocument.getInt("total_pages");
            JSONArray jsonArray = rootDocument.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {

                id = jsonArray.getJSONObject(i).getInt("id");
                vote_average = jsonArray.getJSONObject(i).getInt("vote_average");

                title = jsonArray.getJSONObject(i).optString("title");
                poster_path = jsonArray.getJSONObject(i).optString("poster_path");
                original_title = jsonArray.getJSONObject(i).optString("original_title");
                backdrop_path = jsonArray.getJSONObject(i).optString("backdrop_path");
                overview = jsonArray.getJSONObject(i).optString("overview");
                release_date = jsonArray.getJSONObject(i).optString("release_date");

                Movie movie = new Movie( id,
                            vote_average, title, poster_path, original_title,
                            backdrop_path, overview, release_date);

                mMovieList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mMovieList;

    } // stringToJson



    static public List<TrailersThumbNails> makeList1(String inputString) {

        if (mTrailersThumbNails.size() != 0 ) {
            mTrailersThumbNails.clear();
        }

        try {

            JSONObject root_document = new JSONObject(inputString);
            JSONArray youtube = root_document.getJSONArray("youtube");

            if (youtube.length() != 0 ) {

                for (int x=0; x < youtube.length(); x++) {

                    String source = youtube.getJSONObject(x).getString("source");

                    mTrailersThumbNails.add( new TrailersThumbNails(source) );

                }

            } else {
                youtube.put(0, root_document);
            }


        } catch (JSONException j) {
            j.printStackTrace();
        }

        return mTrailersThumbNails;
    }


    static public List<MovieReview> makeReviewList(String inputString) {

        JSONObject root_document;
        JSONArray jsonArray = new JSONArray();

        if (mMovieReview.size() != 0 ) {
            mMovieReview.clear();
        }

        try {

            root_document = new JSONObject(inputString);
            int total_results = root_document.getInt("total_results");

            if (total_results > 0 ) {
                jsonArray = root_document.getJSONArray("results");

                for (int x=0; x < jsonArray.length(); x++) {
                    String author = jsonArray.getJSONObject(x).getString("author");
                    String content = jsonArray.getJSONObject(x).getString("content");

                    mMovieReview.add( new MovieReview(author, content) );
                }
            } else {
                jsonArray.put(0, root_document);
            }




        } catch (JSONException j) {
            j.printStackTrace();
        }

        return mMovieReview;
    }


} // class StringUtil
