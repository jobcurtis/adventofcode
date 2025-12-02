package com.emlett.aoc.y2021

object Day12 : Year2021() {
  private val graph: Map<String, Set<String>> = lines.map { it.split('-') }.fold(emptyMap()) { graph, (a, b) ->
    graph.let { it + (a to (it.getOrDefault(a, emptySet()) + b)) }
      .let { it + (b to (it.getOrDefault(b, emptySet()) + a)) }
  }

  override fun part1() = graph.findPaths(listOf("start")).count()
  override fun part2() = graph.findPaths(listOf("start"), true).count()

  private fun Map<String, Set<String>>.findPaths(
    path: List<String>,
    repeatSmallCave: Boolean = false,
  ): List<List<String>> {
    return if (path.last() == "end") listOf(path)
    else this.getValue(path.last())
      .filterNot { it == "start" }
      .filterNot { if (repeatSmallCave) false else it.all(Char::isLowerCase) && it in path }
      .map { path + it }
      .flatMap { nextPath -> findPaths(nextPath, repeatSmallCave && !nextPath.containsLowerCaseDuplicate()) }
  }

  private fun List<String>.containsLowerCaseDuplicate() = this.groupBy { it }
    .any { (k, v) -> v.size > 1 && k.all(Char::isLowerCase) }
}
