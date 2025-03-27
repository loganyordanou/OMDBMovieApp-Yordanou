package com.example.omdbmovieapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.omdbmovieapp.model.MovieItem;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieItem> movies; //movies to display
    private OnMovieClickListener listener;
    //constructor gets movie list and click handler
    public MovieAdapter(List<MovieItem> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }
    //click handler for main activity
    public interface OnMovieClickListener {
        void onMovieClick(MovieItem movie);
    }
    //update movie list on livedata change
    public void updateData(List<MovieItem> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged(); //refresh
    }
    //make new row view
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate xml layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    //bind data to each row
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }
    //total rows
    @Override
    public int getItemCount() {
        return movies.size();
    }
    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText, yearText, ratingText, studioText;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            //grab text fields from xml
            titleText = itemView.findViewById(R.id.movieTitle);
            yearText = itemView.findViewById(R.id.movieYear);
            ratingText = itemView.findViewById(R.id.movieRating);
            studioText = itemView.findViewById(R.id.movieStudio);
        }
        //replace placeholders with acc data
        public void bind(MovieItem movie) {
            titleText.setText(movie.getTitle());
            yearText.setText("Year: " + movie.getYear());
            ratingText.setText("Rating: " + movie.getRated());
            studioText.setText("Studio: " + movie.getStudio());
            //handle click on individual movie
            itemView.setOnClickListener(v -> listener.onMovieClick(movie));
        }
    }
}
