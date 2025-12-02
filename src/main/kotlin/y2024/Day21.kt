package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import java.util.*

object Day21 : Year2024() {
  val keysNumeric = mapOf(
    Point2D(0, 3) to '7',
    Point2D(1, 3) to '8',
    Point2D(2, 3) to '9',
    Point2D(0, 2) to '4',
    Point2D(1, 2) to '5',
    Point2D(2, 2) to '6',
    Point2D(0, 1) to '1',
    Point2D(1, 1) to '2',
    Point2D(2, 1) to '3',
    Point2D(1, 0) to '0',
    Point2D(2, 0) to 'A',
  )

  val keysDirectional = mapOf(
    Point2D(1, 1) to '^',
    Point2D(2, 1) to 'A',
    Point2D(0, 0) to '<',
    Point2D(1, 0) to 'v',
    Point2D(2, 0) to '>',
  )

  val pathsNumeric by lazy { keysNumeric.paths() }
  val pathsDirectional by lazy { keysDirectional.paths() }

  override fun part1() = lines.sumOf { code -> cost(code, depth = +2) * code.digits.toLong() }
  override fun part2() = lines.sumOf { code -> cost(code, depth = 25) * code.digits.toLong() }

  private val costCache = mutableMapOf<Pair<String, Int>, Long>()

  fun cost(code: String, depth: Int, paths: Map<Pair<Char, Char>, List<String>> = pathsNumeric): Long {
    fun stepCost(step: Pair<Char, Char>) = paths
      .getValue(step)
      .minOf { path -> if (depth == 0) path.length.toLong() else cost(path, depth - 1, pathsDirectional) }

    return costCache.getOrPut(code to depth) { "A$code".zipWithNext().sumOf(::stepCost) }
  }

  fun Map<Point2D, Char>.paths() = keys
    .flatMap { a -> keys.map { b -> a to b } }
    .associate { (a, b) -> (getValue(a) to getValue(b)) to lowestCostPaths(a, b) }

  fun Map<Point2D, Char>.lowestCostPaths(start: Point2D, target: Point2D): List<String> {
    val queue = PriorityQueue<Pair<List<Point2D>, Int>>(compareBy { (_, cost) -> cost }).apply { add(listOf(start) to 0) }
    val visited = mutableMapOf<Point2D, Int>()
    val candidates = mutableListOf<String>()
    var best = Int.MAX_VALUE

    while (queue.isNotEmpty()) {
      val (path, cost) = queue.poll()

      if (cost > best) break

      val curr = path.last()

      if (curr == target) {
        if (cost < best) best = cost
        candidates.add(path.zipWithNext().map(::directionKey).joinToString("") + "A")
      } else if (visited.getOrDefault(curr, Int.MAX_VALUE) >= cost) {
        visited[curr] = cost
        curr.adjacentPoints.filter(keys::contains).forEach { next -> queue.add(path + next to cost + 1) }
      }
    }

    return candidates
  }

  fun directionKey(it: Pair<Point2D, Point2D>) = when (it.first.directionTo(it.second)) {
    Direction.NORTH -> '^'
    Direction.EAST -> '>'
    Direction.SOUTH -> 'v'
    Direction.WEST -> '<'
  }
}
