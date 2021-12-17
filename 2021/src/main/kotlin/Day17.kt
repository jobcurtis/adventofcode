package com.emlett.aoc

import kotlin.math.abs
import kotlin.math.max

fun main() {
    val regex = Regex("""target area: x=([-\d]+)\.\.([-\d]+), y=([-\d]+)\.\.([-\d]+)""")
    val input = readAsLines("Day17.txt").mapNotNull(regex::matchEntire)
        .flatMap { it.groupValues.drop(1) }
        .map(String::toInt)
        .let { (ax, bx, ay, by) -> ax..bx to ay..by }

    input.second.min()
        .let { minY -> abs(minY) - 1 }
        .let(::sumOfIntegers)
        .also { println("Part 1: $it") }

    val minVX = (1..input.first.min()).first { sumOfIntegers(it) > input.first.min() }
    val minVY = input.second.min()
    val maxVX = input.first.max()
    val maxVY = abs(input.second.min()) - 1

    (minVX..maxVX).flatMap { vx -> (minVY..maxVY).map { vy -> Probe(vx, vy) } }
        .count { it.projection(input).map(Probe::loc).any(input::contains) }
        .also { println("Part 2: $it") }
}

private data class Probe(val vx: Int, val vy: Int, val loc: Point = Point(0, 0))

private val Probe.next get() = Probe(max(0, vx - 1), vy - 1, Point(loc.x + vx, loc.y + vy))

private operator fun Pair<IntRange, IntRange>.contains(point: Point) = point.x in first && point.y in second

private fun Probe.projection(area: Pair<IntRange, IntRange>) = generateSequence(this) {
    it.next.let { next -> if ((next.loc.x !in area.first && next.vx == 0) || next.loc.x > area.first.max() || next.loc.y < area.second.min()) null else next }
}
