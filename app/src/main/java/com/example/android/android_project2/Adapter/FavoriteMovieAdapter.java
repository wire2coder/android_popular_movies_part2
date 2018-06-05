package com.example.android.android_project2.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_project2.R;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    /* 'member variables */
    private Context mContext;
    private Cursor mCursor;

    /** constructor
     * @param context the calling 'activity'
     * @param cursor the database 'cursor' with data to display
     * */
    public FavoriteMovieAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public FavoriteMovieAdapter(Context context) {
        mContext = context;
    }

    /* what to do before you create each 'item' */
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /* 1. get an 'inflater' */
        /* 2. inflate the XML */
        /* 3. make a new() 'view holder' from the class you created below */

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View layout_view = layoutInflater.inflate(R.layout.favorite_movie_item, parent, false);

        FavoriteMovieViewHolder fvh = new FavoriteMovieViewHolder(layout_view);
        return fvh;
    }

    /* what to do when the 'adapter' combines data and the 'view' together */
    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {
        holder.tv_favorite_mov_id.setText("id");
        holder.tv_favorite_mov_title.setText("title");
    }

    /* how many 'item' do you want inside your 'recyclable' */
    @Override
    public int getItemCount() {
        return 3;
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {

        TextView tv_favorite_mov_id;
        TextView tv_favorite_mov_title;

    /**
     * Constructor for our ViewHolder, we get a reference to our TextView
     * @param itemView The View that you inflated in
     *  {@link FavoriteMovieAdapter#onCreateViewHolder(ViewGroup, int)}
     *  */
    public FavoriteMovieViewHolder(View itemView) {
        super(itemView);

        tv_favorite_mov_id = itemView.findViewById(R.id.tv_favorite_mov_id);
        tv_favorite_mov_title = itemView.findViewById(R.id.tv_favorite_mov_title);

    }

    } // class FavoriteMovieViewHolder

} // class FavoriteAdapter
