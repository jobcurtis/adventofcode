package com.emlett.aoc.y2025

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day04 : Year2025() {
  val rolls by lazy { text.parseGrid().filterValues { it == '@' }.keys }

  override fun part1() = rolls.count(accessible(rolls))
  override fun part2() = rolls.size - removeAccessible(rolls).size

  fun accessible(rolls: Set<Point2D>) =
    fun(roll: Point2D) = roll.adjacentPointsDiagonal.count(rolls::contains) < 4

  tailrec fun removeAccessible(rolls: Set<Point2D>): Set<Point2D> {
    val removed = rolls.filter(accessible(rolls))
    return if (removed.isEmpty()) rolls else removeAccessible(rolls - removed.toSet())
  }
}
