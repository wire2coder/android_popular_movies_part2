package com.example.android.android_project2.MovieData;

/* getting trailer thumbnail pictures from
* https://img.youtube.com/vi/<source>/mqdefault.jpg
* <source> is from
* https://api.themoviedb.org/3/movie/343611/trailers?api_key=
* */

import android.os.Parcel;
import android.os.Parcelable;

public class TrailersThumbNails implements Parcelable {

    /*
    * member variables
    * */


    private String thumbKey;
    private String thumbPath;



    /*
    * constructors
    * */


    public TrailersThumbNails(String key, String path) {
        this.thumbKey = key;
        this.thumbPath = path;

    }

    private TrailersThumbNails(Parcel in) {
        thumbPath = in.readString();
        thumbKey = in.readString();
    }



    /*
    * Setter
    * */


    public void setThumbKey(String thumbKey) {
        this.thumbKey = thumbKey;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }




    /*
    * Getter
    * */

    public String getThumbKey() {
        return this.thumbKey;
    }

    public String getThumbPath() {
        return thumbPath;
    }




    /*
    * Creating Parcelable
    */

    public static final Creator<TrailersThumbNails> CREATOR =
            new Creator<TrailersThumbNails>() {

        @Override
        public TrailersThumbNails createFromParcel(Parcel in) {
            return new TrailersThumbNails(in);
        }

        @Override
        public TrailersThumbNails[] newArray(int size) {
            return new TrailersThumbNails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbKey);
        parcel.writeString(thumbPath);
    }




} // class
