/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2.MovieData;

import android.os.Parcel;
import android.os.Parcelable;

// https://guides.codepath.com/android/using-parcelable
public class Movie implements Parcelable {


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


} // class Movie

