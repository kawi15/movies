package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RemovedDao {

    @Query("SELECT * FROM  Removed")
    List<Removed> getMoviesId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Removed removed);
}
