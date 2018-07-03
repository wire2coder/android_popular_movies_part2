/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_project2.MovieData.Movie;
import com.example.android.android_project2.R;
import com.example.android.android_project2.Util.LogUtil;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter< MovieAdapter.RecyclerViewHolder > {


    private ArrayList<Movie> mArrayList;
    private Context mContext;
    final private ListItemClickListener mOnClickListener;
    private String picUrl = "http://image.tmdb.org/t/p/w500//";


    public interface ListItemClickListener {
        void onListItemClick(Movie clickedItemIndex);
    }


    public MovieAdapter(Context context, ListItemClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
    }


    public void swapData(ArrayList<Movie> list_in) {
        mArrayList = list_in;
        notifyDataSetChanged();
    }


    @Override
    public MovieAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.row_movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder rvh = new RecyclerViewHolder(view);
        return rvh;

    }

    @Override
    public void onBindViewHolder(MovieAdapter.RecyclerViewHolder rvh, int position) {

        rvh.tv_movie_name.setText( mArrayList.get(position).getTitle() );

        Uri builtUri = Uri.parse(picUrl).buildUpon().appendEncodedPath(
                mArrayList.get(position).getPoster_path())
                .build();

        Picasso.with(mContext)
                .load( builtUri )
                .into(rvh.iv_poster);

    }

    @Override
    public int getItemCount() {

        return mArrayList != null ? mArrayList.size() : 0;

    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_poster;
        TextView tv_movie_name;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            iv_poster = itemView.findViewById(R.id.iv_poster);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick( mArrayList.get(clickedPosition) );

        }

    } // class RecyclerViewHolder

} // class
