package com.abdelrahman.movies_data.remote

object ApiKey {
    init {
        System.loadLibrary("movie_db")
    }

    external fun getApiKey(): String
}