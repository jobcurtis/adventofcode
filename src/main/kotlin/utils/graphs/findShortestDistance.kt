package com.emlett.aoc.utils.graphs

import java.util.*

/**
 * Finds the shortest distance in a weighted graph from one or more nodes to a target node.
 *
 * @param initial the initial nodes from which to start the search
 * @param target a lambda function that determines if a node is the target node
 * @return the shortest distance from an initial node to a target node
 */
fun <N> ValueGraph<N, Int>.findShortestDistance(initial: Iterable<N>, target: (N) -> Boolean): Int {
    val weights: MutableMap<N, Int> = initial.associateWith { 0 }.toMutableMap().withDefault { Int.MAX_VALUE }
    val visited: MutableSet<N> = initial.toMutableSet()
    val next: PriorityQueue<N> = PriorityQueue(compareBy(weights::getValue)).apply { addAll(initial) }

    while (next.isNotEmpty()) {
        val currentNode = next.remove().also(visited::add)
        successors(currentNode).forEach { nextNode ->
            if (nextNode !in visited) {
                val weight = weights.getValue(currentNode) + this.getValue(currentNode, nextNode)
                if (weight < weights.getValue(nextNode)) {
                    weights[nextNode] = weight
                    next.add(nextNode)
                }
            }
        }
    }

    return weights.filterKeys(target).minOf(Map.Entry<N, Int>::value)
}

fun <N> ValueGraph<N, Int>.findShortestDistanceOrNull(initial: Iterable<N>, target: (N) -> Boolean) =
    runCatching { findShortestDistance(initial, target) }.getOrNull()
