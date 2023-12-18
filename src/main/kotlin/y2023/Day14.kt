package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Direction.*
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.pt
import kotlin.math.abs

object Day14 : Year2023() {
    private val platform by lazy { lines.flatMapIndexed { y, r -> r.mapIndexed { x, c -> pt(x, -y) to c } }.toMap() }

    override fun part1() = platform
        .toList()
        .fold(platform) { acc, (pt, c) -> if (c != 'O') acc else acc + (pt to '.') + (shift(pt, acc, NORTH) to 'O') }
        .map { (pt, c) -> if (c == 'O') lines.size - abs(pt.y) else 0 }
        .sum()

    override fun part2(): Any {
        val states = mutableMapOf<Map<Point2D, Char>, Int>()
        var current = platform

        for (i in 0..1_000_000_000) {
            val cycleStart = states.put(current, i)
            if (cycleStart != null) {
                val cycleLength = i - cycleStart
                val remainingCycles = (1_000_000_000 - cycleStart) % cycleLength
                val targetState = states.entries.first { it.value == remainingCycles + cycleStart }.key
                current = targetState
                break
            }
            current = cycle(current)
        }

        return current.map { (pt, c) -> if (c == 'O') lines.size - abs(pt.y) else 0 }.sum()
    }

    private fun cycle(platform: Map<Point2D, Char>): Map<Point2D, Char> {
        return listOf(NORTH, WEST, SOUTH, EAST).fold(platform, ::tilt)
    }

    private fun tilt(platform: Map<Point2D, Char>, direction: Direction): Map<Point2D, Char> {
        val rocks = platform.filterValues('O'::equals).keys.sortedWith(
            when (direction) {
                NORTH -> compareByDescending(Point2D::y)
                EAST -> compareByDescending(Point2D::x)
                SOUTH -> compareBy(Point2D::y)
                WEST -> compareBy(Point2D::x)
            }
        )

        return rocks.fold(platform) { acc, pt -> acc + (pt to '.') + (shift(pt, acc, direction) to 'O') }
    }

    private tailrec fun shift(rock: Point2D, platform: Map<Point2D, Char>, direction: Direction): Point2D =
        if (platform[rock + direction] != '.') rock else shift(rock + direction, platform, direction)
}
