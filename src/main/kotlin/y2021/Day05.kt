package com.emlett.aoc.y2021

import kotlin.math.*

object Day05 : Year2021() {
  private val input = lines
    .mapNotNull("""(\d+),(\d+) -> (\d+),(\d+)""".toRegex()::matchEntire)
    .map { it.groupValues.drop(1).map(String::toInt) }
    .map { Line(Coordinate(it[0], it[1]), Coordinate(it[2], it[3])) }

  override fun part1() = input
    .filterNot(Line::isDiagonal)
    .flatMap(Line::points)
    .groupBy { it }
    .count { it.value.size >= 2 }

  override fun part2() = input
    .flatMap(Line::points)
    .groupBy { it }
    .count { it.value.size >= 2 }

  data class Coordinate(val x: Int, val y: Int)

  data class Line(val a: Coordinate, val b: Coordinate) {
    fun isDiagonal(): Boolean = !(a.x == b.x || a.y == b.y)

    fun points(): List<Coordinate> {
      val dx = (b.x - a.x).sign
      val dy = (b.y - a.y).sign
      val steps = max(abs(a.x - b.x), abs(a.y - b.y))

      return List(steps + 1) { Coordinate(a.x + (dx * it), a.y + (dy * it)) }
    }
  }
}
