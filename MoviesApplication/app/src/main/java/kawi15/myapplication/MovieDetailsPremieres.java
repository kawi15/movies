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
import kawi15.myapplication.database.Removed;

public class MovieDetailsPremieres extends AppCompatActivity {

    private DatabaseViewModel databaseViewModel;
    private MovieDb movieDb;
    private int movieId;
    private List<Removed> removed;
    TextView title;
    TextView releaseDate;
    TextView popularity;
    TextView votes;
    TextView rating;
    TextView overview;
    ImageView imageView;
    String ratingText;
    Button addToWatchlist;



    public class RecomendationTask extends AsyncTask<Void, Void, List<MovieDb>> {

        @Override
        protected List<MovieDb> doInBackground(Void... voids) {
            MovieResultsPage movies = new TmdbApi("f753872c7aa5c000e0f46a4ea6fc49b2").getMovies().getRecommendedMovies(movieId, "en-US", 1);
            List<MovieDb> listMovies = movies.getResults();

            return listMovies;
        }

        @Override
        protected void onPostExecute(List<MovieDb> recomendations) {
            /*removed = databaseViewModel.getRemovedFromRecomendations();
            for(MovieDb item : recomendations){
                if(removed == null){
                    databaseViewModel.addRecomendationMovie(item);
                }
                else {
                    for (Removed movieRemoved : removed) {
                        if (item.getId() != movieRemoved.getMovieId()) {
                            databaseViewModel.addRecomendationMovie(item);
                        }
                    }
                }
            }*/

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
        setContentView(R.layout.movie_details_premieres);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        title = findViewById(R.id.title);
        releaseDate = findViewById(R.id.t1);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        overview.setScrollbarFadingEnabled(false);
        popularity = findViewById(R.id.tt1);
        rating = findViewById(R.id.ttt3);
        votes = findViewById(R.id.ttt4);
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);


        Bundle bundle = getIntent().getExtras();

        movieDb = (MovieDb) bundle.getSerializable("object");
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + movieDb.getPosterPath()).into(imageView);
        title.setText(movieDb.getOriginalTitle());
        overview.setText(movieDb.getOverview());
        releaseDate.setText(movieDb.getReleaseDate());
        popularity.setText(String.valueOf(movieDb.getPopularity()));

        if(movieDb.getVoteAverage() == 0.0){
            ratingText = "not rated";
        }
        else ratingText = String.valueOf(movieDb.getVoteAverage()) + " / 10";

        rating.setText(ratingText);
        votes.setText(String.valueOf(movieDb.getVoteCount()));
        addToWatchlist.setText("add to watchlist");

    }

    public void addToWatchlist(View view) {
        databaseViewModel.addWatchlistMovie(movieDb);
        movieId = movieDb.getId();

        addToWatchlist.setText("Added!");
        addToWatchlist.setClickable(false);

        RecomendationTask rt = new RecomendationTask();
        rt.execute();
    }
}
