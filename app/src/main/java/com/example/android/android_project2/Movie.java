/*
 * Terry S Android Nano Degree project 2
 */

package com.example.android.android_project2;

public class Movie {

/* delcare variables */
    private int vote_count, id, vote_average;
    private double popularity;
    private Boolean video, adult;
    private String title, poster_path, original_language, original_title, backdrop_path, overview, release_date;
    private static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";


    /**
     * No args constructor for use in serialization.
     * I don't really understand what it means
     */
    public Movie() {}

    public Movie(int vote_count, int id, int vote_average,
                 double popularity, Boolean video, Boolean adult,
                 String title, String poster_path, String original_language,
                 String original_title, String backdrop_path, String overview, String release_date) {

        this.vote_count = vote_count;
        this.id = id;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.video = video;
        this.adult = adult;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return BASE_POSTER_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
} // class Movie

