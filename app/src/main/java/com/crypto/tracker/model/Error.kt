package com.crypto.tracker.model

import com.crypto.tracker.network.CoingeckoApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data model class contains information about Error from [CoingeckoApi].
 */
@JsonClass(generateAdapter = true)
data class Error(val status: ErrorStatus)

@JsonClass(generateAdapter = true)
data class ErrorStatus(
    @Json(name = "error_code")
    val errorCode: Int,
    @Json(name = "error_message")
    val errorMessage: String? = null
)