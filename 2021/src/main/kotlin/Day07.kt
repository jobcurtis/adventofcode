package com.emlett.aoc

import kotlin.math.abs
import kotlin.math.roundToInt

fun main() {
    val input = readAsString("Day07.txt").trim().split(',').map(String::toInt)

    val part1 = run {
        val median = input.median()
        input.sumOf { abs(it - median) }
    }

    val part2 = run {
        val mean = input.average().roundToInt()
        val area = (mean - 10) .. (mean + 10)
        area.toList().minOf { i -> input.sumOf { sumOfIntegers(abs(it - i)) } }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}

fun sumOfIntegers(n: Int) = n * (n + 1) / 2
