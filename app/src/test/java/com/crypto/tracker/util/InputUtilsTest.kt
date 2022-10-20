package com.crypto.tracker.util

import org.junit.Assert.*
import org.junit.Test


class InputUtilsTest {

    @Test
    fun inputUtils_validatePriceInput() {
        var result = InputUtils.validatePriceInput("1", "")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput("-1", "")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput(" 1", "")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput(" -1 ", "")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput(" -1 ", "22")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput("invalid number,", "")
        assertEquals(result, "")

        result = InputUtils.validatePriceInput("1b", "1")
        assertEquals(result, "1")

        result = InputUtils.validatePriceInput("1,2", "")
        assertEquals(result, "")

        result = InputUtils.validatePriceInput("1.22", "")
        assertEquals(result, "1.22")

        result = InputUtils.validatePriceInput("1.22.33", "")
        assertEquals(result, "")

        result = InputUtils.validatePriceInput("null", "")
        assertEquals(result, "")
    }

    @Test
    fun inputUtils_isMinInputValid() {
        assertFalse(InputUtils.isMinInputValid("1", ""))

        assertFalse(InputUtils.isMinInputValid("0", ""))

        assertFalse(InputUtils.isMinInputValid("5.5", ""))

        assertFalse(InputUtils.isMinInputValid("", ""))

        assertTrue(InputUtils.isMinInputValid("1", "5"))

        assertTrue(InputUtils.isMinInputValid("1", "5.5"))

        assertTrue(InputUtils.isMinInputValid("0", "5"))

        assertTrue(InputUtils.isMinInputValid("11.22", "22.33"))

        assertFalse(InputUtils.isMinInputValid("not valid", "5"))

        assertFalse(InputUtils.isMinInputValid("1", "-5"))

        assertFalse(InputUtils.isMinInputValid("-11", "5"))
    }

    @Test
    fun inputUtils_isMaxInputValid() {
        assertFalse(InputUtils.isMaxInputValid("1", ""))

        assertFalse(InputUtils.isMaxInputValid("0", ""))

        assertFalse(InputUtils.isMaxInputValid("5.5", ""))

        assertFalse(InputUtils.isMaxInputValid("", ""))

        assertTrue(InputUtils.isMaxInputValid("5", "1"))

        assertTrue(InputUtils.isMaxInputValid("5.5", "1.1"))

        assertFalse(InputUtils.isMaxInputValid("0", "5"))

        assertFalse(InputUtils.isMaxInputValid("11.22", "22.33"))

        assertFalse(InputUtils.isMaxInputValid("not valid", "5"))

        assertFalse(InputUtils.isMaxInputValid("1", "-5"))

        assertFalse(InputUtils.isMaxInputValid("-11", "5"))
    }
}