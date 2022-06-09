package cz.cvut.fit.drozdma6.semestral.features.movies

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDatabaseDataSource {

    fun getMoviesStream(): Flow<List<Movie>>

    suspend fun synchronizeMovies(movies: List<Movie>)
}