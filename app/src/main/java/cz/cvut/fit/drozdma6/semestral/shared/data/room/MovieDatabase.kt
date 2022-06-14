package cz.cvut.fit.drozdma6.semestral.shared.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.DbPopularMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.DbTopRatedMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.PopularMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.TopRatedMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist.DbWatchlistMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist.WatchlistMovieDao

@Database(
    entities =
    [
        DbPopularMovie::class,
        DbTopRatedMovie::class,
        DbWatchlistMovie::class
    ], version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getPopularMovieDao(): PopularMovieDao

    abstract fun getTopRatedMovieDao(): TopRatedMovieDao

    abstract fun getWatchlistMovieDao(): WatchlistMovieDao
}


