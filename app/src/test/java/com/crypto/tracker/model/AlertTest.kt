package com.crypto.tracker.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class AlertTest {

    @Test
    fun alert_isPriceLowerThanRange() {
        var alert = Alert(
            min = Double.MIN_VALUE,
            max = Double.MAX_VALUE,
            coinId = "bitcoin",
            coinPrice = CoinPrice(
                mapOf("usd" to "13.500")
            )
        )
        assertFalse(alert.isPriceLowerThanRange)

        alert = Alert(
            min = 0.0,
            max = Double.MAX_VALUE,
            coinId = "bitcoin",
            coinPrice = CoinPrice(
                mapOf("usd" to "13.500")
            )
        )
        assertFalse(alert.isPriceLowerThanRange)

        alert = Alert(
            min = 10.0,
            max = Double.MAX_VALUE,
            coinId = "bitcoin",
            coinPrice = CoinPrice(
                mapOf("usd" to "13.500")
            )
        )
        assertFalse(alert.isPriceLowerThanRange)

        alert = Alert(
            min = 15000.0,
            max = Double.MAX_VALUE,
            coinId = "bitcoin",
            coinPrice = CoinPrice(
                mapOf("usd" to "13.500")
            )
        )
        assertTrue(alert.isPriceLowerThanRange)

        alert = Alert(
            min = 500000.0,
            max = Double.MAX_VALUE,
            coinId = "bitcoin",
            coinPrice = CoinPrice(
                mapOf("usd" to "13.500")
            )
        )
        assertTrue(alert.isPriceLowerThanRange)
    }

    @Test
    fun alert_isPriceOutOfRange() {
        var alert = Alert(min = Double.MIN_VALUE, max = Double.MAX_VALUE, coinId = "bitcoin")
        assertFalse(alert.isPriceOutOfRange(10.0))

        alert = Alert(min = 0.0, max = Double.MAX_VALUE, coinId = "bitcoin")
        assertFalse(alert.isPriceOutOfRange(10.0))


        alert = Alert(min = 50.0, max = 100.0, coinId = "bitcoin")
        assertTrue(alert.isPriceOutOfRange(10.0))

        alert = Alert(min = 50.0, max = 100.0, coinId = "bitcoin")
        assertTrue(alert.isPriceOutOfRange(150.0))
    }

}