package com.emlett.aoc.y2021

import kotlin.math.*

object Day17 : Year2021() {
    private val regex = Regex("""target area: x=([-\d]+)\.\.([-\d]+), y=([-\d]+)\.\.([-\d]+)""")
    private val target = lines.mapNotNull(regex::matchEntire)
        .flatMap { it.groupValues.drop(1) }
        .map(String::toInt)
        .let { (ax, bx, ay, by) -> ax..bx to ay..by }

    override fun part1() = target.second.min().let { minY -> abs(minY) - 1 }.let(::sumOfIntegers)

    override fun part2(): Any {
        val minVX = (1..target.first.min()).first { sumOfIntegers(it) > target.first.min() }
        val minVY = target.second.min()
        val maxVX = target.first.max()
        val maxVY = abs(target.second.min()) - 1

        return (minVX..maxVX)
            .flatMap { vx -> (minVY..maxVY).map { vy -> Probe(vx, vy) } }
            .count { it.projection(target).map(Probe::loc).any { loc -> loc in target } }
    }

    private data class Probe(val vx: Int, val vy: Int, val loc: Point = Point(0, 0))

    private val Probe.next get() = Probe(max(0, vx - 1), vy - 1, Point(loc.x + vx, loc.y + vy))

    private operator fun Pair<IntRange, IntRange>.contains(point: Point) = point.x in first && point.y in second

    private fun Probe.projection(area: Pair<IntRange, IntRange>) = generateSequence(this) {
        it.next.let { next -> if ((next.loc.x !in area.first && next.vx == 0) || next.loc.x > area.first.max() || next.loc.y < area.second.min()) null else next }
    }
}
