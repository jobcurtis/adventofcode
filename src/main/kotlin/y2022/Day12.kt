package com.emlett.aoc.y2022

import com.emlett.aoc.utils.geometry.Point2D
import java.util.*

object Day12 : Year2022() {
  private val grid = lines.flatMapIndexed { y, r -> r.mapIndexed { x, c -> Point2D(x, y) to c } }.toMap()
  private val start = grid.filterValues { it == 'S' }.keys.first()
  private val end = grid.filterValues { it == 'E' }.keys.first()

  private val heights = grid.mapValues { (_, v) ->
    when (v) {
      'S' -> 1
      'E' -> 26
      else -> v.code - 96
    }
  }

  override fun part1() = dijkstra(heights, start, end)
  override fun part2() = heights.filterValues { it == 1 }.keys.minOf { dijkstra(heights, it, end) }

  // cf com.emlett.aoc.y2021.Day15.dijkstra - only modification is the additional filter and
  // the simpler weights calculation
  private fun dijkstra(map: Map<Point2D, Int>, start: Point2D, end: Point2D): Int {
    val weights: MutableMap<Point2D, Int> = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }
    val visited = mutableSetOf(start)
    val next = PriorityQueue<Point2D> { l, r -> weights.getValue(l).compareTo(weights.getValue(r)) }

    next.add(start)

    while (next.isNotEmpty()) {
      val currentNode = next.remove().also { visited.add(it) }
      val currentHeight = map.getValue(currentNode)

      currentNode.adjacentPoints
        .filter { it in map && map.getValue(it) <= currentHeight + 1 }
        .forEach { nextNode ->
          if (nextNode !in visited) {
            val weight = weights.getValue(currentNode) + 1
            if (weight < weights.getValue(nextNode)) {
              weights[nextNode] = weight
              next.add(nextNode)
            }
          }
        }
    }

    return weights.getValue(end)
  }
}
