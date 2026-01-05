package com.abdelrahman.movie_details_data.mappers

import com.abdelrahman.common_data.utils.Constants.BASE_IMAGE_URL_154
import com.abdelrahman.common_data.utils.Constants.BASE_IMAGE_URL_500
import com.abdelrahman.movie_details_data.remote.models.MovieDetailsResponse
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO

fun MovieDetailsResponse.asMovieDetailsDto(): MovieDetailsDTO {
    return MovieDetailsDTO(
        backdropPath = "$BASE_IMAGE_URL_500$backdropPath",
        title = originalTitle,
        overview = overview,
        posterPath = "$BASE_IMAGE_URL_154$posterPath",
        releaseDate = releaseDate,
        tagline = tagline,
        voteAverage = voteAverage,
        genres = genres?.map {
            return@map it?.name
        },
        productionCompanies = productionCompanies?.map {
            return@map "$BASE_IMAGE_URL_154${it?.logoPath}"
        }
    )
}