package com.emlett.aoc.y2015

import com.emlett.aoc.utils.geometry.Point2D

object Day03 : Year2015() {
  override fun part1() = text.toList().getVisitedLocations().size

  override fun part2() = text
    .withIndex()
    .groupBy { it.index % 2 == 0 }
    .values
    .map { ops -> ops.map { it.value }.getVisitedLocations() }
    .let { (santa, roboSanta) -> santa + roboSanta }
    .size

  private fun Iterable<Char>.getVisitedLocations(): Set<Point2D> = this
    .map(::charToOp)
    .fold(Pair(setOf(Point2D(0, 0)), Point2D(0, 0))) { (set, pt), op -> (set + op(pt)) to op(pt) }
    .first

  private fun charToOp(char: Char): (Point2D) -> Point2D = when (char) {
    '^' -> { (x, y) -> Point2D(x, y + 1) }
    'v' -> { (x, y) -> Point2D(x, y - 1) }
    '>' -> { (x, y) -> Point2D(x + 1, y) }
    '<' -> { (x, y) -> Point2D(x - 1, y) }
    else -> throw IllegalArgumentException()
  }
}
