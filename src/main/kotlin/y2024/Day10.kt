package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day10 : Year2024() {
    val grid by lazy { text.parseGrid().mapValues { (_, ch) -> ch.digitToInt() } }
    val paths by lazy { grid.filterValues(0::equals).mapValues { (trailhead, _) -> peaks(grid, trailhead) } }

    override fun part1() = paths.values.sumOf { it.toSet().size }
    override fun part2() = paths.values.sumOf { it.size }

    fun peaks(grid: Map<Point2D, Int>, current: Point2D): List<Point2D> = grid.getValue(current).let { height ->
        if (height == 9) listOf(current) else current.adjacentPoints
            .filter { pt -> grid[pt] == height + 1 }
            .flatMap { pt -> peaks(grid, pt) }
    }
}
