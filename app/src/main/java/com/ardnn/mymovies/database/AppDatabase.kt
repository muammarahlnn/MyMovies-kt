package com.ardnn.mymovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ardnn.mymovies.database.dao.FavoriteFilmDao
import com.ardnn.mymovies.database.dao.RecentFilmDao
import com.ardnn.mymovies.database.entities.FavoriteMovies
import com.ardnn.mymovies.database.entities.FavoriteTvShows
import com.ardnn.mymovies.database.entities.RecentFilms

@Database(
    entities = [
        FavoriteMovies::class,
        FavoriteTvShows::class,
        RecentFilms::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteFilmDao(): FavoriteFilmDao
    abstract fun recentFilmDao(): RecentFilmDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}