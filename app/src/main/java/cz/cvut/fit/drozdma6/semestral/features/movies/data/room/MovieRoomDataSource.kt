package cz.cvut.fit.drozdma6.semestral.features.movies.data.room

import cz.cvut.fit.drozdma6.semestral.features.movies.MovieDatabaseDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(
    private val movieDao: MovieDao
) : MovieDatabaseDataSource {

    override fun getMoviesStream(): Flow<List<Movie>> {
        return movieDao.getMoviesStream().map { dbMovies ->
            dbMovies.map { dbMovie ->
                Movie(
                    id = dbMovie.id,
                    poster_path = dbMovie.poster_path,
                    title = dbMovie.title,
                    overview = dbMovie.overview,
                    original_language = dbMovie.original_language
                )
            }
        }
    }

    override suspend fun synchronizeMovies(movies: List<Movie>) {
        val dbMovie = movies.map { movie ->
            DbMovie(
                id = movie.id,
                poster_path = movie.poster_path,
                title = movie.title,
                overview = movie.overview,
                original_language = movie.original_language
            )
        }
        movieDao.synchronizeMovies(dbMovie)
    }
}
