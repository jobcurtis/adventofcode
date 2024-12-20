package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day20 : Year2024() {
    override fun part1() = cheats(100, 2)
    override fun part2() = cheats(100, 20)

    val path by lazy {
        val grid = text.parseGrid()
        val path = mutableSetOf<Point2D>()
        val end = grid.filterValues('E'::equals).keys.first()
        var current = grid.filterValues('S'::equals).keys.first()

        while (current != end) {
            path.add(current)
            current = current.adjacentPoints.first { it !in path && grid[it] != '#' }
        }

        path.add(end)
        path.toTypedArray()
    }

    fun cheats(minValue: Int, maxDistance: Int): Int {
        var count = 0

        for (i in path.indices) {
            val start = path[i]
            for (j in i + 1 until path.size) {
                val end = path[j]
                val distance = end.manhattanDistanceTo(start)
                if (distance <= maxDistance && j - i - distance >= minValue) count++
            }
        }

        return count
    }
}
