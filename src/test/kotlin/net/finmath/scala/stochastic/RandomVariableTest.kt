package net.finmath.scala.stochastic

import net.finmath.functions.AnalyticFormulas
import net.finmath.kotlin.stochastic.*
import net.finmath.montecarlo.BrownianMotionFromRandomNumberGenerator
import net.finmath.montecarlo.RandomVariableFromDoubleArray
import net.finmath.randomnumbers.MersenneTwister
import net.finmath.time.TimeDiscretizationFromArray
import org.junit.Assert
import org.junit.Test

/**
 * Unit test illustrating valuation under a Monte-Carlo Black-Scholes model.
 */
class RandomVariableTest {

    @Test
    fun testBasicOperators(): Unit
    {
        var tolerance = 1E-14
        var initialValue = 4.0

        var rv = RandomVariableFromDoubleArray(0.0, initialValue);
        Assert.assertEquals("contruction", initialValue, rv.average.toDouble(), tolerance)

        var sum = rv + 5.0;
        Assert.assertEquals("add number", initialValue+5.0, sum.average.toDouble(), tolerance)

        var half = sum / 2.0;
        Assert.assertEquals("div number", (initialValue+5.0)/2.0, half.average.toDouble(), tolerance)

        var two = sum / half;
        Assert.assertEquals("div randomvariable", 2.0, two.average.toDouble(), tolerance)

        var four = two + two;
        Assert.assertEquals("add randomvariable", 4.0, four.average.toDouble(), tolerance)

        var twelve = four * 3.0
        Assert.assertEquals("mult number", 12.0, twelve.average.toDouble(), tolerance)

        var seventeen = 5 + twelve;
        Assert.assertEquals("add left number", 17.0, seventeen.average.toDouble(), tolerance)

        var one = 18 - seventeen
        Assert.assertEquals("sub left number", 1.0, one.average.toDouble(), tolerance)
    }
}
