@file:Suppress("SpellCheckingInspection")

package com.emlett.aoc

private const val max_height = 9

fun main() {
    val map: Map<Point, Int> = readAsLines("Day09.txt")
        .map { line -> line.chunked(1) { it.toString().toInt() } }
        .flatMapIndexed { i, row -> row.mapIndexed { j, height -> (i to j) to height } }
        .associate { (point, height) -> point to height }

    val lowpoints: Set<Point> =
        map.entries.fold(emptySet()) { points, (point, height) ->
            point.adjacentPoints
                .mapNotNull { adjacentPoint -> map[adjacentPoint] }
                .all { adjacentHeight -> adjacentHeight > height }
                .let { lowest -> if (lowest) points + point else points }
        }

    lowpoints
        .mapNotNull(map::get)
        .sumOf { it + 1 }
        .also { println("Part 1: $it") }

    lowpoints
        .map { point -> map.basin(point).size }
        .sortedDescending()
        .take(3)
        .reduce(Int::times)
        .also { println("Part 2: $it") }
}

fun Map<Point, Int>.basin(currentPoint: Point): Set<Point> =
    currentPoint.adjacentPoints
        .filter { adjacentPoint -> this[adjacentPoint]?.let { it < max_height } ?: false }
        .fold(setOf(currentPoint)) { points, adjacentPoint ->
            if (getValue(adjacentPoint) <= getValue(currentPoint)) points
            else points + basin(adjacentPoint)
        }

val Point.adjacentPoints: Set<Point>
    get() = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        .map { diff -> this + diff }
        .toSet()
