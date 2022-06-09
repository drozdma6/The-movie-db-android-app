package cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit

import retrofit2.http.GET

interface MovieApiDescription {
    @GET("movie/popular?api_key=eb5864020f8c15b166c9b0818eefe24a")
    suspend fun fetchPopularMovies(): MovieResponse
}