package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class DbWatchlistMovie (
    @PrimaryKey val id: String,
    val poster_path: String,
    val title: String,
    val overview: String,
    val original_language: String
)