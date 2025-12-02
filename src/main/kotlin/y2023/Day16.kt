package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Direction.*
import com.emlett.aoc.utils.geometry.Point2D

object Day16 : Year2023() {
  private val grid by lazy { lines.flatMapIndexed { y, r -> r.mapIndexed { x, c -> Point2D(x, -y) to c } }.toMap() }

  private val edges by lazy {
    val (xMin, xMax) = 0 to grid.keys.maxOf(Point2D::x)
    val (yMin, yMax) = grid.keys.minOf(Point2D::y) to 0

    return@lazy listOf(
      (xMin..xMax).map { x -> Point2D(x, yMin) to NORTH },
      (yMin..yMax).map { y -> Point2D(xMin, y) to EAST },
      (xMin..xMax).map { x -> Point2D(x, yMax) to SOUTH },
      (yMin..yMax).map { y -> Point2D(xMax, y) to WEST },
    ).flatten()
  }

  override fun part1() = energized(Point2D(0, 0) to EAST, grid).size
  override fun part2() = edges.maxOf { energized(it, grid).size }

  private fun energized(start: Pair<Point2D, Direction>, grid: Map<Point2D, Char>): Set<Point2D> {
    val queue = ArrayDeque<Pair<Point2D, Direction>>().apply { add(start) }
    val visited = mutableSetOf<Pair<Point2D, Direction>>()

    while (queue.isNotEmpty()) {
      val current = queue.removeFirst().takeUnless { it in visited } ?: continue
      val char = grid[current.first] ?: continue
      visited.add(current)
      nextDirection(char, current.second).forEach { queue.addLast(current.first + it to it) }
    }

    return visited.map { it.first }.toSet()
  }

  private fun nextDirection(char: Char, direction: Direction): Set<Direction> = when {
    char == '.' -> setOf(direction)
    char == '|' && (direction == NORTH || direction == SOUTH) -> setOf(direction)
    char == '|' && (direction == EAST || direction == WEST) -> setOf(NORTH, SOUTH)
    char == '-' && (direction == EAST || direction == WEST) -> setOf(direction)
    char == '-' && (direction == NORTH || direction == SOUTH) -> setOf(EAST, WEST)
    char == '/' && direction == NORTH -> setOf(EAST)
    char == '/' && direction == SOUTH -> setOf(WEST)
    char == '/' && direction == EAST -> setOf(NORTH)
    char == '/' && direction == WEST -> setOf(SOUTH)
    char == '\\' && direction == NORTH -> setOf(WEST)
    char == '\\' && direction == SOUTH -> setOf(EAST)
    char == '\\' && direction == EAST -> setOf(SOUTH)
    char == '\\' && direction == WEST -> setOf(NORTH)
    else -> throw IllegalArgumentException("char: $char, direction: $direction")
  }
}
