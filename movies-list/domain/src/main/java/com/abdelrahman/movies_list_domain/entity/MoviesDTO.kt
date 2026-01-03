package com.abdelrahman.movies_list_domain.entity

data class MoviesDTO(
    val currentPage: Int?,
    val totalPages : Int?,
    val moviesList : List<Movie>?
)
