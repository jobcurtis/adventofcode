package com.emlett.aoc.y2024

import com.emlett.aoc.utils.bisect
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.graphs.ValueGraph
import com.emlett.aoc.utils.graphs.findShortestDistance
import com.emlett.aoc.utils.graphs.findShortestDistanceOrNull
import com.emlett.aoc.utils.input.ints

object Day18 : Year2024() {
  const val take = 1024
  const val size = 70

  val initial = Point2D(0, 0)
  val target = Point2D(size, size)

  val bytes by lazy { lines.map(::ints).map { (x, y) -> Point2D(x, y) } }

  override fun part1() = graph(bytes.take(take).toSet()).findShortestDistance(setOf(initial), target::equals)
  override fun part2() = bytes
    .bisect(take, bytes.size) { i -> graph(take(i).toSet()).noPathExists(initial, target) }
    .let { (x, y) -> "${x},${y}" }

  fun graph(bytes: Set<Point2D>) = object : ValueGraph<Point2D, Int> {
    override fun getValue(a: Point2D, b: Point2D) = 1
    override fun successors(node: Point2D): Iterable<Point2D> = node.adjacentPoints
      .filter { it.x in 0..size && it.y in 0..size }
      .filterNot(bytes::contains)
  }

  fun <N : Any> ValueGraph<N, Int>.noPathExists(initial: N, target: N) =
    findShortestDistanceOrNull(setOf(initial), target::equals) == null
}
