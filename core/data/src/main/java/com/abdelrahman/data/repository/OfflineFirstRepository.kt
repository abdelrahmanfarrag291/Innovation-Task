@file:Suppress("UNCHECKED_CAST")

package com.abdelrahman.data.repository

import com.abdelrahman.data.local_datasource.IBaseLocalDataSource
import com.abdelrahman.data.remote_datasource.BaseRemoteDataSource
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.domain.models.StringWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class OfflineFirstRepository(
    private val remoteDataSource: BaseRemoteDataSource,
    private val iBaseLocalDataSource: IBaseLocalDataSource
) {

    suspend fun <T> execute(): Flow<Result<T>>{
        return flow {
            emit(Result.ResultNoInternetConnection(
                error = StringWrapper.FromString("")
            ) as Result<T>)
        }
    }
}