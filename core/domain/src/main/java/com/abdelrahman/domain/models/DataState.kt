package com.abdelrahman.domain.models

sealed interface DataState<out T> {

    data class DataSuccess<T>(val result: T) : DataState<T>
    data class DataError(val errorTypes: ErrorTypes) : DataState<Nothing>
}