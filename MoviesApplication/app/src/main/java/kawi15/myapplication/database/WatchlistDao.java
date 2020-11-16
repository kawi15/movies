package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY movie_title ASC")
    List<Watchlist> getAll();

    @Insert
    void addMovie(Watchlist watchlistMovie);
}
