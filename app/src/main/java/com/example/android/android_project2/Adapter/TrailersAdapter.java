package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_project2.DetailActivity;
import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.RecyclerViewHolder>  {


    /*
    * Fields
    * */

    private Context mContext;
    private List<TrailersThumbNails> mTrailersThumbNails = new ArrayList<>();


    /*
    * Constructor
    * */


    public TrailersAdapter(Context context, List<TrailersThumbNails> list1 ) {
        this.mTrailersThumbNails = list1;
        this.mContext = context;
    }



    /*
    * RecyclerView Implementations
    * */



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int trailersLayout = R.layout.trailers_item_layout;

        View view = inflater.inflate(trailersLayout, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder rvh = new RecyclerViewHolder(view);

        return rvh;
    }


    // inputting data into the individual view
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv_test_test.setText("LKJDFLDJLFJ");

        Picasso.with(mContext)
                .load("https://img.youtube.com/vi/k3kzqVliF48/mqdefault.jpg")
                .into(holder.iv_trailers_item_layout);

    }


    @Override
    public int getItemCount() {
        int size = mTrailersThumbNails.size();
        return size;
    }



    /*
    * View implementation
    * */



    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        /*
        * Fields
        * */



        TextView tv_test_test;
        ImageView iv_trailers_item_layout;


        /*
        * Constructors
        * */


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_test_test = itemView.findViewById(R.id.tv_test_test);
            iv_trailers_item_layout = itemView.findViewById(R.id.iv_trailers_item_layout);


            // need to add setOnClickListener()
        }



    } // class


} // class
