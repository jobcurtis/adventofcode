@file:Suppress("SpellCheckingInspection")

package com.emlett.aoc

fun main() {
    val octopuses: Map<Point, Int> = readAsLines("Day11.txt")
        .map { line -> line.chunked(1) { it.toString().toInt() } }
        .flatMapIndexed { i, row -> row.mapIndexed { j, energy -> (i to j) to energy } }
        .associate { (point, energy) -> point to energy }

    generateSequence(octopuses to 0) { (octopi, flashes) ->
        octopi.cycle().let { (newOctopi, newFlashes) -> newOctopi to flashes + newFlashes }
    }
        .elementAt(100)
        .also { println("Part 1: ${it.second}") }

    generateSequence(octopuses to 0) { (octopodes, _) -> octopodes.cycle() }
        .indexOfFirst { (octopodes, flashes) -> octopodes.size == flashes }
        .also { println("Part 2: $it") }
}

private fun Map<Point, Int>.cycle(): Pair<Map<Point, Int>, Int> {
    val (octopodes, flashed) = generateSequence(mapValues { it.value.inc() } to setOf<Point>()) { (octopi, flashed) ->
        val flashes = octopi.filter { (loc, energy) -> loc !in flashed && energy > 9 }.keys
        when {
            flashes.isEmpty() -> null
            else -> flashes.fold(octopi) { map, loc ->
                loc.adjacentPointsDiagonal.fold(map) { acc, pair ->
                    acc[pair]?.let { acc + (pair to it.inc()) } ?: acc
                }
            } to (flashed + flashes)
        }
    }.last()
    return octopodes.mapValues { (k, v) -> if (k in flashed) 0 else v } to flashed.size
}
