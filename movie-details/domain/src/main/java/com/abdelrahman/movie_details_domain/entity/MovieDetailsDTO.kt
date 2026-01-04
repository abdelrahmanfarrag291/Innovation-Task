package com.abdelrahman.movie_details_domain.entity

data class MovieDetailsDTO(
    val backdropPath: String?,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val tagline: String?,
    val voteAverage: Double?,
    val genres: List<String?>?,
    val productionCompanies: List<String?>?
)