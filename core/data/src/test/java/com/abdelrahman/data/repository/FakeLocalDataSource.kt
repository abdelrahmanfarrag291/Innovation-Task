package com.abdelrahman.data.repository

import com.abdelrahman.data.local_datasource.IBaseLocalDataSource

class FakeLocalDataSource : IBaseLocalDataSource {
    private val fakeList = mutableListOf<String>()

    fun addAllFakes(fakes: List<String>) {
        fakeList.addAll(fakes)
    }

   suspend fun getAllCachedData(): List<String> {
        return fakeList
    }
}