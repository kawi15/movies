package kawi15.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import info.movito.themoviedbapi.model.MovieDb;
import kawi15.myapplication.database.Watchlist;

public class MovieDetails extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        textView = findViewById(R.id.t1);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("class").equals("movieDB")){
            MovieDb movieDb = (MovieDb) bundle.getSerializable("object");
            textView.setText(movieDb.getOriginalTitle());
        }
        else if(bundle.getString("class").equals("watchlist")){
            Watchlist watchlist = (Watchlist) bundle.getSerializable("object");
            textView.setText(watchlist.getMovieTitle());
        }
    }
}
