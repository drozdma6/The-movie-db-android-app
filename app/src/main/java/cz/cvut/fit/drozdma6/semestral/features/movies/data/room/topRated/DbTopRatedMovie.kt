package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topRatedMovies")
data class DbTopRatedMovie(
    @PrimaryKey val id: String,
    val poster_path: String,
    val title: String,
    val overview: String,
    val original_language: String,
)
