package com.example.android.android_project2.MovieData;

import com.example.android.android_project2.Util.Interface_JSON_Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class MovieReview implements Interface_JSON_Util {


    /*
    * Fields
    * */

    private String movie_review_author;
    private String movie_review_content;


    /*
    * Constructors
    * */

    public MovieReview(String author, String content) {
        this.movie_review_author = author;
        this.movie_review_content = content;
    }



    /*
    * Interface_JSON_Util
    * */

    @Override
    public ArrayList<?> convertJSONtoObject(JSONObject object_in) throws JSONException {

        ArrayList<MovieReview> movie_reivew_array_list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();

        if ( object_in.has("results") ) {
            jsonArray = object_in.getJSONArray("results");
        }

        for (int p=0; p < jsonArray.length(); p++) {

            JSONObject json_ojb = jsonArray.getJSONObject(p);
            String author= json_ojb.getString("author");
            String content= json_ojb.getString("content");

            MovieReview movieReview = new MovieReview(author, content);
            movie_reivew_array_list.add( movieReview );
        }

        return movie_reivew_array_list;
    }
}
