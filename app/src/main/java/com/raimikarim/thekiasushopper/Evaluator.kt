package com.raimikarim.thekiasushopper

import java.util.Locale

fun evaluatePrices(
        priceA: Double,
        qtyA: Double,
        priceB: Double,
        qtyB: Double
): Triple<String, String, Int> {
    val rateA = priceA / qtyA
    val rateB = priceB / qtyB
    val isRateAHigher: Int = rateA.compareTo(rateB)
    return Triple(rateA.renderString(), rateB.renderString(), isRateAHigher)
}

fun Double.renderString() = doubleToString(this)
fun doubleToString(num: Double): String {
    val numStr = num.toString()

    return if (numStr.length - (numStr.indexOf(".") + 1) > 5) {
        String.format(Locale.ENGLISH, "%.5f", num)
    } else {
        numStr
    }
}
