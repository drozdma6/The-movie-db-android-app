package cz.cvut.fit.drozdma6.semestral.features.movies.data

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

interface MovieRemoteDataSource {
    suspend fun fetchPopularMovies(): List<Movie>

    suspend fun fetchTopRatedMovies(): List<Movie>
}
