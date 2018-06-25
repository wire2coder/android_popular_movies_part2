package com.example.android.android_project2.MovieData;

/* getting trailer thumbnail pictures from
* https://img.youtube.com/vi/<source>/mqdefault.jpg
* <source> is from
* https://api.themoviedb.org/3/movie/343611/trailers?api_key=
* */

import android.os.Parcel;
import android.os.Parcelable;

public class TrailersThumbNails {

    /*
    * member variables
    * */


    private String thumbKey;



    /*
    * constructors
    * */


    public TrailersThumbNails(String key) {
        this.thumbKey = key;

    }



    /*
    * Setter
    * */


    public void setThumbKey(String thumbKey) {
        this.thumbKey = thumbKey;
    }



    /*
    * Getter
    * */

    public String getThumbKey() {
        return this.thumbKey;
    }




} // class
