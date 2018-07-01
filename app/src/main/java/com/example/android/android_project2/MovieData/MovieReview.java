package com.example.android.android_project2.MovieData;


public class MovieReview {


    /*
    * Fields
    * */

    private String movie_review_author;
    private String movie_review_content;


    /*
    * Constructors
    * */

    public MovieReview(String author, String content) {
        this.movie_review_author = author;
        this.movie_review_content = content;
    }


    /*
    * Getter
    * */

    public String get_author() {
        return movie_review_author;
    }

    public String get_content() {
        return movie_review_content;
    }

}
