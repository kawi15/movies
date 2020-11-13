package kawi15.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Watchlist {
    @PrimaryKey
    public int movieId;
}
