package com.abdelrahman.data.remote_datasource

import com.abdelrahman.data.helper.FakeErrorModel
import com.abdelrahman.data.helper.TestHelper
import com.abdelrahman.data.remote_datasource.result.Result
import com.abdelrahman.data.remote_datasource.validate_remote.IValidateRemoteSource
import com.abdelrahman.data.remote_datasource.validate_remote.ValidateRemoteSource
import com.abdelrahman.data.utils.Constants.ErrorCodes.GENERAL_ERROR_CODE
import com.abdelrahman.domain.R
import com.abdelrahman.domain.models.StringWrapper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.math.exp

/**
 * ValidateRemoteSource class is responsible for mapping retrofit response from Response<T> into more meaningful class
 * that is Result<T> which have two cases
 * 1- ResultSuccess => where API responds successfully and no errors appears [StatusCode] 200
 * 2- ResultError => where throws an exception or API failed or any other reason ,also in here we should map the error obtained from server
 * and as error may differ from server to server then we need to make the error more as abstraction as much as we can
 *
 *
 *    #### TEST CASES ######
 *    1- Scenario : when API throws an exception =>it must return result [ResultError] and it in message with Something Went Wrong
 *    2- Scenario : when API isSuccessful is false,and errorBody is not null => it must looks for errorModel obtained from API.
 *    3- Scenario : when API isSuccessful is false,and errorBody is null => it should returns errorCode 999 and something went wrong message.
 *    4- Scenario : when API isSuccessful is true,and body is not null => it should returns the body.
 */

class ValidateDataSourceShould {

    //SUT
    private lateinit var validateRemoteSource: IValidateRemoteSource

    @Before
    fun init() {
        val errorModel = FakeErrorModel()
        validateRemoteSource = ValidateRemoteSource(errorModel)
    }

    @Test
    fun `when API throws an exception it must return ResultError`() = runTest {
        val fakeResponse = mock<Response<*>>()
        whenever(fakeResponse.isSuccessful).thenThrow(RuntimeException(""))
        val expected =
            Result.ResultError(
                errorCode = GENERAL_ERROR_CODE,
                error = StringWrapper.FromResource(R.string.something_went_wrong)
            )
        val actual = validateRemoteSource.validate(fakeResponse)
        assertEquals(expected, actual)
    }

    @Test
    fun `when API is not succes and errorBody is not null then return ResultError with APIErrorModel`() =
        runTest {
            val fakeResponse = mock<Response<*>>()
            whenever(fakeResponse.isSuccessful).thenReturn(false)
            whenever(fakeResponse.errorBody()).thenReturn(TestHelper.testErrorResponse.toResponseBody())
            val expected =
                Result.ResultError(
                    errorCode = 7,
                    error = StringWrapper.FromString("error")
                )
            val actual = validateRemoteSource.validate(fakeResponse)
            assertEquals(expected, actual)
        }

    @Test
    fun `when API is not success and error body is null then return ResultError with General error`() =
        runTest {
            val fakeResponse = mock<Response<*>>()
            whenever(fakeResponse.isSuccessful).thenReturn(false)
            whenever(fakeResponse.errorBody()).thenReturn(null)
            val expected =
                Result.ResultError(
                    errorCode = GENERAL_ERROR_CODE,
                    error = StringWrapper.FromResource(R.string.something_went_wrong)
                )
            val actual = validateRemoteSource.validate(fakeResponse)
            assertEquals(expected, actual)
        }

    @Test
    fun `when API is success and body is not null then return ResultSuccess with the body obtained`() =
        runTest {
            val fakeResponse = mock<Response<*>>()
            whenever(fakeResponse.isSuccessful).thenReturn(true)
            whenever(fakeResponse.body()).thenReturn("")
            val expected =
                Result.ResultSuccess(
                    data = ""
                )
            val actual = validateRemoteSource.validate(fakeResponse)
            assertEquals(expected, actual)
        }

    @Test
    fun `when API is success and body is null then it should return ResultError with general error code and something went wrong message`() =
        runTest {
            val fakeResponse = mock<Response<*>>()
            whenever(fakeResponse.isSuccessful).thenReturn(true)
            whenever(fakeResponse.body()).thenReturn(null)
            val expected =
                Result.ResultError(
                    errorCode = GENERAL_ERROR_CODE,
                    error = StringWrapper.FromResource(R.string.something_went_wrong)
                )
            val actual = validateRemoteSource.validate(fakeResponse)
            assertEquals(expected, actual)

        }

}