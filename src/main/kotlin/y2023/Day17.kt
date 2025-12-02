package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Orientation
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.graphs.ValueGraph
import com.emlett.aoc.utils.graphs.findShortestDistance
import com.emlett.aoc.utils.input.parseGrid

private typealias PointWithHeading = Pair<Point2D, Orientation>

object Day17 : Year2023() {
  private val grid by lazy { text.parseGrid().mapValues { (_, c) -> c.digitToInt() } }

  private val target by lazy { Point2D(grid.keys.maxOf { it.x }, grid.keys.maxOf { it.y }) }
  private val starts = listOf(Point2D.zero to Orientation.VERTICAL, Point2D.zero to Orientation.HORIZONTAL)

  override fun part1() = CrucibleGraph(1..3, grid).findShortestDistance(starts) { (pt, _) -> pt == target }
  override fun part2() = CrucibleGraph(4..10, grid).findShortestDistance(starts) { (pt, _) -> pt == target }

  private class CrucibleGraph(val step: IntRange, val grid: Map<Point2D, Int>) : ValueGraph<PointWithHeading, Int> {
    override fun successors(node: PointWithHeading): List<PointWithHeading> = node.second
      .directions()
      .flatMap { dir -> step.map { dis -> node.first + (dir to dis) to dir.orientation() } }
      .filter { (pt, _) -> pt in grid }

    override fun getValue(a: PointWithHeading, b: PointWithHeading): Int =
      a.first.lineTo(b.first).drop(1).sumOf(grid::getValue)
  }

  private fun Direction.orientation(): Orientation = when (this) {
    Direction.NORTH -> Orientation.VERTICAL
    Direction.EAST -> Orientation.HORIZONTAL
    Direction.SOUTH -> Orientation.VERTICAL
    Direction.WEST -> Orientation.HORIZONTAL
  }

  private fun Orientation.directions(): Set<Direction> = when (this) {
    Orientation.VERTICAL -> setOf(Direction.EAST, Direction.WEST)
    Orientation.HORIZONTAL -> setOf(Direction.NORTH, Direction.SOUTH)
  }
}
