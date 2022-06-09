package cz.cvut.fit.drozdma6.semestral.features.movies

import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRemoteDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    private val movieDatabaseDataSource: MovieDatabaseDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    fun getMoviesStream(): Flow<List<Movie>> {
        return movieDatabaseDataSource.getMoviesStream()
    }

    suspend fun fetchPopularMovies() {
        val movies = movieRemoteDataSource.fetchPopularMovie()
        movieDatabaseDataSource.synchronizeMovies(movies)
    }
}
