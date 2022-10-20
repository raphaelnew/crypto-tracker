package com.crypto.tracker.model

import androidx.room.Ignore
import java.text.NumberFormat
import java.util.*

/**
 * Data model class contains information about Coin price.
 */
@SuppressWarnings("unused")
data class CoinPrice(
    val rawFields: Map<String, String>
) {
    @Ignore
    val lastUpdatedAt: Long? = rawFields["last_updated_at"]?.toLongOrNull()

    fun getPrice(currency: String): Double =
        checkNotNull(getPriceOrNull(currency))

    fun getPriceOrNull(currency: String): Double? =
        rawFields[currency.lowercase()]?.toDoubleOrNull()

    fun getPriceFormatted(currency: String): String? {
        // Proper show price formatted (ex. instead of 19170.16 it will be show
        // in human readable format 19,170.16 etc.)
        return getPriceOrNull(currency)?.let { price ->
            NumberFormat.getNumberInstance(Locale.getDefault()).format(price)
                .toString()
        }
    }

    fun getMarketCap(currency: String): Double =
        checkNotNull(getMarketCapOrNull(currency))

    fun getMarketCapOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_market_cap"]?.toDoubleOrNull()

    fun get24hrVol(currency: String): Double =
        checkNotNull(get24hrVolOrNull(currency))

    fun get24hrVolOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_24h_vol"]?.toDoubleOrNull()

    fun get24hrChange(currency: String): Double =
        checkNotNull(get24hrChangeOrNull(currency))

    fun get24hrChangeOrNull(currency: String): Double? =
        rawFields["${currency.lowercase()}_24h_change"]?.toDoubleOrNull()

    fun getRawField(key: String): String? = rawFields[key]

    override fun toString(): String = rawFields.toString()
}

data class CoinPrices(
    private val rawFields: Map<String, Map<String, String>>
) {

    fun getCoins(): List<Coin> =
        rawFields.entries.map { Coin(coinId = it.key, coinPrice = CoinPrice(it.value)) }
}

data class Coin(val coinId: String, val coinPrice: CoinPrice?)