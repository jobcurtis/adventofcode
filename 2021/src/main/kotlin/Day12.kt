package com.emlett.aoc

private typealias Graph = Map<String, Set<String>>

fun main() {
    val graph: Graph = readAsLines("Day12.txt")
        .map { it.split('-') }
        .fold(emptyMap()) { graph, (a, b) ->
            graph
                .let { it + (a to (it.getOrDefault(a, emptySet()) + b)) }
                .let { it + (b to (it.getOrDefault(b, emptySet()) + a)) }
        }

    graph.findPaths(listOf("start"))
        .count()
        .also { println("Part 1: $it") }

    graph.findPaths(listOf("start"), true)
        .count()
        .also { println("Part 2: $it") }
}

private fun Graph.findPaths(path: List<String>, repeatSmallCave: Boolean = false): List<List<String>> {
    return if (path.last() == "end") listOf(path)
    else this.getValue(path.last())
        .filterNot { it == "start" }
        .filterNot { if (repeatSmallCave) false else it.all(Char::isLowerCase) && it in path  }
        .map { path + it }
        .flatMap { nextPath -> findPaths(nextPath, repeatSmallCave && !nextPath.containsLowerCaseDuplicate()) }
}

private fun List<String>.containsLowerCaseDuplicate() = this
    .groupBy { it }
    .any { (k, v) -> v.size > 1 && k.all(Char::isLowerCase) }
