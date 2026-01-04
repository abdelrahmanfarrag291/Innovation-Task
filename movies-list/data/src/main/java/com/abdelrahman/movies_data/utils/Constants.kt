package com.abdelrahman.movies_data.utils

object Constants {
    const val LANGUAGE_ENGLISH = "en"
    const val BASE_IMAGE_URL_500= "https://image.tmdb.org/t/p/w500/"
    const val BASE_IMAGE_URL_154="https://image.tmdb.org/t/p/w154/"

    object EndPoints{
        const val PLAYING_MOVIES ="movie/now_playing"
    }
    object QueryParams{
        const val PAGE = "page"
    }

    object Database{
        const val MOVIES_TABLE_NAME = "_movies"
        const val MOVIES_DB = "movies_db"
    }
}