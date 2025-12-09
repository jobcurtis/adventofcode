package com.emlett.aoc.y2025

import com.emlett.aoc.utils.geometry.Point2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day09 : Year2025() {
  val shape by lazy { lines.map { it.split(',').map(String::toInt) }.map { (x, y) -> Point2D(x, y) } }
  val pairs by lazy { shape.flatMapIndexed { i, a -> shape.subList(i + 1, shape.size).map { b -> a to b } } }
  val edges by lazy { (shape + shape.first()).zipWithNext() }

  override fun part1() = pairs.maxOf(::area)
  override fun part2() = context(shape, edges) { pairs.filter { rect -> contained(rect) }.maxOf(::area) }

  fun area(rect: Pair<Point2D, Point2D>) = rect.let { (a, b) ->
    (abs(a.x - b.x) + 1L) * (abs(a.y - b.y) + 1L)
  }

  context(shape: List<Point2D>, edges: List<Pair<Point2D, Point2D>>)
  fun contained(rect: Pair<Point2D, Point2D>): Boolean {
    val (xmin, xmax) = min(rect.first.x, rect.second.x) to max(rect.first.x, rect.second.x)
    val (ymin, ymax) = min(rect.first.y, rect.second.y) to max(rect.first.y, rect.second.y)

    val xrange = (xmin + 1)..<xmax
    val yrange = (ymin + 1)..<ymax

    fun intersects(edge: Pair<Point2D, Point2D>): Boolean = edge.let { (a, b) ->
      return@let when {
        a.y == b.y -> a.y in yrange && (min(a.x, b.x) - 1..max(a.x, b.x)).let { xmin in it || xmax in it }
        a.x == b.x -> a.x in xrange && (min(a.y, b.y) + 1..max(a.y, b.y)).let { ymin in it || ymax in it }
        else -> error("Invalid edge $a -> $b")
      }
    }

    return edges.none(::intersects)
  }
}
