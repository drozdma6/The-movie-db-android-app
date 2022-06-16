package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TopRatedMovieDao {
    @Query("SELECT * FROM topRatedMovies")
    abstract fun getMoviesStream(): Flow<List<DbTopRatedMovie>>

    @Transaction
    open suspend fun synchronizeMovies(topRatedMovies: List<DbTopRatedMovie>) {
        delete()
        insert(topRatedMovies)
    }

    @Insert
    protected abstract suspend fun insert(topRatedMovies: List<DbTopRatedMovie>)

    @Query("DELETE FROM topRatedMovies")
    protected abstract suspend fun delete()

    @Query("SELECT * FROM topRatedMovies where title LIKE :title")
    abstract fun getMoviesWithTitle(title: String): Flow<List<DbTopRatedMovie>>
}
