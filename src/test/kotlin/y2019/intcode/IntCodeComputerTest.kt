package com.emlett.aoc.y2019.intcode

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class IntCodeComputerTest : FunSpec() {
    companion object {
        private suspend fun eval(ints: LongArray, input: LongArray = longArrayOf()) =
            IntCodeComputer(ints, IntCodeIO.ArrayIO(input)).also { it.eval() }
    }

    private fun IntCodeComputer.output() = (io as IntCodeIO.ArrayIO).output

    init {
        withData(
            nameFn = { "Testing memory with input ${it.first.toList()}" },
            longArrayOf(1, 0, 0, 0, 99) to longArrayOf(2, 0, 0, 0, 99),
            longArrayOf(2, 3, 0, 3, 99) to longArrayOf(2, 3, 0, 6, 99),
            longArrayOf(2, 4, 4, 5, 99, 0) to longArrayOf(2, 4, 4, 5, 99, 9801),
            longArrayOf(1, 1, 1, 4, 99, 5, 6, 0, 99) to longArrayOf(30, 1, 1, 4, 2, 5, 6, 0, 99),
            longArrayOf(1002, 4, 3, 4, 33) to longArrayOf(1002, 4, 3, 4, 99),
        ) { (expected, actual) ->
            eval(expected).memory.memory shouldBe actual
        }

        test("3,9,8,9,10,9,4,9,99,-1,8") {
            eval(longArrayOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8), longArrayOf(8)).output() shouldBe longArrayOf(1)
        }

        test("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99") {
            val program = longArrayOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)
            eval(program).output() shouldBe program
        }

        test("1102,34915192,34915192,7,4,7,99,0") {
            val program = longArrayOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0)
            eval(program).output().first().toString().length shouldBe 16
        }

        test("104,1125899906842624,99") {
            eval(longArrayOf(104, 1125899906842624, 99)).output().first() shouldBe 1125899906842624
        }
    }
}