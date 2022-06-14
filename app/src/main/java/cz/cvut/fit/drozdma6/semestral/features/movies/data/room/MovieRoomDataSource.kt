package cz.cvut.fit.drozdma6.semestral.features.movies.data.room

import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieDatabaseDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.DbPopularMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.popular.PopularMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.DbTopRatedMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.topRated.TopRatedMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist.DbWatchlistMovie
import cz.cvut.fit.drozdma6.semestral.features.movies.data.room.watchlist.WatchlistMovieDao
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(
    private val popularMovieDao: PopularMovieDao,
    private val topRatedMovieDao: TopRatedMovieDao,
    private val watchlistMovieDao: WatchlistMovieDao
) : MovieDatabaseDataSource {
    private fun DbPopularMovie.toMovie() =
        Movie(id, poster_path, title, overview, original_language)

    private fun Movie.toDbPopularMovie() =
        DbPopularMovie(id, poster_path, title, overview, original_language)

    private fun DbTopRatedMovie.toMovie() =
        Movie(id, poster_path, title, overview, original_language)

    private fun Movie.toTopRatedMovie() =
        DbTopRatedMovie(id, poster_path, title, overview, original_language)

    private fun DbWatchlistMovie.toMovie() =
        Movie(id, poster_path, title, overview, original_language)

    private fun Movie.toDbWatchListMovie() =
        DbWatchlistMovie(id, poster_path, title, overview, original_language)

    override fun getPopularMoviesStream(): Flow<List<Movie>> {
        return popularMovieDao.getMoviesStream().map { dbMovies ->
            dbMovies.map { dbMovie ->
                dbMovie.toMovie()
            }
        }
    }

    override suspend fun synchronizePopularMovies(movies: List<Movie>) {
        val dbMovies = movies.map { movie ->
            movie.toDbPopularMovie()
        }
        popularMovieDao.synchronizeMovies(dbMovies)
    }

    override fun getTopRatedMoviesStream(): Flow<List<Movie>> {
        return topRatedMovieDao.getMoviesStream().map { dbMovies ->
            dbMovies.map { dbMovie ->
                dbMovie.toMovie()
            }
        }
    }

    override suspend fun synchronizeTopRatedMovies(movies: List<Movie>) {
        val dbMovie = movies.map { movie ->
            movie.toTopRatedMovie()
        }
        topRatedMovieDao.synchronizeMovies(dbMovie)
    }

    override fun getWatchlistMoviesStream(): Flow<List<Movie>> {
        return watchlistMovieDao.getWatchlistMoviesStream().map { movies ->
            movies.map { watchlistMovie ->
                watchlistMovie.toMovie()
            }
        }
    }

    override fun insert(movie: Movie) {
        watchlistMovieDao.insert(
            movie.toDbWatchListMovie()
        )
    }

    override fun delete(movie: Movie) {
        watchlistMovieDao.delete(
            movie.toDbWatchListMovie()
        )
    }
}
