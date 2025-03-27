package com.example.omdbmovieapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.omdbmovieapp.model.MovieItem;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {
    private EditText searchField;
    private Button searchButton;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //grab comps
        searchField = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);
        //connect recyclerview to linearlayout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //create adapter
        adapter = new MovieAdapter(new ArrayList<>(), this);
        //connect recyclerview to adapter
        recyclerView.setAdapter(adapter);
        //create viewmodel
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        //grab livedata from viewmodel
        viewModel.getMovies().observe(this, movies -> {
            if (movies != null) {
                adapter.updateData(movies);
            } else {
                Toast.makeText(MainActivity.this, "couldn't load movies", Toast.LENGTH_SHORT).show();
            }
        });
        //search button
        searchButton.setOnClickListener(v -> {
            String query = searchField.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.fetchMovies(query);
            } else {
                Toast.makeText(MainActivity.this, "search is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //open movie
    @Override
    public void onMovieClick(MovieItem movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("year", movie.getYear());
        intent.putExtra("poster", movie.getPoster());
        intent.putExtra("rating", movie.getRated());
        intent.putExtra("studio", movie.getStudio());
        intent.putExtra("plot", movie.getPlot());
        startActivity(intent);
    }
}
