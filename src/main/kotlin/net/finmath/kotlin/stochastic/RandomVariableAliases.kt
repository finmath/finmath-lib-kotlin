package net.finmath.kotlin.stochastic

import net.finmath.montecarlo.RandomVariableFromDoubleArray
import net.finmath.stochastic.RandomVariable

class RandomVariableAliases {
}

/**
 * @param value The value to add.
 */
operator fun RandomVariable.plus(value : Double) = this.add(value)

operator fun RandomVariable.minus(value : Double) = this.sub(value)

operator fun RandomVariable.times(value : Double) = this.mult(value)

operator fun Number.plus(value: RandomVariable): RandomVariable = value.add(this.toDouble())
operator fun Number.minus(value: RandomVariable): RandomVariable = value.bus(this.toDouble())
operator fun Number.times(value: RandomVariable): RandomVariable = value.mult(this.toDouble())
operator fun Number.div(value: RandomVariable): RandomVariable = value.vid(this.toDouble())

operator fun RandomVariable.plus(value : RandomVariable) = this.add(value)
operator fun RandomVariable.minus(value : RandomVariable) = this.sub(value)
operator fun RandomVariable.times(value : RandomVariable) = this.mult(value)

fun exp(value: RandomVariable): RandomVariable = value.exp()
fun log(value: RandomVariable): RandomVariable = value.log()
fun squared(value: RandomVariable): RandomVariable = value.squared()
fun sqrt(value: RandomVariable): RandomVariable = value.sqrt()
fun max(value: RandomVariable, value2 : Double): RandomVariable = value.floor(value2)
fun expectation(value: RandomVariable): RandomVariable = value.average()
fun variance(value: RandomVariable) : RandomVariable = value.sub(value.average()).squared().average()
fun covariance(x: RandomVariable, y: RandomVariable): RandomVariable = x.covariance(y)
