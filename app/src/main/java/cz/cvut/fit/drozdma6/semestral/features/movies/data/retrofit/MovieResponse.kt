package cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    val page: Int,
    val results: List<ApiMovie>
)
