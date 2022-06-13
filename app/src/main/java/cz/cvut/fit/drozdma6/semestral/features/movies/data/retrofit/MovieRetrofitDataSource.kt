package cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit

import cz.cvut.fit.drozdma6.semestral.features.movies.data.MovieRemoteDataSource
import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie

class MovieRetrofitDataSource(
    private val movieApiDescription: MovieApiDescription
) : MovieRemoteDataSource {
    override suspend fun fetchPopularMovies(): List<Movie> {
        return movieApiDescription.fetchPopularMovies().results.map { apiMovie ->
            Movie(
                id = apiMovie.id,
                poster_path = apiMovie.poster_path,
                title = apiMovie.title,
                overview = apiMovie.overview,
                original_language = apiMovie.original_language
            )
        }
    }

    override suspend fun fetchTopRatedMovies(): List<Movie> {
        return movieApiDescription.fetchTopRatedMovies().results.map { apiMovie ->
            Movie(
                id = apiMovie.id,
                poster_path = apiMovie.poster_path,
                title = apiMovie.title,
                overview = apiMovie.overview,
                original_language = apiMovie.original_language
            )
        }
    }
}


