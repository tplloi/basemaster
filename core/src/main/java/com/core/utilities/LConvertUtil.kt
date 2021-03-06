package com.core.utilities

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import kotlin.math.roundToInt

class LConvertUtil {
    companion object {

        fun convertToPrice(price: BigDecimal?): String {
            price?.let {
                val symbols = DecimalFormatSymbols(Locale.US)
                val formatter = DecimalFormat("#,###", symbols)

                var priceString = formatter.format(roundBigDecimal(price))
                if (priceString == "-0" || priceString == "-," || priceString == ",") {
                    priceString = "0"
                }
                return priceString
            }

            return ""
        }

        fun convertNumberToStringFormat(value: Number?): String {
            value?.let {

                val splitValue = value.toString().split(".")
                var firstValue: BigDecimal? = null
                var secondValue: BigDecimal? = null
                if (splitValue.size > 1) {
                    firstValue = splitValue.first().toBigDecimalOrNull()
                    secondValue = splitValue.last().toBigDecimalOrNull()
                } else {
                    firstValue = splitValue.first().toBigDecimalOrNull()
                }

                return if (secondValue != null) {
                    "${convertToPrice(firstValue)}.$secondValue"
                } else {
                    convertToPrice(firstValue)
                }
            }

            return ""
        }

        fun convertNumberToString(value: Number?): String {
            value?.let {
                val splitValue = value.toString().split(".")
                if (splitValue.lastOrNull()?.toIntOrNull() == 0) {
                    return splitValue.firstOrNull() ?: ""
                }

                return value.toString()
            }

            return ""
        }

        fun convertNumberToPercent(value: Number?): String {
            value?.let {
                val result = (it.toFloat() * 1000).roundToInt().toFloat() / 1000
                return convertNumberToString(result)
            }

            return ""
        }

        // round
        fun roundBigDecimal(value: BigDecimal, newScale: Int = 0): BigDecimal {
            return value.setScale(newScale, BigDecimal.ROUND_HALF_UP)
        }
    }
}