package com.crypto.tracker.data

import androidx.room.TypeConverter

/**
 * Converts Map<String,String> to and from String.
 */
class CoinPriceConverters {

    @TypeConverter
    fun mapToString(values: Map<String, String>): String {
        val stringBuilder = StringBuilder()
        values.forEach { values ->
            stringBuilder
                .append(values.key)
                .append(",")
                .append(values.value)
                .append(";")
        }
        if (stringBuilder.isNotEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1);
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    fun stringToMap(value: String): Map<String, String> {
        val map = mutableMapOf<String, String>()
        value.split(";").forEach { values ->
            val keyValue = values.split(",")
            if (keyValue.size > 1) {
                map[keyValue[0]] = keyValue[1]
            }
        }
        return map
    }
}