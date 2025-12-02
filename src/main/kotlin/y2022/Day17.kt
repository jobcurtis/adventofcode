package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.plot
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.repeat
import kotlin.math.max

object Day17 : Year2022() {
  private val rocks = sequenceOf(Rock1, Rock2, Rock3, Rock4, Rock5).repeat().iterator()
  private val input = text.map { if (it == '>') Direction.EAST else Direction.WEST }.asSequence().repeat().iterator()

  override fun part1() = generateSequence(Chamber()) { chamber -> chamber.with(rocks.next(), input) }
    .drop(2022)
    .first().rocks.let { it + (0..7).map { x -> pt(x, 0) } }
    .run { this.maxOf { it.y } }

  override fun part2() = TODO()

  private data class Chamber(val rocks: Set<Point2D> = setOf(), val maxY: Int = 0) {
    private val minX = 0
    private val maxX = 8
    private val minY = 0
    private val entry get() = pt(minX + 3, maxY + 4)

    fun with(rock: Rock, blasts: Iterator<Direction>): Chamber {
      var pts = rock.pts.map { entry + it }

      while (true) {
        pts = move(pts, blasts.next())
        pts = moveOrNull(pts, Direction.SOUTH) ?: break
      }

      return this.copy(rocks = rocks + pts, maxY = max(maxY, pts.maxOf { it.y }))
    }

    private fun moveOrNull(pts: List<Point2D>, dir: Direction) = pts
      .map { it + dir }
      .takeUnless { it.any { pt -> pt in rocks || pt.y <= minY || pt.x <= minX || pt.x >= maxX } }

    private fun move(pts: List<Point2D>, dir: Direction) = moveOrNull(pts, dir) ?: pts
  }

  private sealed class Rock(val pts: List<Point2D>)
  private object Rock1 : Rock(listOf(pt(0, 0), pt(1, 0), pt(2, 0), pt(3, 0)))
  private object Rock2 : Rock(listOf(pt(1, 0), pt(0, 1), pt(1, 1), pt(2, 1), pt(1, 2)))
  private object Rock3 : Rock(listOf(pt(0, 0), pt(1, 0), pt(2, 0), pt(2, 1), pt(2, 2)))
  private object Rock4 : Rock(listOf(pt(0, 0), pt(0, 1), pt(0, 2), pt(0, 3)))
  private object Rock5 : Rock(listOf(pt(0, 0), pt(1, 0), pt(0, 1), pt(1, 1)))
}
