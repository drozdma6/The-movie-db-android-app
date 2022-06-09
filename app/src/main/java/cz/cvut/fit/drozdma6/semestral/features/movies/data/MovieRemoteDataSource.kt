package cz.cvut.fit.drozdma6.semestral.features.movies.data

import cz.cvut.fit.drozdma6.semestral.features.movies.domain.Movie


interface MovieRemoteDataSource {

    suspend fun fetchPopularMovie(): List<Movie>
}
