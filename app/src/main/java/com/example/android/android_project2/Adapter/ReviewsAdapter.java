/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.MovieData.MovieReview;
import com.example.android.android_project2.R;
import com.example.android.android_project2.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter< ReviewsAdapter.RecyclerViewHolder > {


    /*
    * fields
    * */

    private Context mContext;
    private List<MovieReview> mReviewsList;


    /*
    * constructors
    * */

    public ReviewsAdapter(Context context, List<MovieReview> arrayList_in ) {
        this.mReviewsList = arrayList_in;
        this.mContext = context;
    }

    public void swapData( List<MovieReview> new_data ) {
        mReviewsList = new_data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        int layout_id = R.layout.row_review_item;

        View view = inflater.inflate(layout_id, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder rvh = new RecyclerViewHolder(view);

        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        MovieReview review_object = mReviewsList.get(position);

        holder.tv_author.setText( review_object.get_author() );
        holder.tv_review.setText( review_object.get_content() );

    }


    @Override
    public int getItemCount() {
        if (mReviewsList.size() == 0) {
//            LogUtil.logStuff("REVIEWS is " + mReviewsList.size());
        }

        return mReviewsList.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        /*
        * fields
        * */

        TextView tv_review;
        TextView tv_author;

        /*
        * constructors
        * */

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_review = itemView.findViewById(R.id.tv_review);
            tv_author = itemView.findViewById(R.id.tv_author);
        }


    } // class RecyclerViewHolder

} // class ReviewsAdapter
