package com.abdelrahman.movies_list_domain.entity

data class Movie(
    val movieId: Int? = null,
    val movieName: String? = null,
    val voteCount: Int? = null,
    val voteAverage: Double? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val overview: String?=null
)
