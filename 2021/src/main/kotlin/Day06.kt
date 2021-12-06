package com.emlett.aoc

fun main() {
    val fishMap = readAsString("Day06.txt")
        .trim().split(',').map(String::toInt)
        .groupBy { it }
        .map { (k, v) -> k to v.size.toLong() }
        .toMap()

    println("Part 1: ${fishMap.cycle(80).values.sum()}")
    println("Part 2: ${fishMap.cycle(256).values.sum()}")
}

fun Map<Int, Long>.cycle(times: Int) = (0 until times).fold(this) { result, _ ->
    val tmp = mutableMapOf<Int, Long>()
    for (i in 8 downTo 0) {
        tmp[i - 1] = result[i] ?: 0
    }
    val newGen = tmp.remove(-1) ?: 0
    tmp[6] = tmp.getOrDefault(6, 0).plus(newGen)
    tmp[8] = tmp.getOrDefault(8, 0).plus(newGen)
    return@fold tmp
}
