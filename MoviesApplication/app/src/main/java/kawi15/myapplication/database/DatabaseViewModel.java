package kawi15.myapplication.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;

public class DatabaseViewModel extends AndroidViewModel {
    private AppDatabase db;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    public Removed getRemoveFromRecommendations(int id) {return db.removedDao().getMovieid(id);}

    public List<Watchlist> getWatchlistList() {
        return db.watchlistDao().getAll();
    }

    public List<Watched> getWatchedList(){
        return db.watchedDao().getAll();
    }

    public List<Recommendation> getRecommendationList() {
        return db.recommendationDao().getAll();
    }


    public void addWatchlistMovie(MovieDb movieDb){
        Watchlist addedMovie = new Watchlist();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getVoteAverage());
        addedMovie.setVotes(movieDb.getVoteCount());
        addedMovie.setPopularity(movieDb.getPopularity());


        db.watchlistDao().addMovie(addedMovie);
    }

    public void addWatchlistMovie(Recommendation recommendation){
        Watchlist addedMovie = new Watchlist();
        addedMovie.setMovieId(recommendation.getMovieId());
        addedMovie.setMovieTitle(recommendation.getMovieTitle());
        addedMovie.setOverview(recommendation.getOverview());
        addedMovie.setPosterPath(recommendation.getPosterPath());
        addedMovie.setReleaseDate(recommendation.getReleaseDate());
        addedMovie.setRating(recommendation.getRating());
        addedMovie.setVotes(recommendation.getVotes());
        addedMovie.setPopularity(recommendation.getPopularity());


        db.watchlistDao().addMovie(addedMovie);
    }

    public void addWatchlistMovie(Watched watched){
        Watchlist addedMovie = new Watchlist();
        addedMovie.setMovieId(watched.getMovieId());
        addedMovie.setMovieTitle(watched.getMovieTitle());
        addedMovie.setOverview(watched.getOverview());
        addedMovie.setPosterPath(watched.getPosterPath());
        addedMovie.setReleaseDate(watched.getReleaseDate());
        addedMovie.setRating(watched.getRating());
        addedMovie.setVotes(watched.getVotes());
        addedMovie.setPopularity(watched.getPopularity());


        db.watchlistDao().addMovie(addedMovie);
    }

    public void addWatchedMovie(MovieDb movieDb){
        Watched addedMovie = new Watched();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getVoteAverage());
        addedMovie.setVotes(movieDb.getVoteCount());
        addedMovie.setPopularity(movieDb.getPopularity());


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
        addedMovie.setVotes(watchlist.getVotes());
        addedMovie.setPopularity(watchlist.getPopularity());


        db.watchedDao().addMovie(addedMovie);
    }

    public void addWatchedMovie(Recommendation recommendation){
        Watched addedMovie = new Watched();
        addedMovie.setMovieId(recommendation.getMovieId());
        addedMovie.setMovieTitle(recommendation.getMovieTitle());
        addedMovie.setOverview(recommendation.getOverview());
        addedMovie.setPosterPath(recommendation.getPosterPath());
        addedMovie.setReleaseDate(recommendation.getReleaseDate());
        addedMovie.setRating(recommendation.getRating());
        addedMovie.setVotes(recommendation.getVotes());
        addedMovie.setPopularity(recommendation.getPopularity());


        db.watchedDao().addMovie(addedMovie);
    }

    public void addRecommendationMovie(MovieDb movieDb){
        Recommendation addedMovie = new Recommendation();
        addedMovie.setMovieId(movieDb.getId());
        addedMovie.setMovieTitle(movieDb.getOriginalTitle());
        addedMovie.setOverview(movieDb.getOverview());
        addedMovie.setPosterPath(movieDb.getPosterPath());
        addedMovie.setReleaseDate(movieDb.getReleaseDate());
        addedMovie.setRating(movieDb.getVoteAverage());
        addedMovie.setVotes(movieDb.getVoteCount());
        addedMovie.setPopularity(movieDb.getPopularity());
        db.recommendationDao().addMovie(addedMovie);
    }

    public void addRemovedMovie(Recommendation recommendation){
        Removed addedMovie = new Removed();
        addedMovie.setMovieId(recommendation.getMovieId());

        db.removedDao().addMovie(addedMovie);
    }

    public void deleteWatchlistMovie(Watchlist watchlist){
        db.watchlistDao().deleteMovie(watchlist);
    }

    public void deleteWatchedMovie(Watched watched){
        db.watchedDao().deleteMovie(watched);
    }

    public void deleteRecommendationMovie(Recommendation recommendation){
        db.recommendationDao().deleteMovie(recommendation);
    }
}
