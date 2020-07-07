package net.finmath.kotlin.stochastic

import net.finmath.functions.AnalyticFormulas
import net.finmath.kotlin.stochastic.*
import net.finmath.montecarlo.BrownianMotionFromRandomNumberGenerator
import net.finmath.randomnumbers.MersenneTwister
import net.finmath.time.TimeDiscretizationFromArray
import org.junit.Assert
import org.junit.Test

/**
 * Unit test illustrating valuation under a Monte-Carlo Black-Scholes model.
 */
class MonteCarloBlackScholesTest {
    /*
     * Parameters for the Monte-Carlo method.
     */
    private val seed = 3216L
    private val numberOfSamples = 2000000 // 2 * 10^6

    /*
     * Parameters for the product: European option
     */
    private val optionMaturity = 1.0
    private val strike = 105.0

    /*
     * Parameters for the model: Black Scholes model
     */
    private val initialValue = 100.0
    private val riskFreeRate = 0.05
    private val volatility = 0.20

    @Test
    fun testValuation() {
        val value = getOptionValueUsingRandomVariables()

        val valueAnalytic = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFreeRate, volatility, optionMaturity, strike)

        val error = value - valueAnalytic
        val tolerance = initialValue / Math.sqrt(numberOfSamples.toDouble())

        System.out.println("value: " + value + "\t" + "error: " + error)

        Assert.assertEquals("valuation", valueAnalytic, value, tolerance)
    }

    fun getOptionValueUsingRandomVariables(): Double {
        // Uniform random number generator
        val randomNumberGenerator = MersenneTwister(seed)

        val timeDiscretization = TimeDiscretizationFromArray(0.0, optionMaturity)

        // Brownian motion (with a singe time step)
        val brownianMotion = BrownianMotionFromRandomNumberGenerator(
                timeDiscretization, 1, numberOfSamples, randomNumberGenerator)

        // Model
        val drift = riskFreeRate * optionMaturity - 0.5 * volatility * volatility * optionMaturity
        val diffusion = volatility * brownianMotion.getBrownianIncrement(0.0, 0)
        val underlying = initialValue * exp(drift + diffusion)

        // Product
        val payoff = max(underlying - strike, 0.0)

        // Valuation
        val value =
            expectation(payoff * Math.exp(-riskFreeRate * optionMaturity))

        return value.doubleValue()
    }

    fun getOptionValueUsingRandomVariablesJavaStyle(): Double {
        // Uniform random number generator
        val randomNumberGenerator = MersenneTwister(seed)

        val timeDiscretization = TimeDiscretizationFromArray(0.0, optionMaturity)

        // Brownian motion (with a singe time step)
        val brownianMotion = BrownianMotionFromRandomNumberGenerator(
            timeDiscretization, 1, numberOfSamples, randomNumberGenerator)

        // Model
        val drift = riskFreeRate * optionMaturity - 0.5 * volatility * volatility * optionMaturity
        val diffusion = brownianMotion.getBrownianIncrement(0.0, 0).mult(volatility)
        val underlying = diffusion.add(drift).exp().mult(initialValue)

        // Product
        val payoff = underlying.sub(strike).floor(0.0)

        // Valuation
        val value = payoff.mult(Math.exp(-riskFreeRate * optionMaturity)).average()

        return value.doubleValue()
    }
}