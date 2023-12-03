package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.splitBy

object Day03 : Year2023() {
    private val nums by lazy {
        grid
            .toList()
            .splitBy { (_, char) -> !char.isDigit() }
            .map { pairs -> pairs.joinToString("") { it.second.toString() }.toInt() to pairs.map { it.first }.toSet() }
    }

    private val partNums by lazy { nums.filter { it.isPartNumberIn(grid) } }

    override fun part1() = partNums.sumOf { it.first }
    override fun part2() = partNums
        .mapNotNull { it.adjacentGearsIn(grid)?.let { gears -> it.first to gears } }
        .flatMap { it.second.map { pt -> pt to it.first } }
        .groupBy({ it.first }, { it.second })
        .filterValues { it.size == 2 }
        .map { (_, v) -> v.reduce(Int::times) }
        .sum()

    private fun Pair<Int, Set<Point2D>>.isPartNumberIn(grid: Map<Point2D, Char>): Boolean = let { (_, points) ->
        (points.flatMap(Point2D::adjacentPointsDiagonal) - points).any { grid.getOrDefault(it, '.') != '.' }
    }

    private fun Pair<Int, Set<Point2D>>.adjacentGearsIn(grid: Map<Point2D, Char>): Set<Point2D>? = let { (_, points) ->
        (points.flatMap(Point2D::adjacentPointsDiagonal) - points)
            .filter { grid[it] == '*' }
            .toSet()
            .takeUnless(Set<Point2D>::isEmpty)
    }
}
