package com.emlett.aoc.y2025

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day07 : Year2025() {
  val input = text.parseGrid(invertY = false)
  val start = input.filterValues { it == 'S' }.keys.first()

  override fun part1() = context(input.toMutableMap()) { simulate(start) }
  override fun part2() = context(mutableMapOf<Point2D, Long>(), input) { simulate2(start) }

  context(grid: MutableMap<Point2D, Char>)
  fun simulate(location: Point2D): Int {
    val next = location.copy(y = location.y + 1)
    return when (grid[next]) {
      '.' -> simulate(next).also { grid[next] = '|' }
      '^' -> 1 + simulate(next.copy(x = next.x - 1)) + simulate(next.copy(x = next.x + 1))
      else -> 0
    }
  }

  context(cache: MutableMap<Point2D, Long>, grid: Map<Point2D, Char>)
  fun simulate2(location: Point2D): Long = cache.getOrPut(location) {
    val next = location.copy(y = location.y + 1)
    return@getOrPut when (grid[next]) {
      '.' -> simulate2(next)
      '^' -> simulate2(next.copy(x = next.x - 1)) + simulate2(next.copy(x = next.x + 1))
      else -> 1L
    }
  }
}
