package com.abdelrahman.movies_data.mapper

import com.abdelrahman.movies_data.local.database.MoviesEntity
import com.abdelrahman.movies_data.models.MovieResponse
import com.abdelrahman.movies_data.utils.Constants.BASE_IMAGE_URL_154
import com.abdelrahman.movies_data.utils.Constants.BASE_IMAGE_URL_500
import com.abdelrahman.movies_list_domain.entity.Movie


fun MovieResponse.asMovieEntity(): MoviesEntity {
    return MoviesEntity(
        movieId = id,
        movieName = originalTitle,
        voteCount = voteCount,
        voteAverage = voteAverage,
        backdropPath = backdropPath,
        posterPath = posterPath,
        overview = overview
    )
}

fun MoviesEntity.asMovie(): Movie {
    return Movie(
        movieId = movieId,
        movieName = movieName,
        voteCount = voteCount,
        voteAverage = voteAverage,
        backdropPath = "$BASE_IMAGE_URL_500$backdropPath",
        posterPath = "$BASE_IMAGE_URL_154$posterPath",
        overview = overview
    )
}