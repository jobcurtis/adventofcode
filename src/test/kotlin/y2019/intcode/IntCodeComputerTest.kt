package com.emlett.aoc.y2019.intcode

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class IntCodeComputerTest : FunSpec() {
    companion object {
        private fun eval(ints: IntArray, input: IntArray = intArrayOf()) =
            IntCodeComputer(ints, input).apply(IntCodeComputer::eval)
    }

    init {
        withData(
            nameFn = { "Testing memory with input ${it.first.toList()}" },
            intArrayOf(1, 0, 0, 0, 99) to intArrayOf(2, 0, 0, 0, 99),
            intArrayOf(2, 3, 0, 3, 99) to intArrayOf(2, 3, 0, 6, 99),
            intArrayOf(2, 4, 4, 5, 99, 0) to intArrayOf(2, 4, 4, 5, 99, 9801),
            intArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to intArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99),
            intArrayOf(1002, 4, 3, 4, 33) to intArrayOf(1002, 4, 3, 4, 99)
        ) { (expected, actual) ->
            eval(expected).memory shouldBe actual
        }

        test("3,9,8,9,10,9,4,9,99,-1,8") {
            eval(intArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8), intArrayOf(8)).output shouldBe intArrayOf(1)
        }
    }

}