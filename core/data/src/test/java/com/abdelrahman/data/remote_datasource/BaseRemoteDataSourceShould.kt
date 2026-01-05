package com.abdelrahman.data.remote_datasource

import com.abdelrahman.data.remote_datasource.networkstate.ICheckNetworkState
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response


/***
 * BaseRemoteDataSource is class that will hold a function for safeAPICall which determines if there is internet connection or not
 * and if so it will return result obtained from [com.abdelrahman.data.remote_datasource.validate_remote.ValidateRemoteSource] otherwise
 * it should return [android.speech.RecognizerResultsIntent]
 *
 *
 *  ### TEST CASES
 *  1-when there is internet connection , it should return same result got from [com.abdelrahman.data.remote_datasource.validate_remote.ValidateRemoteSource]
 *  2-where there is no internet connection , it should return [Result.ResultNoInternetConnection]
 */
class BaseRemoteDataSourceShould {
    //SUT
    private lateinit var baseRemoteDataSource: BaseRemoteDataSource
    private val iCheckNetworkState = mock<ICheckNetworkState>()
    private val iValidateRemoteDataSource = mock<IValidateRemoteSource>()

    @Before
    fun init() {
        baseRemoteDataSource = BaseRemoteDataSource(iValidateRemoteDataSource, iCheckNetworkState)
    }

    @Test
    fun `when there is available internet , then return same result from validate remote source`() =
        runTest {
            val response = Response.success("")
            whenever(iCheckNetworkState.isConnected()).thenReturn(true)
            whenever(iValidateRemoteDataSource.validate(response)).thenReturn(
                Result.ResultSuccess(
                    ""
                )
            )
            val expected = Result.ResultSuccess(data = "")
            val actual = baseRemoteDataSource.safeAPICALL {
                response
            }

            assertEquals(expected, actual)
        }

    @Test
    fun `when there is not internet connection , then return Result NoInternetConnection`()=runTest {
        whenever(iCheckNetworkState.isConnected()).thenReturn(false)

        val expected = Result.ResultNoInternetConnection(error = StringWrapper.FromResource(R.string.no_internet_connection))
        val actual = baseRemoteDataSource.safeAPICALL {
            Response.success("")
        }
        assertEquals(expected, actual)

    }
}