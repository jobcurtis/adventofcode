package com.emlett.aoc.y2024

object Day19 : Year2024() {
  val towels by lazy { lines.first().split(", ") }
  val combos by lazy { lines.drop(2) }

  override fun part1() = combos.map(::options).count { it != 0L }
  override fun part2() = combos.sumOf(::options)

  val cache = mutableMapOf<String, Long>()

  fun options(combo: String): Long = cache.getOrPut(combo) {
    if (combo.isEmpty()) 1 else towels
      .filter { combo.startsWith(it) }
      .sumOf { options(combo.substring(it.length)) }
  }
}
