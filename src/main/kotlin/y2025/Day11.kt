package com.emlett.aoc.y2025

object Day11 : Year2025() {
  val cache = mutableMapOf<Pair<String, Set<String>>, Long>()
  val input by lazy {
    lines
      .associate { it.substringBefore(':') to it.substringAfter(':').split(' ').filterNot(String::isEmpty) }
      .withDefault { emptySet() }
  }

  override fun part1() = numPathsWithNodes(start = "you", nodes = emptySet())
  override fun part2() = numPathsWithNodes(start = "svr", nodes = setOf("dac", "fft"))

  fun numPathsWithNodes(start: String, nodes: Set<String>): Long = cache.getOrPut(start to nodes) {
    if (start == "out" && nodes.isEmpty()) 1 else input
      .getValue(start)
      .sumOf { numPathsWithNodes(start = it, nodes = nodes.minus(start)) }
  }
}
