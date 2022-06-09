package cz.cvut.fit.drozdma6.semestral.features.movies.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Query("SELECT * FROM movie")
    abstract fun getMoviesStream(): Flow<List<DbMovie>>

    @Transaction
    open suspend fun synchronizeMovies(movies: List<DbMovie>) {
        delete()
        insert(movies)
    }

    @Insert
    protected abstract suspend fun insert(movies: List<DbMovie>)

    @Query("DELETE FROM movie")
    protected abstract suspend fun delete()
}
