/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /*  class member variables */
    static private List<Movie> mMovieList = new ArrayList<Movie>();


    static public List<Movie> stringToJson(String inputString1) {

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
                int vote_count, id, vote_average;
                double popularity;
                Boolean video, adult;
                String title, poster_path, original_language, original_title, backdrop_path, overview, release_date;

                vote_count = jsonArray.getJSONObject(i).getInt("vote_count");
                id = jsonArray.getJSONObject(i).getInt("id");
                vote_average = jsonArray.getJSONObject(i).getInt("vote_average");

                popularity = jsonArray.getJSONObject(i).getDouble("popularity");

                video = jsonArray.getJSONObject(i).getBoolean("video");
                adult = jsonArray.getJSONObject(i).getBoolean("adult");

                title = jsonArray.getJSONObject(i).optString("title");
                poster_path = jsonArray.getJSONObject(i).optString("poster_path");
                original_language = jsonArray.getJSONObject(i).optString("original_language");
                original_title = jsonArray.getJSONObject(i).optString("original_title");
                backdrop_path = jsonArray.getJSONObject(i).optString("backdrop_path");
                overview = jsonArray.getJSONObject(i).optString("overview");
                release_date = jsonArray.getJSONObject(i).optString("release_date");

                Movie movie = new Movie(vote_count, id, vote_average, popularity, video, adult,
                        title, poster_path, original_language, original_title, backdrop_path,
                        overview, release_date);

                mMovieList.add(movie);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return mMovieList;

    } // stringToJson

} // class StringUtil
