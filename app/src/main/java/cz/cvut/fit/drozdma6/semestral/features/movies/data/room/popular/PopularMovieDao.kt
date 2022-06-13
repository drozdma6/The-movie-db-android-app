package cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.DbPopularMovie
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PopularMovieDao {

    @Query("SELECT * FROM popularMovies")
    abstract fun getMoviesStream(): Flow<List<DbPopularMovie>>

    @Transaction
    open suspend fun synchronizeMovies(popularMovies: List<DbPopularMovie>) {
        delete()
        insert(popularMovies)
    }

    @Insert
    protected abstract suspend fun insert(popularMovies: List<DbPopularMovie>)

    @Query("DELETE FROM popularMovies")
    protected abstract suspend fun delete()
}
