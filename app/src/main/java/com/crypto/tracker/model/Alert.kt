package com.crypto.tracker.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crypto.tracker.util.Constants

/**
 * Data model class contains information about Alert and result
 */
@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val min: Double,
    val max: Double,
    val coinId: String? = null,
    @Embedded val coinPrice: CoinPrice? = null
) {

    val isPriceLowerThanRange: Boolean
        get() {
            val price = coinPrice?.getPriceOrNull(Constants.CURRENCIES) ?: return false
            return price < min
        }

    fun isPriceOutOfRange(price: Double): Boolean {
        val range = min..max
        return price !in range
    }
}