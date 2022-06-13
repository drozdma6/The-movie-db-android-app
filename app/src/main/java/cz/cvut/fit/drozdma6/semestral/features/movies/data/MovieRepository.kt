package cz.cvut.fit.drozdma6.semestral.features.movies.data

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDatabase: MovieDatabaseDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {
    fun getPopularMoviesStream(): Flow<List<Movie>> {
        return movieDatabase.getPopularMoviesStream()
    }

    fun getTopRatedMoviesStream(): Flow<List<Movie>> {
        return movieDatabase.getTopRatedMoviesStream()
    }

    suspend fun synchronizePopularMovies() {
        val movies = movieRemoteDataSource.fetchPopularMovies()
        movieDatabase.synchronizePopularMovies(movies)
    }

    suspend fun synchronizeTopRatedMovies() {
        val movies = movieRemoteDataSource.fetchTopRatedMovies()
        movieDatabase.synchronizeTopRatedMovies(movies)
    }
}
