package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY movie_title ASC")
    List<Watchlist> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Watchlist watchlistMovie);

    @Delete
    void deleteMovie(Watchlist watchlistMovie);
}
