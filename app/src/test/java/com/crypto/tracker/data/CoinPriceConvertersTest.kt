package com.crypto.tracker.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test


class CoinPriceConvertersTest {

    private lateinit var coinPriceConverters: CoinPriceConverters

    @Before
    fun setup() {
        coinPriceConverters = CoinPriceConverters()
    }

    @Test
    fun coinPriceConverters_mapToString() {
        var result = coinPriceConverters.mapToString(FAKE_SIMPLE_MAP)
        assertEquals(result, FAKE_SIMPLE_STRING)

        result = coinPriceConverters.mapToString(FAKE_SIMPLE_MAP)
        assertNotEquals(result, FAKE_COMPLEX_STRING)

        result = coinPriceConverters.mapToString(FAKE_COMPLEX_MAP)
        assertEquals(result, FAKE_COMPLEX_STRING)

        result = coinPriceConverters.mapToString(FAKE_COMPLEX_MAP)
        assertNotEquals(result, FAKE_SIMPLE_MAP)
    }

    @Test
    fun coinPriceConverters_stringToMap() {
        var result = coinPriceConverters.stringToMap(FAKE_SIMPLE_STRING)
        assertEquals(result, FAKE_SIMPLE_MAP)

        result = coinPriceConverters.stringToMap(FAKE_SIMPLE_STRING)
        assertNotEquals(result, FAKE_COMPLEX_MAP)

        result = coinPriceConverters.stringToMap(FAKE_COMPLEX_STRING)
        assertEquals(result, FAKE_COMPLEX_MAP)

        result = coinPriceConverters.stringToMap(FAKE_COMPLEX_STRING)
        assertNotEquals(result, FAKE_SIMPLE_MAP)
    }

    companion object {
        private const val FAKE_SIMPLE_STRING = "usd,19305.3459"
        private val FAKE_SIMPLE_MAP = mapOf("usd" to "19305.3459")

        private const val FAKE_COMPLEX_STRING = "usd,19305.3459;usd_24h_vol,1318549338.4565575"
        private val FAKE_COMPLEX_MAP =
            mapOf("usd" to "19305.3459", "usd_24h_vol" to "1318549338.4565575")
    }
}