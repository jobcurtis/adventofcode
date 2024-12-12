package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day12 : Year2024() {
    val regions by lazy { text.parseGrid().findRegions() }

    override fun part1() = regions.sumOf { region -> region.area * region.perimeter }
    override fun part2() = regions.sumOf { region -> region.area * region.sides }

    fun Map<Point2D, Char>.findRegions(): Collection<Set<Point2D>> {
        val visited = mutableSetOf<Point2D>()
        val regions = mutableSetOf<Set<Point2D>>()

        fun visit(cell: Point2D, region: Set<Point2D>): Set<Point2D> =
            if (!visited.add(cell)) region else cell.adjacentPoints
                .filter { pt -> this[pt] == this[cell] }
                .flatMap { visit(it, region + cell) }
                .toSet() + cell

        return keys.map { visit(it, emptySet()) }.filterNot { it.isEmpty() }
    }

    val Set<Point2D>.area get() = size
    val Set<Point2D>.perimeter get() = sumOf { pt -> pt.adjacentPoints.count { it !in this } }
    val Set<Point2D>.sides get() = this.sumOf { pt -> this.cornersAt(pt) }

    fun Set<Point2D>.cornersAt(pt: Point2D) = Direction.entries.map { it to it.clockwise }.count { (a, b) ->
        when {
            pt + a !in this && pt + b !in this -> true
            pt + a in this && pt + b in this && pt + a + b !in this -> true
            else -> false
        }
    }
}
