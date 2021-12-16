package com.emlett.aoc

import java.util.PriorityQueue
import kotlin.system.measureTimeMillis

fun main() {
    val lines = readAsLines("Day15.txt")

    lines.flatMapIndexed { y, ln -> ln.mapIndexed { x, risk -> Point(x, y) to risk.digitToInt() } }
        .toMap()
        .let { input -> dijkstra2(input, input.keys.min(), input.keys.max()) }
        .also { println("Part 1: $it") }

    val dimX = lines.first().length
    val dimY = lines.size

    (0 until 5).flatMap { ry ->
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
        .let { input -> dijkstra2(input, input.keys.min(), input.keys.max()) }
        .also { println("Part 2: $it") }
}

fun dijkstra2(map: Map<Point, Int>, start: Point, end: Point): Int {
    var result: Int? = null
    measureTimeMillis {
        val weights: MutableMap<Point, Int> = mutableMapOf(start to 0).withDefault { Int.MAX_VALUE }
        val visited = mutableSetOf(start)
        val next =
            PriorityQueue<Point> { l, r -> weights.getValue(l).compareTo(weights.getValue(r)) }.apply { add(start) }

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

        result = weights.getValue(end)
    }.also { println("Runtime: ${it}ms") }
    return result!!
}
