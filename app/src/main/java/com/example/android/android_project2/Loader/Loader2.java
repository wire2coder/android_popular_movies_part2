package com.example.android.android_project2.Loader;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

import com.example.android.android_project2.Adapter.Interface_Adapter;
import com.example.android.android_project2.Util.Interface_JSON_Util;

import java.util.ArrayList;

public class Loader2 {

    /*
    * Fields
    * */

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context mContext;


    /*
    * Constructors
    * */

    public Loader2(RecyclerView recyclerView_in, Context context_in) {
        this.mRecyclerView = recyclerView_in;
        this.mContext = context_in;
    }


    class inner_Loader implements LoaderManager.LoaderCallbacks< ArrayList<?> > {


        @Override
        public Loader< ArrayList<?> > onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<ArrayList<?>>(mContext) {

                @Override
                protected void onStartLoading() {
                    if (args == null) {
                        return;
                    }

                    forceLoad(); // run loadInBackground() below
                }

                @Override
                public ArrayList<?> loadInBackground() {
                    return null;
                }

            };
        }

        @Override
        public void onLoadFinished( Loader< ArrayList<?> > loader, ArrayList<?> data) {

        }

        @Override
        public void onLoaderReset( Loader< ArrayList<?> > loader) {

        }
    } // class inner_Loader


} // class
