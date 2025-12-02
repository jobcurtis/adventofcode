package com.emlett.aoc.y2023

import com.emlett.aoc.utils.geometry.Point2D

object Day13 : Year2023() {
  private val fields by lazy {
    text
      .split("\n\n")
      .map(String::lines)
      .map { it.flatMapIndexed { y, row -> row.mapIndexed { x, c -> Point2D((x + 1), (y + 1)) to c } }.toMap() }
  }

  override fun part1() = fields.sumOf { it.findReflectionValue() }
  override fun part2() = fields.sumOf { it.findReflectionValue(1) }

  private fun Map<Point2D, Char>.findReflectionValue(smudges: Int = 0): Int {
    val (mx, my) = keys.maxOf(Point2D::x) to keys.maxOf(Point2D::y)

    for (x in 1..<mx) if (hasReflectionX(x, my, smudges)) return x
    for (y in 1..<my) if (hasReflectionY(y, mx, smudges)) return y * 100

    throw IllegalStateException("No mirror in this pattern")
  }

  private fun Map<Point2D, Char>.hasReflectionX(mirror: Int, max: Int, smudges: Int): Boolean {
    var differences = 0

    for (x in 1..mirror) {
      for (y in 1..max) {
        val mirrored = this[Point2D(x = 1 + x + (2 * (mirror - x)), y = y)] ?: break
        if (mirrored != this[Point2D(x, y)] && ++differences > smudges) return false
      }
    }

    return differences == smudges
  }

  private fun Map<Point2D, Char>.hasReflectionY(mirror: Int, max: Int, smudges: Int): Boolean {
    var differences = 0

    for (y in 1..mirror) {
      for (x in 1..max) {
        val mirrored = this[Point2D(x = x, y = 1 + y + (2 * (mirror - y)))] ?: break
        if (mirrored != this[Point2D(x, y)] && ++differences > smudges) return false
      }
    }

    return differences == smudges
  }
}
