package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Direction.*
import com.emlett.aoc.utils.geometry.Point2D

object Day14 : Year2022() {
  private val slice = lines
    .map { line -> line.split(" -> ").map { it.split(',') }.map { (x, y) -> Point2D(x.toInt(), y.toInt()) } }
    .map { line -> line.windowed(2).fold(setOf<Point2D>()) { acc, (pt1, pt2) -> acc + pt1.lineTo(pt2) } }
    .flatten()
    .toSet()

  private val origin = Point2D(500, 0)
  private val maxY = slice.maxOf(Point2D::y)

  private val floorSlice = slice + Point2D(500 - (maxY + 5), maxY + 2).lineTo(Point2D(500 + maxY + 5, maxY + 2))

  override fun part1() =
    generateSequence(slice.toMutableSet()) { it.addIfNotNull(nextPosition(it, maxY)) }.drop(1).indexOfLast { true }

  override fun part2() = generateSequence(floorSlice.toMutableSet()) { c ->
    val next = nextPosition(c, maxY + 2)?.also { c.add(it) }
    if (next != null) c else null
  }.indexOfLast { true }

  private fun <T> MutableSet<T>.addIfNotNull(other: T?) = if (other == null) null else this.also { it.add(other) }

  // TODO optimise with 'falling' stack
  private fun nextPosition(occupiedPositions: Set<Point2D>, maxY: Int): Point2D? {
    var current = origin

    while (true) {
      if (origin in occupiedPositions || current.y >= maxY) return null
      else when {
        current + NORTH !in occupiedPositions -> current += NORTH
        current + NORTH + WEST !in occupiedPositions -> current = current + NORTH + WEST
        current + NORTH + EAST !in occupiedPositions -> current = current + NORTH + EAST
        else -> break
      }
    }

    return current
  }
}
