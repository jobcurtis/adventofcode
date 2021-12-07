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
        area.toList().minOf { i -> input.sumOf { abs(it - i).let { it * (it + 1) / 2 } } }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}

fun List<Int>.median(): Int = sorted().run {
    if (size % 2 == 0) (this[size / 2] + this[(size / 2) - 1]) / 2
    else this[size / 2]
}
