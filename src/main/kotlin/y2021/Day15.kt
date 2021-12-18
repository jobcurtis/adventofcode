package com.emlett.aoc.y2021

import java.util.*

object Day15 : Year2021() {
    private val dimX = lines.first().length
    private val dimY = lines.size

    override fun part1() = lines
        .flatMapIndexed { y, ln -> ln.mapIndexed { x, risk -> Point(x, y) to risk.digitToInt() } }
        .toMap()
        .let { input -> dijkstra(input, input.keys.min(), input.keys.max()) }

    override fun part2() = (0 until 5).flatMap { ry ->
        (0 until 5).flatMap { rx ->
            lines.flatMapIndexed { y, ln ->
                ln.mapIndexed { x, risk ->
                    Point(x + (rx * dimX),
                        y + (ry * dimY)) to (risk.digitToInt() + rx + ry).let { if (it > 9) it.mod(9) else it }
                }
            }
        }
    }
        .toMap()
        .let { input -> dijkstra(input, input.keys.min(), input.keys.max()) }

    private fun dijkstra(map: Map<Point, Int>, start: Point, end: Point): Int {
        val weights: MutableMap<Point, Int> = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }
        val visited = mutableSetOf(start)
        val next = PriorityQueue<Point> { l, r -> weights.getValue(l).compareTo(weights.getValue(r)) }
            .apply { add(start) }

        while (next.isNotEmpty()) {
            val currentNode = next.remove().also { visited.add(it) }
            currentNode.adjacentPoints.filter { it in map }.forEach { nextNode ->
                if (nextNode !in visited) {
                    val weight = weights.getValue(currentNode) + map.getValue(nextNode)
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
