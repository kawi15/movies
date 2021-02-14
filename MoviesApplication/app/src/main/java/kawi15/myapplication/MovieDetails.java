package kawi15.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import kawi15.myapplication.database.DatabaseViewModel;
import kawi15.myapplication.database.Recommendation;
import kawi15.myapplication.database.Removed;
import kawi15.myapplication.database.Watched;
import kawi15.myapplication.database.Watchlist;

public class MovieDetails extends AppCompatActivity {

    private DatabaseViewModel databaseViewModel;
    private Watchlist watchlist;
    private Watched watched;
    private MovieDb movieDb;
    private Recommendation recommendation;
    private String bool;
    private List<Removed> removed;
    private int movieId;
    String ratingText;
    TextView rating;
    TextView title;
    TextView releaseDate;
    TextView overview;
    TextView votes;
    TextView popularity;
    ImageView imageView;
    Button addToWatchlist;
    Button addToWatched;

    public class RecomendationTask extends AsyncTask<Void, Void, List<MovieDb>> {

        @Override
        protected List<MovieDb> doInBackground(Void... voids) {
            MovieResultsPage movies = new TmdbApi("f753872c7aa5c000e0f46a4ea6fc49b2")
                    .getMovies().getRecommendedMovies(movieId, "en-US", 1);
            List<MovieDb> listMovies = movies.getResults();

            return listMovies;
        }

        @Override
        protected void onPostExecute(List<MovieDb> recomendations) {
            for(MovieDb item : recomendations){
                int toCheck = item.getId();
                if(databaseViewModel.getRemoveFromRecommendations(toCheck) == null){
                    databaseViewModel.addRecommendationMovie(item);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        releaseDate = findViewById(R.id.t1);
        rating = findViewById(R.id.ttt3);
        title = findViewById(R.id.title);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        overview.setScrollbarFadingEnabled(false);
        votes = findViewById(R.id.ttt4);
        popularity = findViewById(R.id.tt1);
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);
        addToWatched = findViewById(R.id.add_watched);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("class").equals("movieDB")){
            bool = "movieDB";
            movieDb = (MovieDb) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + movieDb.getPosterPath()).into(imageView);
            title.setText(movieDb.getTitle());
            releaseDate.setText(movieDb.getReleaseDate());


            if(movieDb.getVoteAverage() == 0.0){
                ratingText = "not rated";
            }
            else ratingText = String.valueOf(movieDb.getVoteAverage()) + " / 10";

            rating.setText(ratingText);
            overview.setText(movieDb.getOverview());
            votes.setText(String.valueOf(movieDb.getVoteCount()));
            popularity.setText(String.valueOf(movieDb.getPopularity()));

            addToWatchlist.setText("add to watchlist");
            addToWatched.setText("add to watched");
        }
        else if(bundle.getString("class").equals("watchlist")){
            bool = "watchlist";
            watchlist = (Watchlist) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + watchlist.getPosterPath()).into(imageView);
            title.setText(watchlist.getMovieTitle());
            releaseDate.setText(watchlist.getReleaseDate());
            overview.setText(watchlist.getOverview());

            if(watchlist.getRating() == 0.0){
                ratingText = "not rated";
            }
            else ratingText = String.valueOf(watchlist.getRating() + " / 10");

            rating.setText(ratingText);
            votes.setText(String.valueOf(watchlist.getVotes()));
            popularity.setText(String.valueOf(watchlist.getPopularity()));

            addToWatchlist.setText("remove from watchlist");
            addToWatched.setText("add to watched");

        }
        else if(bundle.getString("class").equals("watched")){
            bool = "watched";
            watched = (Watched) bundle.getSerializable("object");
            Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + watched.getPosterPath()).into(imageView);
            title.setText(watched.getMovieTitle());
            releaseDate.setText(watched.getReleaseDate());
            overview.setText(watched.getOverview());

            if(watched.getRating() == 0.0){
                ratingText = "not rated";
            }
            else ratingText = String.valueOf(watched.getRating() + " / 10");

            rating.setText(ratingText);
            votes.setText(String.valueOf(watched.getVotes()));
            popularity.setText(String.valueOf(watched.getPopularity()));

            addToWatched.setText("remove from watched");
            addToWatchlist.setText("add to watchlist");
        }
    }

    public void addToWatchlist(View view) {

        if(bool.equals("movieDB")){
            databaseViewModel.addWatchlistMovie(movieDb);
            movieId = movieDb.getId();

            RecomendationTask rt = new RecomendationTask();
            rt.execute();

            addToWatchlist.setText("Added!");
            addToWatchlist.setClickable(false);
        }
        else if (bool.equals("watchlist")){
            databaseViewModel.deleteWatchlistMovie(watchlist);
            addToWatchlist.setText("Removed!");
            addToWatchlist.setClickable(false);
        }
        else if (bool.equals("watched")){
            databaseViewModel.addWatchlistMovie(watched);
            databaseViewModel.deleteWatchedMovie(watched);
            addToWatchlist.setText("Added!");
            addToWatchlist.setClickable(false);
            addToWatched.setText("Removed!");
            addToWatched.setClickable(false);
        }
    }

    public void addToWatched(View view) {

        if(bool.equals("movieDB")){
            databaseViewModel.addWatchedMovie(movieDb);
            addToWatched.setText("Added!");
            addToWatched.setClickable(false);

            movieId = movieDb.getId();

            RecomendationTask rt = new RecomendationTask();
            rt.execute();
        }
        else if (bool.equals("watchlist")){
            databaseViewModel.addWatchedMovie(watchlist);
            databaseViewModel.deleteWatchlistMovie(watchlist);
            addToWatchlist.setText("Removed!");
            addToWatchlist.setClickable(false);
            addToWatched.setText("Added!");
            addToWatched.setClickable(false);
        }
        else if(bool.equals("watched")){
            databaseViewModel.deleteWatchedMovie(watched);
            addToWatched.setText("Removed!");
            addToWatched.setClickable(false);
        }
    }
}
