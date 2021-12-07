package com.emlett.aoc

fun main() {
    val input = readAsString("Day06.txt").trim().split(',').map(String::toInt)
    val fish = List(9) { index -> input.count(index::equals).toLong() }

    println("Part 1: ${fish.cycle(80).sum()}")
    println("Part 2: ${fish.cycle(256).sum()}")
}

fun List<Long>.cycle(times: Int) = (0 until times)
    .fold(this.toMutableList()) { acc, _ ->
        val next = acc.removeFirst()
        acc[6] += next
        acc.add(next)
        return@fold acc
    }
