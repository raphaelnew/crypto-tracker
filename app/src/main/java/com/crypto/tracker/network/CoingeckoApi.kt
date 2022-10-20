package com.crypto.tracker.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Coingecko REST API
 */
@SuppressWarnings("unused")
interface CoingeckoApi {

    @GET(SIMPLE_PRICE)
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies") currencies: String,
        @Query("include_market_cap") includeMarketCap: Boolean = false,
        @Query("include_24hr_vol") include24hrVol: Boolean = false,
        @Query("include_24hr_change") include24hrChange: Boolean = false,
        @Query("include_last_updated_at") includeLastUpdatedAt: Boolean = false,
        @Query("precision") precision: String = "full"
    ): Response<Map<String, Map<String, String>>>

    companion object {
        private const val SIMPLE_PRICE = "simple/price"
    }
}