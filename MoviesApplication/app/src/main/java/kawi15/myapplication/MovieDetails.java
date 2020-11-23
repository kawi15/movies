package kawi15.myapplication;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import info.movito.themoviedbapi.model.MovieDb;
import kawi15.myapplication.database.Watchlist;

public class MovieDetails extends AppCompatActivity {

    TextView textView;
    TextView title;
    TextView releaseDate;
    TextView overview;
    ImageView imageView;
    Button addToWatchlist;
    Button addToWatched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        textView = findViewById(R.id.t1);
        title = findViewById(R.id.first_text1);
        releaseDate = findViewById(R.id.first_text2);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);
        addToWatched = findViewById(R.id.add_watched);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("class").equals("movieDB")){
            MovieDb movieDb = (MovieDb) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + movieDb.getPosterPath()).into(imageView);
            title.setText(movieDb.getTitle());
            releaseDate.setText(movieDb.getReleaseDate());
            overview.setText(movieDb.getOverview());
            addToWatchlist.setText("add to watchlist");
            addToWatched.setText("add to watched");
        }
        else if(bundle.getString("class").equals("watchlist")){
            Watchlist watchlist = (Watchlist) bundle.getSerializable("object");
            textView.setText(watchlist.getMovieTitle());
        }
    }
}
