package cz.cvut.fit.drozdma6.semestral.features.movies.data

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDatabaseDataSource {
    fun getPopularMoviesStream(): Flow<List<Movie>>

    suspend fun synchronizePopularMovies(movies: List<Movie>)

    fun getTopRatedMoviesStream(): Flow<List<Movie>>

    suspend fun synchronizeTopRatedMovies(movies: List<Movie>)
}