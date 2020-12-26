package com.raimikarim.thekiasushopper

import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Test

class EvaluatorTest {
    @Test
    fun rateAishigher() {
        val (rateA, rateB, rateAHigher) = evaluatePrices(50.0, 1.0, 2.0, 1.5)
        Assert.assertThat(rateAHigher, CoreMatchers.`is`(IsEqual.equalTo(1)))
    }
}