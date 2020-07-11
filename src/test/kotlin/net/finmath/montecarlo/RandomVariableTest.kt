package net.finmath.montecarlo

import net.finmath.functions.AnalyticFormulas
import net.finmath.kotlin.stochastic.*
import net.finmath.montecarlo.BrownianMotionFromRandomNumberGenerator
import net.finmath.montecarlo.RandomVariableFromDoubleArray
import net.finmath.randomnumbers.MersenneTwister
import net.finmath.stochastic.RandomVariable
import net.finmath.time.TimeDiscretizationFromArray
import org.junit.Assert
import org.junit.Test

/**
 * Unit test illustrating valuation under a Monte-Carlo Black-Scholes model.
 */
class RandomVariableTest {

    @Test
    fun testBasicOperators() {
        val tolerance = 1E-14
        val initialValue = 4.0

        val rv = RandomVariableFromDoubleArray(0.0, initialValue)
        Assert.assertEquals("contruction", initialValue, rv.average, tolerance)

        val sum = rv + 5.0
        Assert.assertEquals("add number", initialValue+5.0, sum.average, tolerance)

        val half = sum / 2.0
        Assert.assertEquals("div number", (initialValue+5.0)/2.0, half.average, tolerance)

        val two = sum / half
        Assert.assertEquals("div randomvariable", 2.0, two.average, tolerance)

        val four = two + two
        Assert.assertEquals("add randomvariable", 4.0, four.average, tolerance)

        val twelve = four * 3.0
        Assert.assertEquals("mult number", 12.0, twelve.average, tolerance)

        val seventeen = 5 + twelve
        Assert.assertEquals("add left number", 17.0, seventeen.average, tolerance)

        val one = 18 - seventeen
        Assert.assertEquals("sub left number", 1.0, one.average, tolerance)
    }

    @Test
    fun testRandomVariableDeterministc() {

        // Create a random variable with a constant
        val randomVariable : RandomVariable = RandomVariableFromDoubleArray(2.0)

        // Perform some calculations
        val result = randomVariable
            .mult(2.0)
            .add(1.0)
            .squared()
            .sub(4.0)
            .div(7.0)

        // The random variable has average value 3.0 (it is constant 3.0)
        Assert.assertTrue(result.getAverage() == 3.0)

        // Since the random variable is deterministic, it has zero variance
        Assert.assertTrue(result.getVariance() == 0.0)
    }

    @Test
    fun testRandomVariableStochastic() {

        // Create a stochastic random variable
        var randomVariable2 : RandomVariable = RandomVariableFromDoubleArray(0.0, doubleArrayOf(-4.0, -2.0, 0.0, 2.0, 4.0) );

        // Perform some calculations
        randomVariable2 = randomVariable2.add(4.0);
        randomVariable2 = randomVariable2.div(2.0);

        // The random variable has average value 2.0
        Assert.assertTrue(randomVariable2.getAverage() == 2.0);

        // The random variable has variance value 2.0 = (4 + 1 + 0 + 1 + 4) / 5
        Assert.assertEquals(2.0, randomVariable2.getVariance(), 1E-12);

        // Multiply two random variables, this will expand the receiver to a stochastic one
        var randomVariable : RandomVariable = RandomVariableFromDoubleArray(3.0);
        randomVariable = randomVariable.mult(randomVariable2);

        // The random variable has average value 6.0
        Assert.assertTrue(randomVariable.getAverage() == 6.0);

        // The random variable has variance value 2 * 9
        Assert.assertTrue(randomVariable.getVariance() == 2.0 * 9.0);
    }
}
