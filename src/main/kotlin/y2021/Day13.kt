package com.emlett.aoc.y2021

import com.emlett.aoc.utils.input.clean

object Day13 : Year2021() {
  private val points = lines.takeWhile(String::isNotBlank)
    .map { it.split(',').map(String::toInt) }
    .map { (x, y) -> x to y }
    .toSet()
  private val foldLines = lines.takeLastWhile(String::isNotBlank)
    .mapNotNull("""fold along ([xy])=(\d+)""".toRegex()::matchEntire)
    .map { it.groupValues.drop(1) }
    .map { (axis, value) -> axis.first() to value.toInt() }

  override fun part1() = points.foldAtLine(foldLines[0].first, foldLines[0].second).size
  override fun part2() = foldLines.fold(points) { pointsAcc, (axis, value) -> pointsAcc.foldAtLine(axis, value) }
    .let {
      "\n" + (0..it.maxOf(Point::y)).joinToString("\n") { y ->
        (0..it.maxOf(Point::x)).joinToString(" ") { x ->
          if (x to y in it) "#" else " "
        }
      }
    }.clean()

  private fun Set<Point>.foldAtLine(axis: Char, value: Int): Set<Point> = this.map { (x, y) ->
    when {
      axis == 'x' && x > value -> value + value - x to y
      axis == 'y' && y > value -> x to value + value - y
      else -> x to y
    }
  }.toSet()
}
