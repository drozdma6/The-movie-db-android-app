package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WatchlistMovieDao {
    @Query("SELECT * FROM watchlist")
    abstract fun getWatchlistMoviesStream(): Flow<List<DbWatchlistMovie>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun insert(watchlistMovie: DbWatchlistMovie)

    @Delete
    abstract suspend fun delete(watchlistMovie: DbWatchlistMovie)

    @Query("SELECT EXISTS(SELECT * FROM watchlist WHERE id = :id)")
    abstract suspend fun isInWatchlist(id : String) : Boolean

    @Query("SELECT * FROM watchlist where title LIKE :title")
    abstract fun getMoviesWithTitle(title: String): Flow<List<DbWatchlistMovie>>
}