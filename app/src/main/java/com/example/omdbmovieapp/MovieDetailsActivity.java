package com.example.omdbmovieapp;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.omdbmovieapp.databinding.ActivityMovieDetailsBinding;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding; //viewbinding for elements
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater()); //init viewbinding
        setContentView(binding.getRoot());
        //get frm intent
        String title = getIntent().getStringExtra("title");
        String year = getIntent().getStringExtra("year");
        String rating = getIntent().getStringExtra("rating");
        String studio = getIntent().getStringExtra("studio");
        String plot = getIntent().getStringExtra("plot");
        String poster = getIntent().getStringExtra("poster");
        //display data
        binding.detailsTitle.setText(title);
        binding.detailsYear.setText("Year: " + year);
        binding.detailsRating.setText("Rating: " + rating);
        binding.detailsStudio.setText("Studio: " + studio);
        binding.detailsDescription.setText(plot != null ? plot : "N/A");
        //load poster img
        Glide.with(this).load(poster).into(binding.detailsPoster);
        //return to previous screen on back button
        binding.backButton.setOnClickListener(v -> finish());
    }
}
