package cz.cvut.fit.drozdma6.semestral.shared.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.DbPopularMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.DbTopRatedMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.PopularMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.TopRatedMovieDao

@Database(entities = [DbPopularMovie::class, DbTopRatedMovie::class], version = 5)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getPopularMovieDao(): PopularMovieDao

    abstract fun getTopRatedMovieDao(): TopRatedMovieDao
}


