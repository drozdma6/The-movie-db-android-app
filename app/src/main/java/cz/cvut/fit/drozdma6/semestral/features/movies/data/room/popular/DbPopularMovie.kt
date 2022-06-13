package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popularMovies")
data class DbPopularMovie(
    @PrimaryKey val id: String,
    val poster_path: String,
    val title: String,
    val overview: String,
    val original_language: String,
)
