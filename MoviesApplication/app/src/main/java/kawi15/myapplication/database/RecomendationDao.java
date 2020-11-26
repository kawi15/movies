package kawi15.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecomendationDao {

    @Query("SELECT * FROM Recomendation ORDER BY movie_title ASC")
    List<Recomendation> getAll();

    @Insert
    void addMovie(Recomendation recomendation);

    @Delete
    void deleteMovie(Recomendation recomendation);
}
