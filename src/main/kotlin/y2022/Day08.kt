package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D

object Day08 : Year2022() {
    private val grid = lines.flatMapIndexed { y, l -> l.mapIndexed { x, c -> Point2D(x, y) to c.digitToInt() } }.toMap()

    override fun part1() = grid.keys.count { it.isVisible() }
    override fun part2() = grid.keys.maxOf { it.calculateScenicScore() }

    private fun Point2D.calculateScenicScore() = Direction.values().map { this.viewDistanceTo(it) }.reduce(Int::times)

    private fun Point2D.viewDistanceTo(direction: Direction): Int {
        val height = grid[this] ?: throw IllegalStateException()
        var distance = 0
        var current = this + direction
        while (current in grid) {
            distance++
            if (grid.getValue(current) >= height) {
                break
            } else {
                current += direction
            }
        }
        return distance
    }

    private fun Point2D.isVisible(): Boolean = Direction.values().any { direction ->
        val height = grid[this] ?: throw IllegalStateException()
        val next = this + direction
        val seq = generateSequence(next.takeIf(grid::contains)) { pt -> (pt + direction).takeIf(grid::contains) }
        seq.none { pt -> grid[pt]?.let { it >= height } ?: false }
    }
}
