package com.abdelrahman.movies_data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelrahman.movies_data.utils.Constants.Database.MOVIES_TABLE_NAME

@Entity(
    tableName = MOVIES_TABLE_NAME
)
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val movieId: Int?=null,
    val movieName : String?=null,
    val voteCount: Int?=null,
    val voteAverage: Double?=null,
    val backdropPath: String?=null,
    val posterPath: String?=null
)
