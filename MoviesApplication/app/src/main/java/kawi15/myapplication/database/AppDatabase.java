package kawi15.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Watched.class, Watchlist.class, Recomendation.class, Removed.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase dbInstance;

    static AppDatabase getDatabase(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context, AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return dbInstance;
    }


    public abstract WatchlistDao watchlistDao();
    public abstract WatchedDao watchedDao();
    public abstract RecomendationDao recomendationDao();
    public abstract RemovedDao removedDao();
}
