package com.abdelrahman.movie_details_domain.entity

data class MovieDetailsDTO(
    val backdropPath: String?=null,
    val title: String?=null,
    val overview: String?=null,
    val posterPath: String?=null,
    val releaseDate: String?=null,
    val tagline: String?=null,
    val voteAverage: Double?=null,
    val genres: List<String?>?=null,
    val productionCompanies: List<String?>?=null
)