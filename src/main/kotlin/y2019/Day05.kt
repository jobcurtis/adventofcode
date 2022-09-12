package com.emlett.aoc.y2019

import com.emlett.aoc.y2019.intcode.IntCodeComputer

object Day05 : Year2019() {

    private val intArray = text.split(',').map(String::toInt).toIntArray()
    private val ints get() = intArray.copyOf()

    override fun part1() = IntCodeComputer(ints, intArrayOf(1)).also(IntCodeComputer::eval).output.toList()
    override fun part2() = IntCodeComputer(ints, intArrayOf(5)).also(IntCodeComputer::eval).output.toList()
}