package com.raimikarim.thekiasushopper

import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Test

class EvaluatorTest {
    @Test
    fun `rateA is higher`() {
        val (_, _, rateAHigher) = evaluatePrices(50.0, 1.0, 2.0, 1.5)
        Assert.assertThat(rateAHigher, CoreMatchers.`is`(IsEqual.equalTo(1)))
    }
    @Test
    fun `rateB is higher`() {
        val (_, _, rateAHigher) = evaluatePrices(50.0, 2.0, 100.0, 1.5)
        Assert.assertThat(rateAHigher, CoreMatchers.`is`(IsEqual.equalTo(-1)))
    }
    @Test
    fun `same rate`() {
        val (_, _, rateAHigher) = evaluatePrices(50.0, 2.0, 100.0, 4.0)
        Assert.assertThat(rateAHigher, CoreMatchers.`is`(IsEqual.equalTo(0)))
    }
}