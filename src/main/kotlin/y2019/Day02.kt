package com.emlett.aoc.y2019

import com.emlett.aoc.y2019.intcode.IntCodeComputer

object Day02 : Year2019() {
    private const val target = 19690720

    private val intArray = text.split(',').map(String::toInt).toIntArray()
    private val ints get() = intArray.copyOf()

    override fun part1() = ints.also { it[1] = 12 }.also { it[2] = 2 }.let(::IntCodeComputer).eval()

    override fun part2() = generateSequence(0 to 0) { (n, v) -> if (v < 99) n to (v + 1) else (n + 1) to 0 }
        .first { (n, v) -> ints.also { it[1] = n }.also { it[2] = v }.let(::IntCodeComputer).eval() == target }
        .let { (n, v) -> 100 * n + v }

}