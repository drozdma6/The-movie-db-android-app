package cz.cvut.fit.drozdma6.semestral.features.movies.data

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDatabaseDataSource {
    fun getPopularMoviesStream(): Flow<List<Movie>>

    suspend fun synchronizePopularMovies(movies: List<Movie>)

    fun getTopRatedMoviesStream(): Flow<List<Movie>>

    suspend fun synchronizeTopRatedMovies(movies: List<Movie>)

    fun getWatchlistMoviesStream(): Flow<List<Movie>>

    suspend fun insert(movie: Movie)

    suspend fun delete(movie: Movie)

    suspend fun isInWatchlist(id: String): Boolean

    fun getMoviesWithTitle(title: String): Flow<List<Movie>>
}