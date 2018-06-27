package com.example.android.android_project2.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_project2.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter< ReviewsAdapter.RecyclerViewHolder > {


    /*
    * fields
    * */

    ArrayList<?> mArrayList;


    /*
    * constructors
    * */

    public ReviewsAdapter(ArrayList<?> arrayList_in ) {
        this.mArrayList = arrayList_in;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        /*
        * fields
        * */

        TextView tv_review;



        /*
        * constructors
        * */

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            TextView tv_review = itemView.findViewById(R.id.tv_review);
        }


    } // class RecyclerViewHolder

} // class ReviewsAdapter
