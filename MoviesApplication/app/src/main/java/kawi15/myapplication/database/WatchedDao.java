package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WatchedDao {

    @Query("SELECT * FROM Watched ORDER BY movie_title ASC")
    List<Watched> getAll();

    @Insert
    void addMovie(Watched watchedMovie);

    @Delete
    void deleteMovie(Watched watchedMovie);
}
