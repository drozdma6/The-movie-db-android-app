package cz.cvut.fit.drozdma6.semestral.shared.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.MovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.DbMovie

@Database(entities = [DbMovie::class], version = 4)
abstract class MovieDatabase :RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}


