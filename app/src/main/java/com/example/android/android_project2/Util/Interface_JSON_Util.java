package com.example.android.android_project2.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface Interface_JSON_Util {

    ArrayList<?> convertJSONtoObject(JSONObject object_in) throws JSONException;

}
