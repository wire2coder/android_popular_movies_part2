package com.example.android.android_project2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_project2.MovieData.TrailersThumbNails;
import com.example.android.android_project2.R;
import com.example.android.android_project2.Util.LogUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.RecyclerViewHolder>  {


    /*
    * Fields
    * */

    private Context mContext;
    private List<TrailersThumbNails> mTrailersThumbNails;
    final private ListItemClickListener mListItemClickListener;


    public interface ListItemClickListener {
        void onListItemClick(String clickedTrailerId);
    }

    /*
    * Constructor
    * */


    public TrailersAdapter(Context context, List<TrailersThumbNails> list1, ListItemClickListener listener ) {
        this.mTrailersThumbNails = list1;
        this.mContext = context;
        this.mListItemClickListener = listener;
    }



    /*
    * Notification to UI that Data had changed
    * */


    public void swapData(List<TrailersThumbNails> new_data) {

        mTrailersThumbNails = new_data; // receive new data
        notifyDataSetChanged(); // tell UI to update itself
    }




    /*
    * RecyclerView Implementations
    * */



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        int layout_id = R.layout.row_trailer_item;

        View view = inflater.inflate(layout_id, viewGroup, shouldAttachToParentImmediately);

        RecyclerViewHolder rvh = new RecyclerViewHolder(view);

        return rvh;
    }



    // inputting data into the individual view
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        TrailersThumbNails thumb_object = mTrailersThumbNails.get(position);

        String trailerId = thumb_object.getThumbKey();

        LogUtil.logStuff(trailerId);

        // setting the source/ID for Youtube trailer
        holder.iv_trailers.setTag("fUjicxMPDzs");

        Picasso.with(mContext)
                .load("https://img.youtube.com/vi/"
                        + thumb_object.getThumbKey() + "/mqdefault.jpg")
                .into(holder.iv_trailers);

    }



    @Override
    public int getItemCount() {
        int size = mTrailersThumbNails.size();
        return size;
    }



    /*
    * View implementation
    * */


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        /*
        * Fields
        * */


        ImageView iv_trailers;


        /*
        * Constructors
        * */


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            iv_trailers = itemView.findViewById(R.id.iv_trailers);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListItemClickListener.onListItemClick( (String) view.getTag() );
        }

    } // class

} // class
