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

public class MovieDetailsRecommendation extends AppCompatActivity {

    private DatabaseViewModel databaseViewModel;
    private Recommendation recommendation;
    private int movieId;

    TextView title;
    TextView releaseDate;
    TextView overview;
    TextView popularity;
    TextView rating;
    TextView votes;
    ImageView imageView;
    String ratingText;
    Button addToWatchlist;
    Button addToWatched;
    Button removeFromRecommendations;



    public class RecommendationTask extends AsyncTask<Void, Void, List<MovieDb>> {

        @Override
        protected List<MovieDb> doInBackground(Void... voids) {
            MovieResultsPage movies = new TmdbApi("f753872c7aa5c000e0f46a4ea6fc49b2").getMovies().getRecommendedMovies(movieId, "en-US", 1);
            List<MovieDb> listMovies = movies.getResults();

            return listMovies;
        }

        @Override
        protected void onPostExecute(List<MovieDb> recommendations) {
            /*removed = databaseViewModel.getRemovedFromRecomendations();
            for(MovieDb item : recomendations){
                for(Removed movieRemoved : removed){
                    if(item.getId() != movieRemoved.getMovieId()){
                        databaseViewModel.addRecomendationMovie(item);
                    }
                }
            }*/

            for(MovieDb item : recommendations){
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
        setContentView(R.layout.movie_details_recommendation);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        releaseDate = findViewById(R.id.t1);
        title = findViewById(R.id.title);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        overview.setScrollbarFadingEnabled(false);
        popularity = findViewById(R.id.tt1);
        rating = findViewById(R.id.ttt3);
        votes = findViewById(R.id.ttt4);
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);
        addToWatched = findViewById(R.id.add_watched);
        removeFromRecommendations = findViewById(R.id.remove_recommendation);

        Bundle bundle = getIntent().getExtras();

        recommendation = (Recommendation) bundle.getSerializable("object");
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + recommendation.getPosterPath()).into(imageView);
        title.setText(recommendation.getMovieTitle());
        overview.setText(recommendation.getOverview());
        releaseDate.setText(recommendation.getReleaseDate());
        popularity.setText(String.valueOf(recommendation.getPopularity()));

        if(recommendation.getRating() == 0.0){
            ratingText = "not rated";
        }
        else ratingText = String.valueOf(recommendation.getRating()) + " / 10";

        rating.setText(ratingText);
        votes.setText(String.valueOf(recommendation.getVotes()));

        addToWatched.setText("add to watched");
        addToWatchlist.setText("add to watchlist");
        removeFromRecommendations.setText("remove from recomendations");
    }

    public void addToWatchlist(View view) {
        movieId = recommendation.getMovieId();
        databaseViewModel.addWatchlistMovie(recommendation);
        databaseViewModel.addRemovedMovie(recommendation);
        databaseViewModel.deleteRecommendationMovie(recommendation);

        addToWatchlist.setText("Added!");
        addToWatchlist.setClickable(false);

        RecommendationTask rt = new RecommendationTask();
        rt.execute();
    }

    public void addToWatched(View view) {
        movieId = recommendation.getMovieId();
        databaseViewModel.addWatchedMovie(recommendation);
        databaseViewModel.addRemovedMovie(recommendation);
        databaseViewModel.deleteRecommendationMovie(recommendation);


        addToWatched.setText("Added!");
        addToWatched.setClickable(false);

        RecommendationTask rt = new RecommendationTask();
        rt.execute();
    }

    public void removeFromRecommendations(View view) {
        databaseViewModel.addRemovedMovie(recommendation);
        databaseViewModel.deleteRecommendationMovie(recommendation);

        removeFromRecommendations.setText("Removed!");
        removeFromRecommendations.setClickable(false);
    }
}
