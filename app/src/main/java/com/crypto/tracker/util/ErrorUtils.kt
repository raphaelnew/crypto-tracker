package com.crypto.tracker.util

import retrofit2.Response
import retrofit2.Retrofit
import com.crypto.tracker.model.Error
import com.crypto.tracker.model.ErrorStatus
import com.crypto.tracker.network.CoingeckoApi
import java.io.IOException

/**
 * Parses error response body from [CoingeckoApi].
 */
object ErrorUtils {

    fun parseError(response: Response<*>, retrofit: Retrofit): Error? {
        val converter = retrofit.responseBodyConverter<Error>(Error::class.java, arrayOfNulls(0))
        return try {
            converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            Error(status = ErrorStatus(-1, "Can't parse error message"))
        }
    }
}