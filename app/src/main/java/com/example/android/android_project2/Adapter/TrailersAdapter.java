package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_project2.R;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.RecyclerViewHolder>  {


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int trailersLayout = R.layout.recyclerview_trailers;

        View view = inflater.inflate(trailersLayout, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder rvh = new RecyclerViewHolder(view);

        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_recyclerview_trailers;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            iv_recyclerview_trailers = (ImageView) itemView.findViewById(R.id.iv_recyclerview_trailers);

            // need to add setOnClickListener()
        }



    } // class

}
