package com.emlett.aoc

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

fun main() {
    val regex = Regex("""(\d+),(\d+) -> (\d+),(\d+)""")
    val input = readAsLines("Day05.txt")
        .mapNotNull(regex::matchEntire)
        .map { it.groupValues.drop(1).map(String::toInt) }
        .map { Line(Coordinate(it[0], it[1]), Coordinate(it[2], it[3])) }

    val part1 = input
        .filterNot(Line::isDiagonal)
        .flatMap(Line::points)
        .groupBy { it }
        .count { it.value.size >= 2 }

    val part2 = input
        .flatMap(Line::points)
        .groupBy { it }
        .count { it.value.size >= 2 }

    println("Part 1: $part1")
    println("Part 2: $part2")
}

data class Coordinate(val x: Int, val y: Int)

data class Line(val a: Coordinate, val b: Coordinate) {
    fun isDiagonal(): Boolean = !(a.x == b.x || a.y == b.y)

    fun points(): List<Coordinate> {
        val dx = (b.x - a.x).sign
        val dy = (b.y - a.y).sign
        val steps = max(abs(a.x - b.x), abs(a.y - b.y))

        return List(steps + 1) { Coordinate(a.x + (dx * it), a.y + (dy * it)) }
    }
}
