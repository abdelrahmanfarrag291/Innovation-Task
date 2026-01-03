package com.abdelrahman.data.helper

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object TestHelper {
    val testErrorResponse = """
        {"code":7,
        "message":"error",
        "success":false
        }
    """.trimIndent()

  //  val errorResponse = Response.error<Any>(500, testErrorResponse.toResponseBody())
}