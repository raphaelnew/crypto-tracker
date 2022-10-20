package com.crypto.tracker.data.remote

import com.crypto.tracker.model.CoinPrices
import com.crypto.tracker.model.Result
import com.crypto.tracker.network.CoingeckoApi
import com.crypto.tracker.util.ErrorUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

/**
 * Fetches data from remote source [CoingeckoApi].
 */
class PricesRemoteDataSource(private val api: CoingeckoApi, private val retrofit: Retrofit) {

    fun fetchPrices(
        coinIds: String,
        currencies: String,
        include24hrChange: Boolean = false,
        precision: String = "4"
    ): Flow<Result<CoinPrices>> {
        return flow {
            emit(Result.loading())
            val result = getPriceResponse(
                request = {
                    api.getPrice(
                        ids = coinIds,
                        currencies = currencies,
                        include24hrChange = include24hrChange,
                        precision = precision
                    )
                }
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun getPriceResponse(
        request: suspend () -> Response<Map<String, Map<String, String>>>
    ): Result<CoinPrices> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                val rawValues = result.body()
                return rawValues?.let { Result.success(CoinPrices(rawValues)) }
                    ?: Result.success(null)
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(
                    errorResponse?.status?.errorMessage ?: "Error fetching price",
                    errorResponse
                )
            }
        } catch (e: Throwable) {
            Timber.e(e)
            Result.error("Unknown Error", null)
        }
    }
}