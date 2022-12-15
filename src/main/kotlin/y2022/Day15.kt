package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.reduce
import com.emlett.aoc.utils.size
import kotlin.math.abs
import kotlin.math.min

object Day15 : Year2022() {
    private val regex = Regex("""^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)$""")

    private val input = lines
        .mapNotNull(regex::matchEntire)
        .map(MatchResult::destructured)
        .map { (sx, sy, bx, by) -> Point2D(sx, sy) to Point2D(bx, by) }
        .sortedBy { (sensor, beacon) -> min(sensor.x, beacon.x) }

    private const val level = 2_000_000

    private val beaconsAtLevel by lazy { input.map { it.second }.distinctBy { it.x }.count { it.y == level } }

    override fun part1() = input.map { it.coversAtY(level) }.reduce().sumOf { it.size } - beaconsAtLevel

    // too slow :(
    override fun part2() = generateSequence(0, 1::plus)
        .map { y -> y to input.map { it.coversAtY(y) }.reduce() }
        .first { (_, covered) -> covered.size >= 2 }
        .let { (y, lists) -> lists.minOf { it.last + 1 } * 4_000_000L + y }

    private fun Pair<Point2D, Point2D>.coversAtY(y: Int): IntRange = let { (sensor, beacon) ->
        val distance = sensor.manhattanDistanceTo(beacon)
        val diff = abs(sensor.y - y)
        if (diff > distance) IntRange.EMPTY else sensor.x - distance + diff..sensor.x + distance - diff
    }
}
