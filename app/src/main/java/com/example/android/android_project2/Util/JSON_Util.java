package com.example.android.android_project2.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



/*
* i still don't really understand what's going in here
* */
public class JSON_Util {

    public ArrayList<?> getJson(Interface_JSON_Util interface_in, String string_in) throws JSONException {

        JSONObject jsonObject = new JSONObject(string_in);

        return interface_in.convertJSONtoObject(jsonObject);
    }

}
