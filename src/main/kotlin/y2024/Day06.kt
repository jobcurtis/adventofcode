package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Direction.NORTH
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.input.parseGrid

object Day06 : Year2024() {
  val input by lazy { text.parseGrid(invertY = true) }
  val guard by lazy { input.filterValues('^'::equals).keys.first() }

  val path by lazy { walk(guard to NORTH, input::get).toList() }

  override fun part1() = path.distinctBy { it.first }.count()

  override fun part2() = path.distinctBy { it.first }.parallelStream()
    .map { (block, _) -> block to (path.takeWhile { (seen) -> block != seen }.lastOrNull() ?: (guard to NORTH)) }
    .map { (block, start) -> walk(start) { pt -> if (pt == block) '#' else input[pt] } }
    .filter { path -> path.isLoop() }
    .count().toInt()

  fun walk(start: Pair<Point2D, Direction>, charAt: (Point2D) -> Char?) = generateSequence(start) { (guard, dir) ->
    val next = guard.plus(dir)
    when (charAt(next)) {
      '#' -> guard to dir.clockwise
      null -> null
      else -> next to dir
    }
  }

  fun <T> Sequence<T>.isLoop(): Boolean {
    val seen = mutableSetOf<T>()
    return !this.all(seen::add)
  }
}
