package com.example.android.android_project2.Util;


import com.example.android.android_project2.MovieData.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface JSONInterface {

    ArrayList<Movie> JSON_to_ArrayList(JSONObject json_object_in) throws JSONException;

} // interface JSONInterface
