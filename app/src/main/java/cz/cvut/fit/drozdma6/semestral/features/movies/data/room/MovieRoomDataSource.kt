package cz.cvut.fit.drozdma6.semestral.features.movies.data.room

import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieDatabaseDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.DbPopularMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.PopularMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.DbTopRatedMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.TopRatedMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(
    private val popularMovieDao: PopularMovieDao,
    private val topRatedMovieDao: TopRatedMovieDao
) : MovieDatabaseDataSource {
    override fun getPopularMoviesStream(): Flow<List<Movie>> {
        return popularMovieDao.getMoviesStream().map { dbMovies ->
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

    override suspend fun synchronizePopularMovies(movies: List<Movie>) {
        val dbMovie = movies.map { movie ->
            DbPopularMovie(
                id = movie.id,
                poster_path = movie.poster_path,
                title = movie.title,
                overview = movie.overview,
                original_language = movie.original_language,
            )
        }
        popularMovieDao.synchronizeMovies(dbMovie)
    }

    override fun getTopRatedMoviesStream(): Flow<List<Movie>> {
        return topRatedMovieDao.getMoviesStream().map { dbMovies ->
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

    override suspend fun synchronizeTopRatedMovies(movies: List<Movie>) {
        val dbMovie = movies.map { movie ->
            DbTopRatedMovie(
                id = movie.id,
                poster_path = movie.poster_path,
                title = movie.title,
                overview = movie.overview,
                original_language = movie.original_language,
            )
        }
        topRatedMovieDao.synchronizeMovies(dbMovie)
    }
}
