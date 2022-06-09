package cz.cvut.fit.drozdma6.semestral.features.movies.data.retrofit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiMovie(
    val id: String,
    val poster_path: String,
    val title: String,
    val overview: String,
    val original_language: String
)
