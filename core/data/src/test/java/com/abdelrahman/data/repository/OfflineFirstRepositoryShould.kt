package com.abdelrahman.data.repository

import com.abdelrahman.data.remote_datasource.BaseRemoteDataSource
import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

/**
 * This repos responsibility to fetch first data from [com.abdelrahman.data.remote_datasource.BaseRemoteDataSource]
 * if it returns data successfully then save data to [com.abdelrahman.data.local_datasource.IBaseLocalDataSource] and then get
 * the data from that [com.abdelrahman.data.local_datasource.IBaseLocalDataSource] ,
 * if it return data failure then return same result obtained from [com.abdelrahman.data.remote_datasource.BaseRemoteDataSource]
 * IF IT return data no internet connect then go to look for data inside [com.abdelrahman.data.local_datasource.IBaseLocalDataSource]
 * if it has cached data return it with state sucess otherwise return no internet connection
 */
class OfflineFirstRepositoryShould {

    //SUT
    private lateinit var offlineFirstRepository: OfflineFirstRepository
    val remoteDataSource = mock<BaseRemoteDataSource>()
    val fakeLocalDataSource = FakeLocalDataSource()

    @Before
    fun init() {
        offlineFirstRepository = OfflineFirstRepository(remoteDataSource, fakeLocalDataSource)
    }

    @Test
    fun `when data is success then it must save data to local database and then return it from local database`() =
        runTest {
            val response = Response.success(listOf("1", "2"))
            whenever(remoteDataSource.safeAPICALL {
                response
            }).thenReturn(
                Result.ResultSuccess(listOf("1", "2"))
            )
            val actual = offlineFirstRepository.execute<List<String>>(
                {
                    remoteDataSource.safeAPICALL {
                        response
                    }
                }, localCall = { fakeLocalDataSource.getAllCachedData() }, saveLocal = {
                       fakeLocalDataSource.addAllFakes(listOf("1", "2"))

                }
            ).first()
            val expected = flow { emit(Result.ResultSuccess(data = listOf("1", "2"))) }.first()
            assertEquals(expected, actual)
        }


}