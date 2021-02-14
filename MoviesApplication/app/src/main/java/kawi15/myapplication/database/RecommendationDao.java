package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecommendationDao {

    @Query("SELECT * FROM Recommendation ORDER BY movie_title ASC")
    List<Recommendation> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(Recommendation recommendation);

    @Delete
    void deleteMovie(Recommendation recommendation);
}
