/*
 * Terry S Android Nano Degree project 3
 */

package com.example.android.android_project2.MovieData;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.android.android_project2.Util.JSONInterface;
import com.example.android.android_project2.Util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// https://guides.codepath.com/android/using-parcelable
public class Movie implements Parcelable, JSONInterface {


    /*
    * fields
    * */

    //    private static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";

    private int id;
    private int vote_average;

    private String title;
    private String poster_path;
    private String original_title;
    private String backdrop_path;
    private String overview;
    private String release_date;


    public Movie() {
        // Normal actions performed by class, since this is still a normal object!
    }


    public Movie(int id, int vote_average, String title, String poster_path, String original_title, String backdrop_path, String overview, String release_date) {

        this.id = id;
        this.vote_average = vote_average;

        this.title = title;
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;

    }


    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Movie(Parcel in) {

        id = in.readInt();
        vote_average = in.readInt();

        title = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();

    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeInt(vote_average);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(release_date);

    }


    @Override
    public int describeContents() {
        return 0;
    }


    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {

            return new Movie(in);

        }

        @Override
        public Movie[] newArray(int size) {

            return new Movie[size];

        }
    };


    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public int getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }


    @Override
    public ArrayList<Movie> JSON_to_ArrayList(JSONObject json_object_in) throws JSONException {

        ArrayList<Movie> mmMovieList = new ArrayList<>();
        JSONArray jsonArray1 = new JSONArray();

         int id;
         int vote_average;

         String title;
         String poster_path;
         String original_title;
         String backdrop_path;
         String overview;
         String release_date;


        if (json_object_in.has("results")) {
            jsonArray1 = json_object_in.getJSONArray("results");
        } else {
            jsonArray1.put(0, json_object_in);
        }


         for ( int i=0; i < jsonArray1.length(); i++ ) {

             JSONObject root = jsonArray1.getJSONObject(i);

             id = root.getInt("id");
             vote_average = root.getInt("vote_average");

             title = root.optString("title");
             poster_path = root.optString("poster_path");
             original_title = root.optString("original_title");
             backdrop_path = root.optString("backdrop_path");
             overview = root.optString("overview");
             release_date = root.optString("release_date");

             mmMovieList.add( new Movie( id,
                     vote_average, title, poster_path, original_title,
                     backdrop_path, overview, release_date) );

         } // for

//        LogUtil.logStuff( "mmMovieList size: " + String.valueOf( mmMovieList.size() ) );
        return mmMovieList;

    } // JSON_to_ArrayList()

} // class Movie

