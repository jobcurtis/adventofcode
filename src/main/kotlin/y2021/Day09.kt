@file:Suppress("SpellCheckingInspection")

package com.emlett.aoc.y2021

object Day09 : Year2021() {
  private const val max_height = 9
  private val map = lines.map { line -> line.chunked(1) { it.toString().toInt() } }
    .flatMapIndexed { i, row -> row.mapIndexed { j, height -> (i to j) to height } }
    .associate { (point, height) -> point to height }

  private val lowpoints: Set<Point> = map.entries.fold(emptySet()) { points, (point, height) ->
    point.adjacentPoints.mapNotNull { adjacentPoint -> map[adjacentPoint] }
      .all { adjacentHeight -> adjacentHeight > height }
      .let { lowest -> if (lowest) points + point else points }
  }

  override fun part1() = lowpoints.mapNotNull(map::get).sumOf { it + 1 }
  override fun part2() = lowpoints.map { point -> map.basin(point).size }
    .sortedDescending()
    .take(3)
    .reduce(Int::times)

  private fun Map<Point, Int>.basin(currentPoint: Point): Set<Point> = currentPoint.adjacentPoints
    .filter { adjacentPoint ->
      this[adjacentPoint]?.let { it < max_height } ?: false
    }
    .fold(setOf(currentPoint)) { points, adjacentPoint ->
      if (getValue(adjacentPoint) <= getValue(currentPoint)) points
      else points + basin(adjacentPoint)
    }
}
