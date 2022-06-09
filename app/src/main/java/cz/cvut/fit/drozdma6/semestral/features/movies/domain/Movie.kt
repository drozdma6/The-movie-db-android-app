package cz.cvut.fit.drozdma6.semestral.features.movies.domain

data class Movie(
    val id: String,
    val poster_path: String,
    val title: String,
    val overview: String,
    val original_language: String
)