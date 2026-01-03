package com.abdelrahman.movies_data.mapper

import com.abdelrahman.movies_data.local.database.MoviesEntity
import com.abdelrahman.movies_data.models.MovieResponse
import com.abdelrahman.movies_list_domain.entity.Movie


fun MovieResponse.asMovieEntity(): MoviesEntity {
    return MoviesEntity(
        movieId = id,
        movieName = originalTitle,
        voteCount = voteCount,
        voteAverage = voteAverage,
        backdropPath = backdropPath,
        posterPath = posterPath
    )
}

fun MoviesEntity.asMovie(): Movie{
    return Movie(
        movieId = movieId,
        movieName = movieName,
        voteCount = voteCount,
        voteAverage = voteAverage,
        backdropPath = backdropPath,
        posterPath = posterPath
    )
}