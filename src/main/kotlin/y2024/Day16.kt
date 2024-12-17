package com.emlett.aoc.y2024

import com.emlett.aoc.utils.geometry.Direction
import com.emlett.aoc.utils.geometry.Point2D
import com.emlett.aoc.utils.graphs.ValueGraph
import com.emlett.aoc.utils.graphs.findShortestDistance
import com.emlett.aoc.utils.input.parseGrid
import java.util.*

object Day16 : Year2024() {

    val input by lazy { text.parseGrid(invertY = true) }
    val start by lazy { input.filterValues('S'::equals).keys.map { it to Direction.EAST } }
    val graph = object : ValueGraph<Pair<Point2D, Direction>, Int> {
        override fun successors(node: Pair<Point2D, Direction>) = buildList {
            if (input[node.first + node.second] != '#') add(node.first + node.second to node.second)
            add(node.first to node.second.clockwise)
            add(node.first to node.second.counterclockwise)
        }

        override fun getValue(a: Pair<Point2D, Direction>, b: Pair<Point2D, Direction>): Int = when (b) {
            a.first + a.second to a.second -> 1
            a.first to a.second.clockwise -> 1000
            a.first to a.second.counterclockwise -> 1000
            else -> throw IllegalStateException()
        }
    }

    override fun part1() = graph.findShortestDistance(start) { input[it.first] == 'E' }
    override fun part2() = graph.findPath(start) { input[it.first] == 'E' }.map { it.first }.distinct().count()

    fun <N> ValueGraph<N, Int>.findPath(initial: Iterable<N>, target: (N) -> Boolean): List<N> {
        TODO("this does give the correct answer but is not a correct solution (and is very slow...)")

        val weights: MutableMap<N, Int> = initial.associateWith { 0 }.toMutableMap().withDefault { Int.MAX_VALUE }
        val visited: MutableSet<N> = initial.toMutableSet()
        val next: PriorityQueue<N> = PriorityQueue(compareBy(weights::getValue)).apply { addAll(initial) }
        val pathTo = mutableMapOf<N, Set<N>>().withDefault { mutableSetOf() }

        while (next.isNotEmpty()) {
            val currentNode = next.remove().also(visited::add)
            successors(currentNode).forEach { nextNode ->
                if (nextNode !in visited) {
                    val weight = weights.getValue(currentNode) + this.getValue(currentNode, nextNode)
                    if (weight <= weights.getValue(nextNode)) {
                        weights[nextNode] = weight
                        next.add(nextNode)
                        pathTo[nextNode] = pathTo.getValue(nextNode) + pathTo.getValue(currentNode) + currentNode
                    }
                }
            }
        }

        return pathTo.filterKeys(target).values.flatten()
    }
}
