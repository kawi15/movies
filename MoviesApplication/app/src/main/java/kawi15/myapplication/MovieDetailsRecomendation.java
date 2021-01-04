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
import kawi15.myapplication.database.Removed;
import kawi15.myapplication.database.Watched;
import kawi15.myapplication.database.Watchlist;

public class MovieDetailsRecomendation extends AppCompatActivity {

    private DatabaseViewModel databaseViewModel;
    private Recomendation recomendation;
    private int movieId;
    private List<Removed> removed;
    TextView textView;
    TextView title;
    TextView releaseDate;
    TextView overview;
    ImageView imageView;
    Button addToWatchlist;
    Button addToWatched;
    Button removeFromRecomendations;



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
                for(Removed movieRemoved : removed){
                    if(item.getId() != movieRemoved.getMovieId()){
                        databaseViewModel.addRecomendationMovie(item);
                    }
                }
            }*/

            for(MovieDb item : recomendations){
                int toCheck = item.getId();
                if(databaseViewModel.getRemoveFromRecomendations(toCheck) == null){
                    databaseViewModel.addRecomendationMovie(item);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_recomendation);
        databaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        textView = findViewById(R.id.t1);
        title = findViewById(R.id.title);
        //releaseDate = findViewById(R.id.first_text2);
        overview = findViewById(R.id.overview);
        overview.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.image);
        addToWatchlist = findViewById(R.id.add_watchlist);
        addToWatched = findViewById(R.id.add_watched);
        removeFromRecomendations = findViewById(R.id.remove_recomendation);

        Bundle bundle = getIntent().getExtras();

        recomendation = (Recomendation) bundle.getSerializable("object");
        Glide.with(imageView).load("https://image.tmdb.org/t/p/w500" + recomendation.getPosterPath()).into(imageView);
        title.setText(recomendation.getMovieTitle());
        overview.setText(recomendation.getOverview());
        addToWatched.setText("add to watched");
        addToWatchlist.setText("add to watchlist");
        removeFromRecomendations.setText("remove from recomendations");
    }

    public void addToWatchlist(View view) {
        movieId = recomendation.getMovieId();
        databaseViewModel.addWatchlistMovie(recomendation);
        databaseViewModel.addRemovedMovie(recomendation);
        databaseViewModel.deleteRecomendationMovie(recomendation);

        addToWatchlist.setText("Added!");
        addToWatchlist.setClickable(false);

        RecomendationTask rt = new RecomendationTask();
        rt.execute();
    }

    public void addToWatched(View view) {
        movieId = recomendation.getMovieId();
        databaseViewModel.addWatchedMovie(recomendation);
        databaseViewModel.addRemovedMovie(recomendation);
        databaseViewModel.deleteRecomendationMovie(recomendation);


        addToWatched.setText("Added!");
        addToWatched.setClickable(false);

        RecomendationTask rt = new RecomendationTask();
        rt.execute();
    }

    public void removeFromRecomendations(View view) {
        databaseViewModel.addRemovedMovie(recomendation);
        databaseViewModel.deleteRecomendationMovie(recomendation);

        removeFromRecomendations.setText("Removed!");
        removeFromRecomendations.setClickable(false);
    }
}
