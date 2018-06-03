/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends BaseAdapter {

    // SOURCE: https://www.raywenderlich.com/127544/android-gridview-getting-started

    /* declaring member variables */
    private Context mContext;
    private List<Movie> mMovies = new ArrayList<>();


    /* Constructor */
    public MovieAdapter() {
    }

    /* Constructor */
    public MovieAdapter(Context context, List<Movie> movies) {

        this.mContext = context;
        this.mMovies = movies;
    }

    // you return the number of cells to render here
    @Override
    public int getCount() {

        if ( mMovies.size() != 0 ) {
            int size = this.mMovies.size();
//            LogUtil.logStuff( "getCount() : " + String.valueOf(size) );
            return size; // >> 20
        } else {
            return 0;
        }
    }

    // You don’t need to return an id for this tutorial, so just return 0.
    // Android still requires you to provide an implementation for this method.
    @Override
    public Object getItem(int i) {
        return null;
    }

    // See #3, but instead return null.
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /*
     * the INDIVIDUAL VIEW inside the Grid View
     *  Now that you have a basic adapter implementation,
        you can use this class as the data provider for the GridView in MainActivity.
        Within onCreate() in MainActivity.java, underneath
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*  make a TextView inside BooksAdapter */
//        TextView dummyTextView = new TextView(mContext);
//        dummyTextView.setText( String.valueOf(position) );

        Movie movie = mMovies.get(position);

        /* GridView optimizes memory usage by recycling the cells. This means that
            if convertView is null, you instantiate a new cell view by using a
            LayoutInflater and inflating your linearlayout_book layout.
         */

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.movie_adapter_layout, null);
        }

        ImageView iv_poster = (ImageView) convertView.findViewById(R.id.iv_poster);

        Picasso.with(mContext)
                .load(movie.getPoster_path())
                .into(iv_poster);

        return convertView;
    }

    /* helper: Sunshine 04.03, something about replacing old
        data with new data fetched from servers
    */
    public void setMovies(List<Movie> movies) {
        if ( movies != null ) {
            mMovies.clear();
            mMovies.addAll(movies);

//            mMovies = movies; << this will give onClickListener ERROR!
            notifyDataSetChanged();
        }
    }

} // class
