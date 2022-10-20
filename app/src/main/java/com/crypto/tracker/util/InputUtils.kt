package com.crypto.tracker.util

/**
 * Utils for validation of user input.
 */
object InputUtils {

    fun validatePriceInput(stringToValidate: String, previousValue: String): String {
        if (stringToValidate.isEmpty()) {
            return ""
        }

        return stringToValidate.toDoubleOrNull()?.let { double ->
            stringToValidate.trim().replace("-", "")
        } ?: previousValue
    }

    fun isMinInputValid(stringToValidate: String, maxValue: String): Boolean {
        val minDouble = stringToValidate.toDoubleOrNull() ?: return false
        val maxDouble = maxValue.toDoubleOrNull() ?: return false
        if (minDouble < 0.0 || maxDouble < 0.0) {
            return false
        }

        if (minDouble >= maxDouble) {
            return false
        }
        return true
    }

    fun isMaxInputValid(stringToValidate: String, minValue: String): Boolean {
        val maxDouble = stringToValidate.toDoubleOrNull() ?: return false
        val minDouble = minValue.toDoubleOrNull() ?: return false
        if (minDouble < 0.0 || maxDouble < 0.0) {
            return false
        }

        if (maxDouble <= minDouble) {
            return false
        }
        return true
    }
}