package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.android_project2.Database.MovieDatabaseContract;
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

        /* Move the mCursor to the position of the item to be displayed */
        if ( !mCursor.moveToPosition(position) ) { // >> position of the current 'view row'
            return; // exit if the current does not exist
        }

        /* get movie title and movie id value from the database */
        int movie_id = mCursor.getInt(mCursor.getColumnIndex(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_ID));
        String movie_title = mCursor.getString(mCursor.getColumnIndex(MovieDatabaseContract.MovieDatabaseTable.COLUMN_MOVIE_TITLE));

        holder.tv_favorite_mov_id.setText(String.valueOf(movie_id));
        holder.tv_favorite_mov_title.setText(movie_title);
    }

    /* how many 'item' do you want inside your 'recyclable' */
    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    /** Swaps the Cursor in this 'adapter' with a NEW one, you do this so you can
     * call 'this.notifyDataSetChanged()' to UPDATE the user face
     * @param newCursor the new cursor that will replace the existing cursor */
    public void swapCursor(Cursor newCursor) {
        /* always close the previous mCursor first */
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }

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

        /* use the 'stuff' from favorite_movie_item.xml */
        tv_favorite_mov_id = itemView.findViewById(R.id.tv_favorite_mov_id);
        tv_favorite_mov_title = itemView.findViewById(R.id.tv_favorite_mov_title);

    }

    } // class FavoriteMovieViewHolder

} // class FavoriteAdapter
