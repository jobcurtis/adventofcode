package com.emlett.aoc.y2019

import com.emlett.aoc.y2019.intcode.IntCodeComputer
import com.emlett.aoc.y2019.intcode.IntCodeIO.ArrayIO
import kotlinx.coroutines.CoroutineScope

object Day09 : Year2019() {

    private val longArray = text.split(',').map(String::toLong).toLongArray()
    private suspend fun compute(vararg input: Long) = (IntCodeComputer(longArray, ArrayIO(input))).also { it.eval() }

    override suspend fun CoroutineScope.part1() = compute(1).io.let { it as ArrayIO }.output.first()
    override suspend fun CoroutineScope.part2() = compute(2).io.let { it as ArrayIO }.output.first()
}