package com.emlett.aoc

fun main() {
    val input = readAsString("Day06.txt").trim().split(',').map(String::toInt)
    val fish = MutableList(9) { index -> input.count(index::equals).toLong() }

    println("Part 1: ${fish.cycle(80).sum()}")
    println("Part 2: ${fish.cycle(256).sum()}")
}

fun MutableList<Long>.cycle(times: Int) = (0 until times).fold(this) { acc, _ ->
        acc.apply { removeFirst().also { acc[6] += it }.also { acc += it } }
    }
