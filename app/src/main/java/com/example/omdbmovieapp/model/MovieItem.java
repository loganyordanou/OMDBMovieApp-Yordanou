package com.example.omdbmovieapp.model;
public class MovieItem {
    private String title;
    private String year;
    private String poster;
    private String imdbID;
    private String rated;
    private String studio;
    private String plot;
    //1 movie
    public MovieItem(String title, String year, String poster, String imdbID, String rated, String studio, String plot) {
        this.title = title;
        this.year = year;
        this.poster = poster;
        this.imdbID = imdbID;
        this.rated = rated;
        this.studio = studio;
        this.plot = plot;
    }
    //get
    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getPoster() { return poster; }
    public String getImdbID() { return imdbID; }
    public String getRated() { return rated; }
    public String getStudio() { return studio; }
    public String getPlot() { return plot; }
}