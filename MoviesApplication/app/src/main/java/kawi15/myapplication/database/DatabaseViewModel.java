package kawi15.myapplication.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class DatabaseViewModel extends AndroidViewModel {
    private AppDatabase db;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    public List<Watchlist> getWatchlistList() {
        return db.watchlistDao().getAll();
    }

    public List<Watched> getWatchedList(){
        return db.watchedDao().getAll();
    }

    public List<Recomendation> getRecomendationList() {
        return db.recomendationDao().getAll();
    }


    public void addWatchlistMovie(MovieDb movieDb){
        Watchlist addedMovie = new Watchlist();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getUserRating());

        db.watchlistDao().addMovie(addedMovie);
    }

    public void addWatchedMovie(MovieDb movieDb){
        Watched addedMovie = new Watched();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getUserRating());

        db.watchedDao().addMovie(addedMovie);
    }

    public void addWatchedMovie(Watchlist watchlist){
        Watched addedMovie = new Watched();
        addedMovie.setMovieId(watchlist.getMovieId());
        addedMovie.setMovieTitle(watchlist.getMovieTitle());
        addedMovie.setOverview(watchlist.getOverview());
        addedMovie.setPosterPath(watchlist.getPosterPath());
        addedMovie.setReleaseDate(watchlist.getReleaseDate());
        addedMovie.setRating(watchlist.getRating());

        db.watchedDao().addMovie(addedMovie);
    }

    public void addRecomendationMovie(MovieDb movieDb){
        Recomendation addedMovie = new Recomendation();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getUserRating());

        db.recomendationDao().addMovie(addedMovie);
    }

    public void deleteWatchlistMovie(Watchlist watchlist){
        db.watchlistDao().deleteMovie(watchlist);
    }

    public void deleteWatchedMovie(Watched watched){
        db.watchedDao().deleteMovie(watched);
    }

    public void deleteRecomendationMovie(Recomendation recomendation){
        db.recomendationDao().deleteMovie(recomendation);
    }
}
