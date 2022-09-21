package com.emlett.aoc.y2019

import com.emlett.aoc.y2019.intcode.IntCodeComputer
import com.emlett.aoc.y2019.intcode.IntCodeIO
import kotlinx.coroutines.CoroutineScope

object Day05 : Year2019() {

    private val longArray = text.split(',').map(String::toLong).toLongArray()
    private suspend fun computer(input: Int) = IntCodeComputer(longArray.copyOf(), input(input)).also { it.eval() }

    override suspend fun CoroutineScope.part1() = computer(1).output().toList().last()
    override suspend fun CoroutineScope.part2() = computer(5).output().toList().last()

    private fun input(int: Int) = IntCodeIO.ArrayIO(input = longArrayOf(int.toLong()))
    private fun IntCodeComputer.output() = (io as IntCodeIO.ArrayIO).output
}