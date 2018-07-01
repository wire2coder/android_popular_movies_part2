package com.example.android.android_project2.MovieData;

/* getting trailer thumbnail pictures from
* https://img.youtube.com/vi/<source>/mqdefault.jpg
* <source> is from
* https://api.themoviedb.org/3/movie/343611/trailers?api_key=
* */


public class TrailersThumbNails {

    private String thumbKey;


    public TrailersThumbNails(String source) {
        this.thumbKey = source;
    }


    public void setThumbKey(String thumbKey) {
        this.thumbKey = thumbKey;
    }

    public String getThumbKey() {
        return this.thumbKey;
    }


} // class
