package com.emlett.aoc.y2018

import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.geometry.plot
import com.emlett.aoc.utils.geometry.pt
import com.emlett.aoc.utils.input.extractInts
import kotlin.math.max
import kotlin.math.min

object Day10 : Year2018() {
  val regex = Regex("""^position=<\s*([\d\-]+),\s*([\d\-]+)> velocity=<\s*([\d\-]+),\s*([\d\-]+)>$""")
  val input by lazy { lines.map { line -> regex.extractInts(line) { (px, py, vx, vy) -> pt(px, py) to pt(vx, vy) } } }

  val message by lazy {
    generateSequence(input) { it.map { (p, v) -> p.plus(v) to v } }
      .map { pairs -> pairs.map { it.first } }
      .withIndex()
      .zipWithNext()
      .first { (curr, next) -> next.value.greaterThan(curr.value) }
      .first
  }

  override fun part1() = plot(message.value, invertY = false, markers = false)
  override fun part2() = message.index

  fun <T, R : Comparable<R>> Iterable<T>.rangeOf(selector: (T) -> R) = minOf(selector)..maxOf(selector)

  fun ClosedRange<Int>.size() = max(start, endInclusive) - min(start, endInclusive)

  fun Collection<Point2D>.greaterThan(other: Collection<Point2D>) =
    other.rangeOf(Point2D::x).size() < this.rangeOf(Point2D::x).size()
        || other.rangeOf(Point2D::y).size() < this.rangeOf(Point2D::y).size()
}
