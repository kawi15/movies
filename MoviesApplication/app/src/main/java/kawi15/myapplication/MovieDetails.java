package kawi15.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import kawi15.myapplication.database.DatabaseViewModel;
import kawi15.myapplication.database.Recomendation;
import kawi15.myapplication.database.Watched;
import kawi15.myapplication.database.Watchlist;

public class MovieDetails extends AppCompatActivity {

    private DatabaseViewModel databaseViewModel;
    private Watchlist watchlist;
    private Watched watched;
    private MovieDb movieDb;
    private Recomendation recomendation;
    private String bool;
    private int movieId;
    TextView textView;
    TextView title;
    TextView releaseDate;
    TextView overview;
    ImageView imageView;
    Button addToWatchlist;
    Button addToWatched;

    public class RecomendationTask extends AsyncTask<Void, Void, List<MovieDb>> {

        @Override
        protected List<MovieDb> doInBackground(Void... voids) {
            MovieResultsPage movies = new TmdbApi("f753872c7aa5c000e0f46a4ea6fc49b2").getMovies().getRecommendedMovies(movieId, "en-US", 1);
            List<MovieDb> listMovies = movies.getResults();

            return listMovies;
        }

        @Override
        protected void onPostExecute(List<MovieDb> recomendations) {
            for(MovieDb item : recomendations){
                databaseViewModel.addRecomendationMovie(item);
            }
            //Toast toast = Toast.makeText(getApplicationContext(), "dodano", Toast.LENGTH_SHORT);
            //toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        textView = findViewById(R.id.t1);
        title = findViewById(R.id.title);
        //releaseDate = findViewById(R.id.first_text2);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);
        addToWatched = findViewById(R.id.add_watched);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("class").equals("movieDB")){
            bool = "movieDB";
            movieDb = (MovieDb) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + movieDb.getPosterPath()).into(imageView);
            title.setText(movieDb.getTitle());
            //releaseDate.setText(movieDb.getReleaseDate());
            overview.setText(movieDb.getOverview());
            addToWatchlist.setText("add to watchlist");
            addToWatched.setText("add to watched");
        }
        else if(bundle.getString("class").equals("watchlist")){
            bool = "watchlist";
            watchlist = (Watchlist) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + watchlist.getPosterPath()).into(imageView);
            title.setText(watchlist.getMovieTitle());
            overview.setText(watchlist.getOverview());
            addToWatchlist.setText("remove from watchlist");
            addToWatched.setText("add to watched");

        }
        else if(bundle.getString("class").equals("watched")){
            bool = "watched";
            watched = (Watched) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + watched.getPosterPath()).into(imageView);
            title.setText(watched.getMovieTitle());
            overview.setText(watched.getOverview());
            addToWatched.setText("already in watched");
            addToWatched.setClickable(false);
            addToWatchlist.setText("remove from watched");
        }
        else if(bundle.getString("class").equals("recomendation")){
            bool = "recomendations";
            recomendation = (Recomendation) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + recomendation.getPosterPath()).into(imageView);
            title.setText(recomendation.getMovieTitle());
            overview.setText(recomendation.getOverview());
            addToWatched.setText(" ");
            addToWatched.setClickable(false);
            addToWatchlist.setText("remove from recomendations");
        }
    }

    public void addToWatchlist(View view) {
        if(addToWatchlist.getText().equals("add to watchlist")){
            if(bool.equals("movieDB")){
                databaseViewModel.addWatchlistMovie(movieDb);
                movieId = movieDb.getId();
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(movieId), Toast.LENGTH_SHORT);
                toast.show();

                RecomendationTask rt = new RecomendationTask();
                rt.execute();
            }
        }
        else if(addToWatchlist.getText().equals("remove from watchlist")){
            if (bool.equals("watchlist")){
                databaseViewModel.deleteWatchlistMovie(watchlist);
            }

        }
        else if(addToWatchlist.getText().equals("remove from watched")){
            databaseViewModel.deleteWatchedMovie(watched);
            addToWatchlist.setText("removed from watched");
            addToWatchlist.setClickable(false);
        }
        else if(addToWatchlist.getText().equals("remove from recomendations")){
            databaseViewModel.deleteRecomendationMovie(recomendation);
        }
    }

    public void addToWatched(View view) {
        if (addToWatched.getText().equals("add to watched")){
            if(bool.equals("movieDB")){
                databaseViewModel.addWatchedMovie(movieDb);
            }
            if (bool.equals("watchlist")){
                databaseViewModel.addWatchedMovie(watchlist);
                databaseViewModel.deleteWatchlistMovie(watchlist);
                addToWatchlist.setText("removed from watchlist");
                addToWatchlist.setClickable(false);
                addToWatched.setText("added to watched");
                addToWatched.setClickable(false);
            }
        }
        else if(addToWatched.getText().equals("remove from watched")){
            databaseViewModel.deleteWatchedMovie(watched);
            addToWatched.setText("removed");
            addToWatched.setClickable(false);
        }
    }
}
