package com.example.omdbmovieapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.omdbmovieapp.model.MovieItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<MovieItem>> movieList = new MutableLiveData<>();
    private final String API_KEY = "62742059";

    //return movie list as livedata so app can grab
    public LiveData<ArrayList<MovieItem>> getMovies() {
        return movieList;
    }
    //async search (used in main on search)
    public void fetchMovies(String query) {
        new Thread(() -> {
            ArrayList<MovieItem> tempList = new ArrayList<>();
            try {
                //format query
                String searchUrl = "https://www.omdbapi.com/?apikey=" + API_KEY + "&s=" + query.replace(" ", "%20");
                HttpURLConnection conn = (HttpURLConnection) new URL(searchUrl).openConnection();
                conn.setRequestMethod("GET");
                //read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                reader.close();
                //parse to get imdb list
                JSONObject response = new JSONObject(json.toString());
                if (response.has("Search")) {
                    JSONArray results = response.getJSONArray("Search");
                    // for every result, get deets
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject basic = results.getJSONObject(i);
                        String imdbID = basic.optString("imdbID");
                        //get full movie details
                        String detailUrl = "https://www.omdbapi.com/?apikey=" + API_KEY + "&i=" + imdbID + "&plot=short";
                        HttpURLConnection detailConn = (HttpURLConnection) new URL(detailUrl).openConnection();
                        detailConn.setRequestMethod("GET");
                        //read it
                        BufferedReader detailReader = new BufferedReader(new InputStreamReader(detailConn.getInputStream()));
                        StringBuilder detailJson = new StringBuilder();
                        while ((line = detailReader.readLine()) != null) {
                            detailJson.append(line);
                        }
                        detailReader.close();

                        JSONObject movieObj = new JSONObject(detailJson.toString());
                        //create movie object
                        MovieItem movie = new MovieItem(
                                movieObj.optString("Title", "N/A"),
                                movieObj.optString("Year", "N/A"),
                                movieObj.optString("Poster", ""),
                                imdbID,
                                movieObj.optString("Rated", "N/A"),
                                movieObj.optString("Production", "N/A"),
                                movieObj.optString("Plot", "N/A")
                        );
                        tempList.add(movie);
                    }
                }
                //post list as livedata
                movieList.postValue(tempList);

            } catch (Exception e) {
                e.printStackTrace();
                movieList.postValue(null);//self destruct lol
            }
        }).start();
    }
}
