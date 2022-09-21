package com.emlett.aoc.y2019

import com.emlett.aoc.y2019.intcode.IntCodeComputer
import kotlinx.coroutines.CoroutineScope

object Day02 : Year2019() {
    private const val target = 19690720L

    private val longArray = text.split(',').map(String::toLong).toLongArray()
    private val ints get() = longArray.copyOf()

    override suspend fun CoroutineScope.part1() = evaluate(ints.also { it[1] = 12 }.also { it[2] = 2 })

    override suspend fun CoroutineScope.part2() =
        generateSequence(0L to 0L) { (n, v) -> if (v < 99) n to (v + 1) else (n + 1) to 0 }
            .first { (n, v) -> ints.also { it[1] = n }.also { it[2] = v }.let { evaluate(it) } == target }
            .let { (n, v) -> 100 * n + v }

    private suspend fun evaluate(memory: LongArray): Long = IntCodeComputer(memory).also { it.eval() }.memory[0]

}