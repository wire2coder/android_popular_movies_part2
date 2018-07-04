package com.example.android.android_project2.Util;

import com.example.android.android_project2.MovieData.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtil {

    public static ArrayList<Movie> get_json_object(JSONInterface object_in, String string_in) throws JSONException {

        JSONObject jobject1 = new JSONObject(string_in);

        return object_in.JSON_to_ArrayList(jobject1);
    }

} // class JSONUtil
