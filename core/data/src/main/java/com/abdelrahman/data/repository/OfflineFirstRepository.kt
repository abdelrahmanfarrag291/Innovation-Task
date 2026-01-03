@file:Suppress("UNCHECKED_CAST")

package com.abdelrahman.data.repository

import com.abdelrahman.data.local_datasource.IBaseLocalDataSource
import com.abdelrahman.data.remote_datasource.BaseRemoteDataSource

open class OfflineFirstRepository(
    private val remoteDataSource: BaseRemoteDataSource,
    private val iBaseLocalDataSource: IBaseLocalDataSource
) {

}