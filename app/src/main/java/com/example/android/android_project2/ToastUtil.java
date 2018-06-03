package com.example.android.android_project2;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private String TAG = ToastUtil.class.getName();

    public static void makeMeAToast(Context context, String stuffToSay) {
        Toast.makeText( context, stuffToSay, Toast.LENGTH_LONG).show();
    }

}
