/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.util.Log;

public class LogUtil {

    private static String TAG = LogUtil.class.getSimpleName();

    public static void logStuff(String string1) {
        Log.v("ttt >>> " + TAG, string1);
    }

    public static void sayNull() {
        Log.v(TAG, "THE THING IS NULL");
    }

} // class
